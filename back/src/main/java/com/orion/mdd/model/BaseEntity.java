package com.orion.mdd.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@Accessors(chain = true)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseEntity implements Serializable {
    public static final String ID_COLUMN_NAME = "id";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String UPDATED_AT_COLUMN_NAME = "updated_at";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    @CreationTimestamp
    private Instant updatedAt;
}