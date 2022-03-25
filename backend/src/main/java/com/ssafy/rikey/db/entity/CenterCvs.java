package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "center_cvs")
@NoArgsConstructor
public class CenterCvs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "center_cvs_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cvs_id")
    private Cvs cvs;

    @Builder
    public CenterCvs(Center center, Cvs cvs) {
        this.center = center;
        this.cvs = cvs;
    }
}
