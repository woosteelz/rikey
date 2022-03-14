package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "toilet")
@NoArgsConstructor
public class Toilet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "toilet_id")
    private Long id;

    private String name;

    private String roadAddress;

    private String address;

    private String openTime;

    private Point point;
}
