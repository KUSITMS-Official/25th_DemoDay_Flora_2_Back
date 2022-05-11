package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.Portfolio;
import com.lookatthis.flora.service.PortfolioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/portfolio")
@Api(tags = {"Portfolio API"})
public class PortfolioController {

    private final PortfolioService portfolioService;

    // 전체 꽃 상품 정보
    @ApiOperation(value = "전체 꽃 상품")
    @GetMapping("/")
    public ResponseEntity<? extends ResponseDto> getPortfolios() {
        List<Portfolio> portfolios = portfolioService.getPortfolios();
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 꽃 상품 정보
    @ApiOperation(value = "꽃 상품")
    @GetMapping("/{portfolioId}")
    public ResponseEntity<? extends ResponseDto> getPortfolio(@PathVariable Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioService.getPortfolio(portfolioId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolio));
    }

    // 꽃 상품 정보
    @ApiOperation(value = "가게의 꽃 상품")
    @GetMapping("/shop/{flowerShopId}")
    public ResponseEntity<? extends ResponseDto> getAllPortfolioByShop(@PathVariable Long flowerShopId) {
        List<Portfolio> portfolios = portfolioService.getAllPortfolioByShop(flowerShopId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }


    // 인기 꽃 상품 정보 (5개)
//    @ApiOperation(value = "인기 꽃 상품")
//    @GetMapping("/hot")
//    public ResponseEntity<?extends ResponseDto> getHotPorfolio() {
//        User user = userService.getMyInfo();
//        locationService.getLocation(address);
//        porfolioService.getHotPorfolio(user);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
