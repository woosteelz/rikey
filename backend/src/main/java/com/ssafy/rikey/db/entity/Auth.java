package com.ssafy.rikey.db.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "auth")
public class Auth {

    @Id
    @Column(name = "auth_id")
    private String id;

    @Column(columnDefinition = "boolean default false")
    private Boolean isAdmin;

    @OneToOne(mappedBy = "auth", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private User user;
}
