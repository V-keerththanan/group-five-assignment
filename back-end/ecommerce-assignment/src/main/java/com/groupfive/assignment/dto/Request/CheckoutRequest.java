package com.groupfive.assignment.dto.Request;

import com.groupfive.assignment.model.Cart;
import com.groupfive.assignment.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutRequest {

        private Integer userId;
        private Long cartId;

        private String homeNo;
         private String homeStreet;
         private String homeCity;
        private String homeDistrict;
         private String homePhoneNo;



    }


