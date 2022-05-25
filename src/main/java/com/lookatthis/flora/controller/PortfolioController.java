package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.*;
import com.lookatthis.flora.model.Color;
import com.lookatthis.flora.model.Portfolio;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.PortfolioService;
import com.lookatthis.flora.service.S3Service;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/portfolio")
@Api(tags = {"Portfolio API"})
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final UserService userService;
    private final S3Service s3Service;

    // 꽃 상품 추가
    @ApiOperation(value = "꽃 상품 추가")
    @PostMapping("/create")
    public ResponseEntity<Object> createPortfolio(@RequestBody PortfolioDto portfolioDto) {
        return ResponseEntity.ok(portfolioService.createPortfolio(portfolioDto));
    }

    // 꽃 상품 이미지 업로드
    @ApiOperation(value = "꽃 상품 이미지 업로드")
    @PostMapping("/images")
    public ResponseEntity<Object> imageUpload(@RequestParam(name = "portfolioId") Long portfolioId, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();

        return ResponseEntity.ok(portfolioService.setPortfolioImage(portfolioId, imgPath, imgName));
    }

    // 꽃 상품 할인 수정
    @ApiOperation(value = "꽃 상품 할인 수정")
    @PostMapping("/discount")
    public ResponseEntity<Object> updateDiscountPortfolio(@RequestParam(name = "portfolioId") Long portfolioId, @RequestParam(name = "discount") int discount) {
        return ResponseEntity.ok(portfolioService.updateDiscountPortfolio(portfolioId, discount));
    }

    // 전체 꽃 상품 정보 조회
    @ApiOperation(value = "전체 꽃 상품 조회")
    @GetMapping("/")
    public ResponseEntity<? extends ResponseDto> getPortfolios() {
        List<Portfolio> portfolios = portfolioService.getPortfolios();
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 꽃 상품 ID로 꽃 상품 정보 조회
    @ApiOperation(value = "꽃 상품 ID로 꽃 상품 정보 조회")
    @GetMapping("/{portfolioId}")
    public ResponseEntity<? extends ResponseDto> getPortfoliosByItem(@PathVariable Long portfolioId) {
        Optional<Portfolio> portfolio = portfolioService.getPortfoliosByItem(portfolioId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolio));
    }

    // 꽃집 ID로 꽃 상품 정보 조회
    @ApiOperation(value = "꽃집 ID로 꽃 상품 정보 조회")
    @GetMapping("/shop/{flowerShopId}")
    public ResponseEntity<? extends ResponseDto> getPortfoliosByShop(@PathVariable Long flowerShopId) {
        List<Portfolio> portfolios = portfolioService.getPortfoliosByShop(flowerShopId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 인기 꽃 상품 정보 (5개)
    @ApiOperation(value = "인기 꽃 상품")
    @GetMapping("/hot")
    public ResponseEntity<?extends ResponseDto> getHotPorftolios() {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<Portfolio> portfolios = portfolioService.getHotPortfolios(point.getY(), point.getX());
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 할인 꽃 상품 정보 (5개)
    @ApiOperation(value = "할인 꽃 상품")
    @GetMapping("/sale")
    public ResponseEntity<?extends ResponseDto> getDiscountPortfolios() {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<Portfolio> portfolios = portfolioService.getDiscountPortfolios(point.getY(), point.getX());
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

    // 피드
    @ApiOperation(value = "피드 보기(필터[거리, 색상, 가격], 정렬[최신순, 인기순, 가격순, 거리순, 리뷰 평가 높은 순])")
    @GetMapping("/filter")
    public ResponseEntity<?extends ResponseDto> getFilterPortpolios(@RequestParam(name = "distance", required = false) Double distance,
                                                                    @RequestParam(name = "color", required = false) Color color,
                                                                    @RequestParam(name = "startprice", required = false) Integer startPrice,
                                                                    @RequestParam(name = "endprice", required = false) Integer endPrice,
                                                                    @RequestParam(name = "sort", required = false) String sort) {
        User user = userService.getMyInfo();
        Point point = user.getUserPoint();
        List<Portfolio> portfolios = portfolioService.getFilterPortfolios(point.getY(), point.getX(), distance, color, startPrice, endPrice, sort);
        return ResponseEntity.ok().body(new CommonResponseDto<>(portfolios));
    }

}
