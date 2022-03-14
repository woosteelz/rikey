package com.ssafy.rikey.db.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "riding_info")
@NoArgsConstructor
public class RidingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "riding_info_id")
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int ridingCalorie;

    private int ridingTime;

    private double ridingDist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
