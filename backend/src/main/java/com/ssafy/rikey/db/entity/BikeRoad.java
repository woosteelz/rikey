package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "bikeroad")
@NoArgsConstructor
public class BikeRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bikeroad_id")
    private Long id;

    private String name;

    private String departure;

    private String destination;

    private int hour;

    private int minute;

    private int score;

    private int cnt;

    @Column(length = 5000)
    private String introduce;

    private Double startLatitude;

    private Double startLongitude;

    private Double endLatitude;

    private Double endLongitude;

    @OneToMany(mappedBy = "bikeRoad", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    // 평점 수정을 위한 편의 함수
    public void update(int score, int cnt) {
        this.score = score;
        this.cnt = cnt;
    }
}
