package com.specdoctor.domain.review.entity;

import com.specdoctor.domain.activity.entity.Activity;
import com.specdoctor.domain.user.entity.User;
import com.specdoctor.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Review extends BaseEntity {

    @JoinColumn(name = "writer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User writer;

    @JoinColumn(name = "activity_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Activity activity;

    @Column(columnDefinition = "TEXT")
    private String review;

    @NotNull
    @Column
    private int star;

    @NotNull
    @Column
    private String role;
}