package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.PortfolioDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.FlowerRepository;
import com.lookatthis.flora.repository.FlowerShopRepository;
import com.lookatthis.flora.repository.PortfolioRepository;
import com.lookatthis.flora.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final FlowerShopRepository flowerShopRepository;
    private final FlowerRepository flowerRepository;
    private final PortfolioRepository portfolioRepository;
    private final EntityManager em;

    public Portfolio createPortfolio(PortfolioDto portfolioDto) {
        FlowerShop flowerShop = flowerShopRepository.findById(portfolioDto.getFlowerShopId()).orElseThrow();
        Flower flower = flowerRepository.findById(portfolioDto.getFlowerId()).orElseThrow();
        Portfolio portfolio = Portfolio.builder()
                .portfolioName(portfolioDto.getPortfolioName())
                .price(portfolioDto.getPrice())
                .color(portfolioDto.getColor())
                .flowerShop(flowerShop)
                .flower(flower)
                .portfolioDescription(portfolioDto.getPortfolioDescription())
                .build();
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
        List<Portfolio> portfolios = portfolioRepository.findAllByFlowerShopId(flowerShopId);
        return portfolios;
    }

    // 사용자 위치 기반 인기 꽃 상품
    @Transactional(readOnly = true)
    public List<Portfolio> getHotPortfolios(Double latitude, Double longitude) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 5.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 5.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT p.portfolio_id, p.portfolio_name, p.portfolio_image, "
                        + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                        + "FROM flower_shop AS f, portfolio AS p "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", f.flower_shop_point) AND f.flower_shop_id = p.flower_shop_id "
                        + "ORDER BY p.clip_count", Portfolio.class)
                .setMaxResults(5);

        List<Portfolio> portfolios = query.getResultList();
        return portfolios;
    }

    // 사용자 위치 기반 피드
    public List<Portfolio> getFilterPortfolios(Double latitude, Double longitude, Double distance, Color color, Integer startPrice, Integer endPrice, String sort) {

        if(distance == null) distance = 5.0;
        if(startPrice == null) startPrice = 0;
        if(endPrice == null) endPrice = Integer.MAX_VALUE;
        if(sort == null) sort = "created_date";

        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query;
        if(color == null) {
            query = em.createNativeQuery("SELECT p.portfolio_id, p.portfolio_name, p.portfolio_image, "
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id , p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class);
        }
        else {
            query = em.createNativeQuery("SELECT p.portfolio_id, p.portfolio_name, p.portfolio_image, "
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id AND p.color = :color "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class)
                    .setParameter("color", color.toString());

        }
        List<Portfolio> portfolios = query.getResultList();
        return portfolios;
    }

}
