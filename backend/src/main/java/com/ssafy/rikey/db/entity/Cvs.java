package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "cvs")
@NoArgsConstructor
public class Cvs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cvs_id")
    private Long id;

    private String brandName;

    private String name;

    private String address;

    private String roadAddress;

    private Double latitude;

    private Double longitude;
}
