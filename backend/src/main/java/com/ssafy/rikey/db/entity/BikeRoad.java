package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

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

    private String course;

    private String name;

    private Double departureLatitude;

    private Double departureLongitude;

    private Double arrivalLatitude;

    private Double arrivalLongitude;

    private int hour;

    private int minute;

    private String introduce;

    private Point startPoint;

    private Point endPoint;

    @OneToMany(mappedBy = "bikeRoad", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Center> centers = new ArrayList<>();

    @OneToMany(mappedBy = "bikeRoad", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
