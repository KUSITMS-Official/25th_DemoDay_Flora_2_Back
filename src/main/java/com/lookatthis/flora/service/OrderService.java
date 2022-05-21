package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.OrderDto;
import com.lookatthis.flora.dto.PortfolioDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FlowerShopRepository flowerShopRepository;
    private final PortfolioRepository portfolioRepository;
    private final EntityManager em;

    //주문건수(=예약건수) 출력
    public int countOrder(OrderDto orderDto) {
        Query query = em.createNativeQuery("SELECT COUNT(*) FROM user_portfolio");
        int c = query.getMaxResults();
        return c;
    }

    //예약 등록
    public user_portfolio createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUser()).orElseThrow();
        Portfolio portfolio = portfolioRepository.findById(orderDto.getPortfolio()).orElseThrow();
        FlowerShop flowerShop = flowerShopRepository.findById(orderDto.getFlowerShop()).orElseThrow();
        user_portfolio u_p = user_portfolio.builder()
                .user(user)
                .portfolio(portfolio)
                .flowerShop(flowerShop)
                .pickupDate(orderDto.getPickupDate())
                .price(orderDto.getPrice())
                .comment(orderDto.getComment())
                .build();
        return orderRepository.save(u_p);
    }

    //결제 완료

    //픽업완료

}
