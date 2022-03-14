package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @Column(name = "user_id")
    private String id;

    private String nickName;

    private String profile_pic;

    private String greeting;

    private int cumulCarlorie;

    private int cumulDistance;

    private int cumulTime;

    @Enumerated(EnumType.STRING)
    private Area area;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auth_id", unique = true)
    private Auth auth;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likeArticles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<RidingInfo> ridingInfos = new ArrayList<>();

    @Builder
    public User(String id, Auth auth, String nickName, String greeting, Area area) {
        this.id = id;
        this.auth = auth;
        this.nickName = nickName;
        this.profile_pic = "";
        this.greeting = greeting;
        this.cumulCarlorie = 0;
        this.cumulDistance = 0;
        this.cumulTime = 0;
        this.area = area;
    }
}
