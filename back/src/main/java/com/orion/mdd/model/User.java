package com.orion.mdd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

@Entity
@Accessors(chain = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User extends BaseEntity {
    public static final String TABLE_NAME = "user";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String USERNAME_COLUMN_NAME = "username";
    public static final String PASSWORD_COLUMN_NAME = "password";

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
}