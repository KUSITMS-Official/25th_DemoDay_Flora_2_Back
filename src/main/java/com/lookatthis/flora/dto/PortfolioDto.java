package com.lookatthis.flora.dto;

import com.lookatthis.flora.model.Color;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Portfolio;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

}
