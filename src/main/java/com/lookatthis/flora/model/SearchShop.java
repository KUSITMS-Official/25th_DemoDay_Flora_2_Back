package com.lookatthis.flora.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Builder
public class SearchShop {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "search_shop_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flower_shop_id", nullable = false)
    private FlowerShop flowerShop;
}
