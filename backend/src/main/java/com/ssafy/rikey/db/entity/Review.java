package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "review")
@NoArgsConstructor
public class Review extends com.ssafy.rikey.db.entity.BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String content;

    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_road_id")
    private BikeRoad bikeRoad;

    @Builder
    public Review(String content, int score, User user, BikeRoad bikeRoad) {
        this.content = content;
        this.score = score;
        this.user = user;
        this.bikeRoad = bikeRoad;
    }

    // 리뷰 수정을 위한 편의 함수
    public void update(String content, int score) {
        this.content = content;
        this.score = score;
    }
}
