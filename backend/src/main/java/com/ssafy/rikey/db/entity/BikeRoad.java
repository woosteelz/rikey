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

    private String name;

    private String departure;

    private String destination;

    private int hour;

    private int minute;

    @Column(length = 5000)
    private String introduce;

    private Double startLatitude;

    private Double startLongitude;

    private Double endLatitude;

    private Double endLongitude;

    @OneToMany(mappedBy = "bikeRoad", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();
}
