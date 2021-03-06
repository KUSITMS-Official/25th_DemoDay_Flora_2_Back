package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.ReviewDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.PortfolioRepository;
import com.lookatthis.flora.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final PortfolioRepository portfolioRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 저장
    @Transactional
    public Object createReview(User user, ReviewDto reviewDto) {
        Portfolio portfolio = portfolioRepository.findById(reviewDto.getPortfolioId()).orElseThrow();
        FlowerShop flowerShop = portfolio.getFlowerShop();
        Review review = Review.builder()
                .content(reviewDto.getContent())
                .score(reviewDto.getScore())
                .user(user)
                .portfolio(portfolio)
                .flowerShop(flowerShop)
                .build();
        portfolio.setPortfolioReview(reviewDto.getScore());
        flowerShop.setFlowerShopReview(reviewDto.getScore());
        return reviewRepository.save(review);
    }

    // 리뷰 이미지 업로드
    @Transactional
    public Object setReviewImage(Long reviewId, String imgPath, String imgName) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(null);
        review.setReviewImages(imgPath);
        return review;
    }

    // 꽃집 리뷰 조회
    public List<Review> getFlowerShopReviews(Long flowerShopId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return reviewRepository.findAllByFlowerShopId(flowerShopId, sort);
    }

    // 포트폴리오 리뷰 조회
    public List<Review> getPortfolioReviews(Long portfolioId) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return reviewRepository.findAllByPortfolioId(portfolioId, sort);
    }

    // 사용자 리뷰 조회
    public List<Review> getReviews(User user) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        return reviewRepository.findAllByUserId(user.getId(), sort);
    }

}
