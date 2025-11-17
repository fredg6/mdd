package com.orion.mdd.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class User extends BaseEntity {
    public static final String TABLE_NAME = "user";
    public static final String EMAIL_COLUMN_NAME = "email";
    public static final String USERNAME_COLUMN_NAME = "username";
    public static final String PASSWORD_COLUMN_NAME = "password";
    public static final String SUBSCRIPTION_JOIN_TABLE_NAME = "subscription";

    @NotEmpty
    @Email
    @Column(unique = true)
    private String email;
    @NotBlank
    @Column(unique = true)
    private String username;
    @NotBlank
    private String password;
    @ManyToMany
    @JoinTable(name = SUBSCRIPTION_JOIN_TABLE_NAME, inverseJoinColumns = @JoinColumn(name = "topic_id"))
    private Set<Topic> subscribedTopics;
}