import React, { useState } from "react";

export const Register = (props) => {
    
    const [fname, setFname] = useState('');
    const [lname, setLname] = useState('');
    const [email, setEmail] = useState('');
    const [pass, setPass] = useState('');
    const [cpass, setCpass] = useState('');
    

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(email);
    }

   

    return (
        <div className="auth-form-container">
            <h2>Registration</h2>
        <form className="register-form" onSubmit={handleSubmit}>
            <label htmlFor="fname">First name</label>
            <input value={fname} name="fname" onChange={(e) => setFname(e.target.value)} id="fname" placeholder="First Name" />
            <label htmlFor="lname">Last name</label>
            <input value={lname} name="lname" onChange={(e) => setLname(e.target.value)} id="lname" placeholder="Last Name" />
            <label htmlFor="email">Email</label>
            <input value={email} onChange={(e) => setEmail(e.target.value)}type="email" placeholder="youremail@gmail.com" id="email" name="email" />
            <label htmlFor="password">Password</label>
            <input value={pass} onChange={(e) => setPass(e.target.value)} type="password" placeholder="********" id="password" name="password" />
            <label htmlFor="cpassword">Confirm password</label>
            <input value={cpass} onChange={(e) => setCpass(e.target.value)} type="password" placeholder="********" id="cpassword" name="cpassword" />
            &nbsp;
            <button type="submit">Register</button>
        </form>
        <button className="link-btn" onClick={() => props.onFormSwitch('login')}>Have an account? Login here.</button>
    </div>
    )
}