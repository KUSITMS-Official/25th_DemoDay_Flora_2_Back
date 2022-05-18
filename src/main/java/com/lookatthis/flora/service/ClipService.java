package com.lookatthis.flora.service;

import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.sampled.Clip;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClipService {

    private final ClipShopRepository clipShopRepository;
    private final ClipItemRepository clipItemRepository;
    private final FlowerShopRepository flowerShopRepository;
    private final PortfolioRepository portfolioRepository;

    // 사용자 꽃집 찜 목록 조회
    @Transactional
    public List<ClipShop> findShopByUserId(Long userId) {
        return clipShopRepository.findAllByUserId(userId);
    }

    // 사용자 꽃 상품 목록 조회
    @Transactional
    public List<ClipItem> findItemByUserId(Long userId) {
        return clipItemRepository.findAllByUserId(userId);
    }

    // 사용자 꽃집 찜 추가
    @Transactional
    public ClipShop createShopClip(User user, Long shopId) {
        FlowerShop flowerShop = flowerShopRepository.findById(shopId).get();
        ClipShop clipShop = ClipShop.builder().user(user).flowerShop(flowerShop).build();

        flowerShop.setClipCount(flowerShop.getClipCount() + 1); // 찜 수 증가

        return clipShopRepository.save(clipShop);
    }

    // 사용자 꽃집 찜 삭제
    @Transactional
    public void deleteShopClip(Long userId, Long flowerShopId) {
        clipShopRepository.deleteByUserIdAndFlowerShopId(userId, flowerShopId);
        FlowerShop flowerShop = flowerShopRepository.findById(flowerShopId).get();
        flowerShop.setClipCount(flowerShop.getClipCount() - 1); // 찜 수 감소

    }

    // 사용자 꽃 상품 찜 추가
    @Transactional
    public ClipItem createItemClip(User user, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
        ClipItem clipItem = ClipItem.builder().user(user).portfolio(portfolio).build();

        portfolio.setClipCount(portfolio.getClipCount() + 1); // 찜 수 증가

        return clipItemRepository.save(clipItem);
    }

    // 사용자 꽃 상품 찜 삭제
    @Transactional
    public void deleteItemClip(Long userId, Long portfolioId) {
        clipItemRepository.deleteByUserIdAndPortfolioId(userId, portfolioId);
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
        portfolio.setClipCount(portfolio.getClipCount() - 1); // 찜 수 감소

    }
}
