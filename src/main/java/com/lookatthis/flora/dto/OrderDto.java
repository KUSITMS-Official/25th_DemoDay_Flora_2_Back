package com.lookatthis.flora.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class OrderDto {
    private Long upId;
    private Long user;
    private Long portfolio;
    private Long flowerShop;
    private String pickupDate;
    private int price;
    private String comment;
}
