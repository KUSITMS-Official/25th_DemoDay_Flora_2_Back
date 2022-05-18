package com.lookatthis.flora.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class FlowerShop extends Timestamped implements Serializable {

    // ID가 자동으로 생성 및 증가
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "flower_shop_id")
    private Long id;

    @Column(name = "flower_shop_name")
    private String flowerShopName;

    @Column(name = "flower_shop_description")
    @Size(max = 5000)
    private String flowerShopDescription;

    @Column(name = "flower_shop_number")
    private String flowerShopNumber;

    @Column(name = "flower_shop_open_time")
    private String flowerShopOpenTime;

    @Column(name = "flower_shop_close_time")
    private String flowerShopCloseTime;

    @Column(name = "flower_shop_rest_time")
    private String flowerShopRestTime;

    @Column(name = "flower_shop_address")
    private String flowerShopAddress;

    @Column(name = "clip_count")
    private int clipCount = 0;

    @Lob
    @Column(name = "flower_shop_image")
    private Blob flowerShopImage;

    public InputStream getFlowerShopImageContent() throws SQLException {
        if (getFlowerShopImage() == null) {
            return null;
        }
        return getFlowerShopImage().getBinaryStream();
    }

    @Column(name = "flower_shop_latitude")
    private Double flowerShopLatitude;

    @Column(name = "flower_shop_longitude")
    private Double flowerShopLongitude;

    @JsonIgnore
    @Column(name = "flower_shop_point")
    private Point flowerShopPoint;

}
