package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private Boolean airInjector;

    private Boolean repairTable;

    private Double latitude;

    private Double longitude;
}
