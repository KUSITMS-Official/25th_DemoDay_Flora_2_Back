package com.lookatthis.flora.dto;

import com.lookatthis.flora.model.FlowerShop;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FlowerShopDto {

    private String flowerShopAddress;

    private Double latitude;

    private Double longitude;

    private String flowerShopNumber;

    private String flowerShopOpenTime;
    private String flowerShopCloseTime;
    private String flowerShopRestTime;

    private String flowerShopName;

    public FlowerShop toFlowerShop() throws ParseException {
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point flowerShopPoint = (Point) new WKTReader().read(pointWKT);
        return FlowerShop.builder()
                .flowerShopAddress(flowerShopAddress)
                .flowerShopLatitude(latitude)
                .flowerShopLongitude(longitude)
                .flowerShopPoint(flowerShopPoint)
                .flowerShopName(flowerShopName)
                .flowerShopNumber(flowerShopNumber)
                .flowerShopOpenTime(flowerShopOpenTime)
                .flowerShopCloseTime(flowerShopCloseTime)
                .flowerShopRestTime(flowerShopRestTime)
                .build();
    }

}
