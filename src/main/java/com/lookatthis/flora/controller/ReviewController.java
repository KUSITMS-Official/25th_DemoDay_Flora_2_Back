package com.lookatthis.flora.controller;

import com.lookatthis.flora.dto.*;
import com.lookatthis.flora.model.Review;
import com.lookatthis.flora.model.User;
import com.lookatthis.flora.service.ReviewService;
import com.lookatthis.flora.service.S3Service;
import com.lookatthis.flora.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/review")
@Api(tags = {"Review API"})
public class ReviewController {

    private final UserService userService;
    private final ReviewService reviewService;
    private final S3Service s3Service;

    // 리뷰 저장
    @ApiOperation(value = "리뷰 저장")
    @PostMapping("/create")
    public ResponseEntity<?extends ResponseDto> createReview(@RequestBody ReviewDto reviewDto) {
        User user = userService.getMyInfo();
        return ResponseEntity.ok().body(new CommonResponseDto<>(reviewService.createReview(user, reviewDto)));
    }

    // 리뷰 이미지 업로드
    @ApiOperation(value = "리뷰 이미지 업로드")
    @PostMapping("/images")
    public ResponseEntity<Object> imageUpload(@RequestParam(name = "reviewId") Long reviewId, MultipartFile file) throws IOException {
        String imgPath = s3Service.upload(file);
        String imgName = file.getOriginalFilename();

        return ResponseEntity.ok(reviewService.setReviewImage(reviewId, imgPath, imgName));
    }

    // 꽃집 리뷰 조회
    @ApiOperation(value = "꽃집 리뷰 조회")
    @GetMapping("/flowershop/{flowerShopId}")
    public ResponseEntity<?extends ResponseDto> getFlowerShopReviews(@PathVariable Long flowerShopId) {
        List<Review> reviews = reviewService.getFlowerShopReviews(flowerShopId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(reviews));
    }


    // 포트폴리오 리뷰 조회
    @ApiOperation(value = "포트폴리오 리뷰 조회")
    @GetMapping("/portfolio/{portfolioId}")
    public ResponseEntity<?extends ResponseDto> getPortfolioReviews(@PathVariable Long portfolioId) {
        List<Review> reviews = reviewService.getPortfolioReviews(portfolioId);
        return ResponseEntity.ok().body(new CommonResponseDto<>(reviews));
    }

    // 사용자 리뷰 조회
    @ApiOperation(value = "사용자 리뷰 조회")
    @GetMapping("/user")
    public ResponseEntity<?extends ResponseDto> getReviews() {
        User user = userService.getMyInfo();
        List<Review> reviews = reviewService.getReviews(user);
        return ResponseEntity.ok().body(new CommonResponseDto<>(reviews));
    }

}
