package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.service.ClipService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    // 사용자 꽃집 찜 목록 조회
    @ApiOperation(value = "사용자 꽃집 찜 목록 조회")
    @GetMapping("/shop")
    public ResponseEntity<? extends ResponseDto> getShopClips() {
        User user = userService.getMyInfo();
        List<ClipShop> clipShops = clipService.findShopByUserId(user.getId());
        List<FlowerShop> flowerShops = new ArrayList<>();
        for(ClipShop clipShop : clipShops)
            flowerShops.add(clipShop.getFlowerShop());
        return ResponseEntity.ok().body(new CommonResponseDto<>(flowerShops));
    }

    // 사용자 꽃집 찜 추가
    @ApiOperation(value = "사용자 꽃집 찜 추가")
    @GetMapping("/shop/{flowerShopId}")
    public ResponseEntity createShopClip(@PathVariable("flowerShopId") Long flowerShopId) {
        User user = userService.getMyInfo();
        clipService.createShopClip(user, flowerShopId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 사용자 꽃집 찜 삭제
    @ApiOperation(value = "사용자 꽃집 찜 삭제")
    @GetMapping("/shop/unclip/{flowerShopId}")
    public ResponseEntity deleteShopClip(@PathVariable("flowerShopId") Long flowerShopId) {
        User user = userService.getMyInfo();
        clipService.deleteShopClip(user.getId(), flowerShopId);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 사용자 꽃 상품 찜 목록 조회
    @ApiOperation(value = "사용자 꽃 상품 찜 목록 조회")
    @GetMapping("/item")
    public ResponseEntity<? extends ResponseDto> getItemClips() {
        User user = userService.getMyInfo();
        List<ClipItem> clipItems = clipService.findItemByUserId(user.getId());
        List<Portfolio> portfolios = new ArrayList<>();
        for(ClipItem clipItem : clipItems)
            portfolios.add(clipItem.getPortfolio());
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 사용자 꽃 상품 찜 추가
    @ApiOperation(value = "사용자 꽃 상품 찜 추가")
    @GetMapping("/item/{portfolioId}")
    public ResponseEntity createItemClip(@PathVariable("portfolioId") Long portfolioId) {
        User user = userService.getMyInfo();
        clipService.createItemClip(user, portfolioId);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 사용자 꽃 상품 찜 삭제
    @ApiOperation(value = "사용자 꽃 상품 찜 삭제")
    @GetMapping("/item/unclip/{portfolioId}")
    public ResponseEntity deleteItemClip(@PathVariable("portfolioId") Long portfolioId) {
        User user = userService.getMyInfo();
        clipService.deleteItemClip(user.getId(), portfolioId);

        return new ResponseEntity(HttpStatus.OK);
    }

    // 사용자 찜 목록 가게별로 조회

    // 사용자 꽃집 찜 여부 조회
    @ApiOperation(value = "사용자 꽃찝 찜 여부 조회")
    @GetMapping("/shop/check/{flowerShopId}")
    public ResponseEntity<Boolean> checkUserClipFlowerShop(@PathVariable Long flowerShopId) {
        User user = userService.getMyInfo();
        return ResponseEntity.ok(clipService.checkUserClipFlowerShop(user.getId(), flowerShopId));
    }

    // 사용자 꽃 상품 찜 여부 조회
    @ApiOperation(value = "사용자 꽃 상품 찜 여부 조회")
    @GetMapping("/item/check/{portfolioId}")
    public ResponseEntity<Boolean> checkUserClipPortfolio(@PathVariable Long portfolioId) {
        User user = userService.getMyInfo();
        return ResponseEntity.ok(clipService.checkUserClipPortfolio(user.getId(), portfolioId));
    }


}
