package com.lookatthis.flora.model;

import lombok.*;

import javax.persistence.*;

@Getter
public class Location {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "locaiton_id")
    private Long id;

    @Column(name = "location_latitude", nullable = false)
    private Double latitude;

    @Column(name = "location_longttude", nullable = false)
    private Double longitude;

    public Location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
