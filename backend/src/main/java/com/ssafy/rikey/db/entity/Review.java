package com.ssafy.rikey.db.entity;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @NotNull
    private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bike_road_id")
    private BikeRoad bikeRoad;

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateScore(int score) {
        this.score = score;
    }

    @Builder
    public Review(String content, int score, User user, BikeRoad bikeRoad) {
        this.content = content;
        this.score = score;
        this.user = user;
        this.bikeRoad = bikeRoad;
    }
}
