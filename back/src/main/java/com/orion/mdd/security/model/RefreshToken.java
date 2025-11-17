package com.orion.mdd.security.model;

import com.orion.mdd.model.BaseEntity;
import com.orion.mdd.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken extends BaseEntity {
    public static final String TABLE_NAME = "refresh_token";
    public static final String USER_ID_COLUMN_NAME = "user_id";
    public static final String TOKEN_COLUMN_NAME = "token";
    public static final String EXPIRY_DATE_COLUMN_NAME = "expiry_date";

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}