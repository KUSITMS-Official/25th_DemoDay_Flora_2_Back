package com.lookatthis.flora.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Float score;
    private String content;
    private Long portfolioId;
}
