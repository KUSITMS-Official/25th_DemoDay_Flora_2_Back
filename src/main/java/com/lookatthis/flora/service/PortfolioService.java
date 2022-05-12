package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.PortfolioDto;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Portfolio;
import com.lookatthis.flora.repository.FlowerShopRepository;
import com.lookatthis.flora.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final FlowerShopRepository flowerShopRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional
    public Portfolio createPortfolio(PortfolioDto portfolioDto) {
        Portfolio portfolio = Portfolio.builder()
                .portfolioName(portfolioDto.getPortfolioName())
                .price(portfolioDto.getPrice())
                .color(portfolioDto.getColor())
                .portfolioDescription(portfolioDto.getPortfolioDescription())
                .build();
        FlowerShop flowerShop = flowerShopRepository.findById(portfolioDto.getFlowerShopId()).orElseThrow();
        flowerShop.getPortfolios().add(portfolio);
        return portfolioRepository.save(portfolio);
    }

    public List<Portfolio> getPortfolios() {

        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolios;

    }

    public Optional<Portfolio> getPortfolio(Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        return portfolio;

    }

    @Transactional
    public List<Portfolio> getAllPortfolioByShop(Long flowerShopId) {
        FlowerShop flowerShop = flowerShopRepository.findById(flowerShopId).orElseThrow();
        return flowerShop.getPortfolios();
    }
}
