//package com.lookatthis.flora.model;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import javax.persistence.*;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class user_portfolio extends Timestamped{
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Id
//    @Column(name = "user_portfolio_id")
//    private Long UpId;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "portfolio_id", referencedColumnName = "flower_shop_id")
//    private Portfolio portfolio;
//
//    //flowershop 오류뜰수도
//    @ManyToOne
//    @JoinColumn(name = "flower_shop_id")
//    private FlowerShop flowerShop;
//
//    @Column(name = "order_date")
//    private String orderDate;
//
//    @Column(name = "pickup_date")
//    private String pickupDate;
//
//    @Column(name = "price")
//    private int price;
//
//    @Column(name = "comment")
//    private String comment;
//}