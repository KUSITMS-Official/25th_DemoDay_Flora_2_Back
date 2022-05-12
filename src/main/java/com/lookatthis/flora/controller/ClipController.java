package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.service.ClipService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/clip")
@Api(tags = {"Clip API"})
public class ClipController {
    private final UserService userService;
    private final ClipService clipService;

    @GetMapping("/shop")
    public ResponseEntity<? extends ResponseDto> getShopClip() {
        User user = userService.getMyInfo();
        List<ClipShop> clipShops = clipService.findShopByUserId(user.getId());
        List<FlowerShop> flowerShops = new ArrayList<>();
        for(ClipShop clipShop : clipShops)
            flowerShops.add(clipShop.getFlowerShop());
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShops));
    }

    @GetMapping("/shop/{flowerShopId}")
    public ResponseEntity createShopClip(@PathVariable("flowerShopId") Long flowerShopId) {
        User user = userService.getMyInfo();
        clipService.createShop(user, flowerShopId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/shop/unclip/{flowerShopId}")
    public ResponseEntity deleteShopClip(@PathVariable("flowerShopId") Long flowerShopId) {
        User user = userService.getMyInfo();
        clipService.deleteShop(user.getId(), flowerShopId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/item")
    public ResponseEntity<? extends ResponseDto> getItemClip() {
        User user = userService.getMyInfo();
        List<ClipItem> clipItems = clipService.findItemByUserId(user.getId());
        List<Portfolio> portfolios = new ArrayList<>();
        for(ClipItem clipItem : clipItems)
            portfolios.add(clipItem.getPortfolio());
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    @GetMapping("/item/{portfolioId}")
    public ResponseEntity createItemClip(@PathVariable("portfolioId") Long portfolioId) {
        User user = userService.getMyInfo();
        clipService.createItem(user, portfolioId);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/item/unclip/{portfolioId}")
    public ResponseEntity deleteItemClip(@PathVariable("portfolioId") Long portfolioId) {
        User user = userService.getMyInfo();
        clipService.deleteItem(user.getId(), portfolioId);

        return new ResponseEntity(HttpStatus.OK);
    }
}
