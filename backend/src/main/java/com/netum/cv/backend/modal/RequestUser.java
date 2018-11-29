package com.netum.cv.backend.modal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestUser {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public static RequestUser buildApiUser(String username, String firstName, String lastName, String email, String password){

        return new RequestUser(username, firstName, lastName, email, password);
    }

}
