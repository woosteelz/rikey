package com.ssafy.rikey.db.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Table(name = "article")
@NoArgsConstructor
public class Article extends com.ssafy.rikey.db.entity.BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    private String title;

    private String content;

    private int hits;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> likeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Like> comments = new ArrayList<>();


    @Builder
    private Article(String title, String content, Category category, User user) {
        this.title = title;
        this.content = content;
        this.hits = 0;
        this.category = category;
        this.author = user;
    }

    public void update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void increaseHits() {
        this.hits++;
    }

}
