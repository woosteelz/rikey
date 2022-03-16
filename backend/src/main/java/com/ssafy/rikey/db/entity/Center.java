package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "center")
@NoArgsConstructor
public class Center {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_id")
    private Long id;

    private String name;

    private String address;

    private Long bikeroadId;

    private Point point;
}
