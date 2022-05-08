package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class Location {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "locaiton_id")
    private Long id;

    @Column(name = "location_longtitude", nullable = false)
    private BigDecimal longtitude;

    @Column(name = "location_latitude", nullable = false)
    private BigDecimal latitude;

    @OneToOne(mappedBy = "location")
    private FlowerShop flowerShop;

}
