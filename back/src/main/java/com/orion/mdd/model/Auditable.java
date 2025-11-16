package com.orion.mdd.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Auditable implements Serializable {
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String UPDATED_AT_COLUMN_NAME = "updated_at";
    public static final String CREATED_BY_COLUMN_NAME = "created_by";
    public static final String UPDATED_BY_COLUMN_NAME = "updated_by";

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    protected Instant createdAt;

    @LastModifiedDate
    protected Instant updatedAt;

    @Column(nullable = false, updatable = false)
    @CreatedBy
    protected String createdBy = "SYSTEM";

    @LastModifiedBy
    protected String updatedBy;
}