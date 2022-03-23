package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    @Column(unique = true)
    private String nickName;

    private String profile_pic;

    private String greeting;

    private int cumulCalorie;

    private double cumulDistance;

    private int cumulTime;

    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", unique = true)
    private Auth auth;

    @OneToMany(mappedBy = "user")
    private List<Likey> likeArticles = new ArrayList<>();

    @OneToMany(mappedBy = "author", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RidingInfo> ridingInfos = new ArrayList<>();

    @Builder
    public User(String id, Auth auth, String nickName, String greeting, Area area, int height, int weight) {
        this.id = id;
        this.auth = auth;
        this.nickName = nickName;
        this.profile_pic = "";
        this.greeting = greeting;
        this.cumulCalorie = 0;
        this.cumulDistance = 0;
        this.cumulTime = 0;
        this.area = area;
        this.height = height;
        this.weight = weight;
    }

    // 유저 정보 수정을 위한 편의 함수
    public void update(String nickName, String greeting, Area area, String profile_pic) {
        this.nickName = nickName;
        this.greeting = greeting;
        this.area = area;
        this.profile_pic = profile_pic;
    }

    // 유저 주행 정보 수정을 위한 편의 함수
    public void updateRiding(int calorie, double distance, int time) {
        this.cumulCalorie += calorie;
        this.cumulDistance += distance;
        this.cumulTime += time;
    }
}
