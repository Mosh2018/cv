package com.netum.cv.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users", uniqueConstraints = {

        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends ChangeTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String username;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

}
