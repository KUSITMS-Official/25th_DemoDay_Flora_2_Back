package com.lookatthis.flora.dto;

import com.lookatthis.flora.model.Review;
import lombok.*;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {
    private Long id;
    private String title;
    private String filePath;

}
