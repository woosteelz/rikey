package com.ssafy.rikey.api.request;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ArticleRequestDto {

    private String title;

    private String content;

    private Category category;

}
