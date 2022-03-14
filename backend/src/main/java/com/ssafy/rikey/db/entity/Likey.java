package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likey")
public class Likey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likey_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Article article;

    @Builder
    public Likey(User user, Article article) {
        this.user = user;
        this.article = article;
    }
}
