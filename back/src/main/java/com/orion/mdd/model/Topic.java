package com.orion.mdd.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
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
public class Topic extends BaseEntity {
    public static final String TABLE_NAME = "topic";
    public static final String TITLE_COLUMN_NAME = "title";
    public static final String DESCRIPTION_COLUMN_NAME = "description";

    @NotBlank
    private String title;
    private String description;
    @OneToMany(mappedBy = "topic")
    private Set<Post> posts;
}