package com.lookatthis.flora.service;

import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.FlowerRepository;
import com.lookatthis.flora.repository.FlowerShopRepository;
import com.lookatthis.flora.repository.SearchRepository;
import com.lookatthis.flora.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final FlowerRepository flowerRepository;
    private final FlowerShopRepository flowerShopRepository;
    private final SearchRepository searchRepository;
    private final EntityManager em;

    // 꽃 저장
    public Flower createFlower(String flowerName) {
        Flower flower = Flower.builder()
                .flowerName(flowerName).build();
        return flowerRepository.save(flower);
    }

    // 검색 (꽃 상품 by 꽃)
    public List<Portfolio> getSearchFlowerPortfolios(User user, String searchWord, String word, Double distance, Color color, Integer startPrice, Integer endPrice, String sort) {
        if(distance == null) distance = 5.0;
        if(startPrice == null) startPrice = 0;
        if(endPrice == null) endPrice = Integer.MAX_VALUE;
        if(sort == null) sort = "created_date";
        Double latitude = user.getUserPoint().getX();
        Double longitude = user.getUserPoint().getY();
        Flower flower = flowerRepository.findByFlowerName(word);
        if(flower == null) throw new RuntimeException("검색결과가 없습니다.");
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
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id AND p.flower_id = :flower "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class)
                    .setParameter("flower", flower.getId());
        }
        else {
            query = em.createNativeQuery("SELECT p.portfolio_id, p.portfolio_name, p.portfolio_image, "
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id AND p.color = :color AND p.flower_id = :flower "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class)
                    .setParameter("color", color.toString())
                    .setParameter("flower", flower.getId());

        }
        List<Portfolio> portfolios = query.getResultList();

        // 검색어 저장
        Search search = Search.builder()
                .searchWord(searchWord)
                .user(user)
                .build();
        searchRepository.save(search);
        return portfolios;
    }

    // 검색 (꽃집 by 주소)
    public List<Portfolio> getSearchAddressPortfolios(User user, String searchWord, String word, Double distance, Color color, Integer startPrice, Integer endPrice, String sort) {
        if(distance == null) distance = 5.0;
        if(startPrice == null) startPrice = 0;
        if(endPrice == null) endPrice = Integer.MAX_VALUE;
        if(sort == null) sort = "created_date";
        Double latitude = user.getUserPoint().getX();
        Double longitude = user.getUserPoint().getY();
        List<FlowerShop> flowerShops = flowerShopRepository.findByFlowerShopAddressContaining(word);
        if(flowerShops.isEmpty()) throw new RuntimeException("검색결과가 없습니다.");
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
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id AND p.flower_shop_id IN :flowerShops "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class)
                    .setParameter("flowerShops", flowerShops);
        }
        else {
            query = em.createNativeQuery("SELECT p.portfolio_id, p.portfolio_name, p.portfolio_image, "
                            + "p.portfolio_description, p.portfolio_price, p.color, p.clip_count, p.created_date, p.last_modified_date, p.flower_shop_id, p.flower_id "
                            + "FROM flower_shop AS f, portfolio AS p "
                            + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) "
                            + "AND f.flower_shop_id = p.flower_shop_id AND p.color = :color AND p.flower_shop_id IN :flowerShops "
                            + "AND p.portfolio_price BETWEEN " + startPrice + " AND " + endPrice + " ORDER BY " + sort, Portfolio.class)
                    .setParameter("color", color.toString())
                    .setParameter("flowerShops", flowerShops);

        }
        List<Portfolio> portfolios = query.getResultList();

        // 검색어 저장
        Search search = Search.builder()
                .searchWord(searchWord)
                .user(user)
                .build();
        searchRepository.save(search);
        return portfolios;
    }

    // 검색어 조회
    public List<Search> getSearchWords(User user) {
        return searchRepository.findAllByUserId(user.getId());
    }

    // 검색어 전체 삭제
    @Transactional
    public void deleteAll(User user) {
        searchRepository.deleteAllByUserId(user.getId());
    }

    // 특정 검색어 삭제
    @Transactional
    public void delete(User user, String search) {
        searchRepository.deleteByUserIdAndSearchWord(user.getId(), search);
    }

}
