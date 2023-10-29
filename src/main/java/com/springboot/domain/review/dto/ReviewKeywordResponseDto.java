package com.springboot.domain.review.dto;

import com.springboot.domain.review.entity.Reviews;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewKeywordResponseDto {

    private long id;

    private String date;

    private String content;

    private String keyword;

    @Builder
    public ReviewKeywordResponseDto(Reviews entity) {
        this.id = entity.getId();
        this. date = entity.getDate();
        this.content = entity.getContent();
        this.keyword = entity.getKeyword();
    }

}
