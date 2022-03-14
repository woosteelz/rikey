package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "store")
@NoArgsConstructor
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    private String name;

    private String roadAddress;

    private String address;

    private boolean airInjector;

    private boolean repairTable;

    private Point point;
}
