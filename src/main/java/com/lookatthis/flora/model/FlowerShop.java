package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class FlowerShop extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "shop_id")
    private Long id;

    // nullable: null 허용 여부
    // unique: 중복 허용 여부 (false 일때 중복 허용)
    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_address", nullable = false)
    private String shopAddress;

    @Column(name = "shop_number")
    private String shopNumber;

    @Column(name = "shop_open_time")
    private String shopOpenTime;

    @Column(name = "shop_close_time")
    private String shopCloseTime;

    @Column(name = "shop_rest_time")
    private String shopRestTime;

    @Lob
    @Column(name = "shop_image")
    private Blob shopImage;

    public InputStream getShopImageContent() throws SQLException {
        if (getShopImage() == null) {
            return null;
        }
        return getShopImage().getBinaryStream();
    }

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;
}
