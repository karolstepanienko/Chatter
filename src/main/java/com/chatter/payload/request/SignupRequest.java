package com.chatter.payload.request;

import javax.validation.constraints.*;

import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private String role;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
