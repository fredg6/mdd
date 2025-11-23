package com.orion.mdd.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Comment extends BaseEntity {
    public static final String TABLE_NAME = "comment";
    public static final String POST_ID_COLUMN_NAME = "post_id";
    public static final String CONTENT_COLUMN_NAME = "content";
    public static final String CREATED_AT_COLUMN_NAME = "created_at";
    public static final String CREATED_BY_COLUMN_NAME = "created_by";

    @NotBlank
    @Size(max = 2000)
    private String content;
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;
    @Column(nullable = false, updatable = false)
    @CreatedBy
    private String createdBy;
    @ManyToOne
    private Post post;
}