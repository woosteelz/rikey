package com.ssafy.rikey.db.entity;

import lombok.Builder;
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

    @ElementCollection
    private List<String> pics;

    private int hits;

    @Enumerated(EnumType.STRING)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Likey> likeUsers = new ArrayList<>();

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Article(String title, String content, List<String> pics, Category category, User user) {
        this.title = title;
        this.content = content;
        this.pics = pics;
        this.hits = 0;
        this.category = category;
        this.author = user;
    }

    // 게시글 수정을 위한 편의 함수
    public void update(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    // 조회수 증가를 위한 편의 함수
    public void increaseHits() {
        this.hits++;
    }
}
