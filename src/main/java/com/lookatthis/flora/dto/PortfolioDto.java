package com.lookatthis.flora.dto;

import com.lookatthis.flora.model.Color;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioDto {

    private int price;
    private Color color;
    private String portfolioName;
    private String portfolioDescription;
    private Long flowerShopId;
    private Long flowerId;

}
