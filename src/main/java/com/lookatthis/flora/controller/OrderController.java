package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.OrderDto;
import com.lookatthis.flora.dto.PortfolioDto;
import com.lookatthis.flora.service.FlowerShopService;
import com.lookatthis.flora.service.OrderService;
import com.lookatthis.flora.service.PortfolioService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
@Api(tags = {"Order API"})
public class OrderController {

    private final OrderService orderService;

    //주문하기
    @ApiOperation(value = "주문")
    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }
}
