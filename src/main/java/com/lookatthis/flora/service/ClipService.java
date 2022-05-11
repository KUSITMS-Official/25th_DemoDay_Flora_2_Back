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

    @Transactional
    public List<ClipShop> findShopByUserId(Long userId) {
        return clipShopRepository.findAllByUserId(userId);
    }

    @Transactional
    public List<ClipItem> findItemByUserId(Long userId) {
        return clipItemRepository.findAllByUserId(userId);
    }

    @Transactional
    public ClipShop createShop(User user, Long shopId) {
        FlowerShop flowerShop = flowerShopRepository.findById(shopId).get();
        ClipShop clipShop = ClipShop.builder().user(user).flowerShop(flowerShop).build();

        flowerShop.setClipCount(flowerShop.getClipCount() + 1); // 찜 수 증가

        return clipShopRepository.save(clipShop);
    }

    @Transactional
    public void deleteShop(Long userId, Long flowerShopId) {
        clipShopRepository.deleteByUserIdAndFlowerShopId(userId, flowerShopId);
        FlowerShop flowerShop = flowerShopRepository.findById(flowerShopId).get();
        flowerShop.setClipCount(flowerShop.getClipCount() - 1); // 찜 수 감소

    }

    @Transactional
    public ClipItem createItem(User user, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
        ClipItem clipItem = ClipItem.builder().user(user).portfolio(portfolio).build();

        portfolio.setClipCount(portfolio.getClipCount() + 1); // 찜 수 증가

        return clipItemRepository.save(clipItem);
    }

    @Transactional
    public void deleteItem(Long userId, Long portfolioId) {
        clipItemRepository.deleteByUserIdAndPortfolioId(userId, portfolioId);
        Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
        portfolio.setClipCount(portfolio.getClipCount() - 1); // 찜 수 감소

    }
}
