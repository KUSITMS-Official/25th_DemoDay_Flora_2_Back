package com.lookatthis.flora.service;

import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Portfolio;
import com.lookatthis.flora.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;

    public List<Portfolio> getPortfolios() {

        List<Portfolio> portfolios = portfolioRepository.findAll();
        return portfolios;

    }

    public Optional<Portfolio> getPortfolio(Long portfolioId) {

        Optional<Portfolio> portfolio = portfolioRepository.findById(portfolioId);
        return portfolio;

    }

    public List<Portfolio> getAllPortfolioByShop(Long flowerShopId) {
        List<Portfolio> portfolios = portfolioRepository.findAllByFlowerShopId(flowerShopId);
        return portfolios;
    }
}
