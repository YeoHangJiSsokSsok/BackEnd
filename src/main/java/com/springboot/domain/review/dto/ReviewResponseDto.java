package com.springboot.domain.review.dto;

import com.springboot.domain.review.entity.Reviews;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReviewResponseDto {

    private long id;

    private String date;

    private String content;

    private int state;

    @Builder
    public ReviewResponseDto(Reviews entity, int state) {
        this.id = entity.getId();
        this. date = entity.getDate();
        this.content = entity.getContent();
        this.state = state;
    }

}
