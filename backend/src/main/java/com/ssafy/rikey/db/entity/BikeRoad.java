package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "bike_road")
@NoArgsConstructor
public class BikeRoad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bike_road_id")
    private Long id;

    private String course;

    private String name;

    private double departureLatitude;

    private double departureLongitude;

    private double arrivalLatitude;

    private double arrivalLongitude;

    private int hour;

    private int minute;

    private String introduce;
}
