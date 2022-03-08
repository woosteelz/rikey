package com.ssafy.rikey.db.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String nickName;

    private String profile_pic;

    private String greeting;

    private int cumulCarlorie;

    private int cumulDistance;

    private int cumulTime;

    @Enumerated(EnumType.STRING)
    private Area area;
}
