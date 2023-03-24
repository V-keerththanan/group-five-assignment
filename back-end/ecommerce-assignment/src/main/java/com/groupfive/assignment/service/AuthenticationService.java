package com.groupfive.assignment.service;



import com.groupfive.assignment.dto.Request.AuthenticationRequest;
import com.groupfive.assignment.dto.Request.RegisterRequest;
import com.groupfive.assignment.dto.Response.AuthenticationResponse;
import com.groupfive.assignment.email.EmailService;
import com.groupfive.assignment.error.UserAlreadyExistsException;
import com.groupfive.assignment.model.User;
import com.groupfive.assignment.repository.TokenRepository;
import com.groupfive.assignment.repository.UserRepository;
import com.groupfive.assignment.token.Token;
import com.groupfive.assignment.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final EmailService emailService;



  public AuthenticationResponse register(RegisterRequest request) {
    boolean userExists = repository.existsByEmail(request.getEmail());

    if(repository.existsByEmail(request.getEmail())) {
      throw new UserAlreadyExistsException("User with this email already exists.");
    }
    String otp = emailService.generateOtp();
    var user = User.builder()
        .firstname(request.getFirstname())
        .lastname(request.getLastname())
        .email(request.getEmail())
            .status(false)
            .otp(otp)
        .password(passwordEncoder.encode(request.getPassword()))
        .build();
    var savedUser = repository.save(user);
    emailService.sendOtpEmail(user.getEmail(), otp);
    var jwtToken = jwtService.generateToken(user);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }
}
