package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.CommonResponseDto;
import com.lookatthis.flora.dto.FlowerShopDto;
import com.lookatthis.flora.dto.PortfolioDto;
import com.lookatthis.flora.dto.ResponseDto;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Portfolio;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.FlowerShopService;
import com.lookatthis.flora.service.PortfolioService;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/portfolio")
@Api(tags = {"Portfolio API"})
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;
    private final FlowerShopService flowerShopService;

    // 꽃 상품 추가
    @ApiOperation(value = "꽃 상품 추가")
    @PostMapping("/create")
    public ResponseEntity<Object> createFlowerShop(@RequestBody PortfolioDto portfolioDto) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioDto));
    }

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
    @ApiOperation(value = "인기 꽃 상품")
    @GetMapping("/hot")
    public ResponseEntity<?extends ResponseDto> getHotPorfolios() {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<Portfolio> portfolios = portfolioService.getHotPortfolios(point.getX(), point.getY());
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }
}
