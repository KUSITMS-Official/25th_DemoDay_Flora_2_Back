package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.FlowerShopDto;
import com.lookatthis.flora.model.*;
import com.lookatthis.flora.repository.FlowerShopRepository;
import com.lookatthis.flora.util.GeometryUtil;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FlowerShopService {

    private final FlowerShopRepository flowerShopRepository;
    private final EntityManager em;
    // Sort sort = Sort.by(Sort.Direction.DESC, userAddress);

    public FlowerShop createFlowerShop(FlowerShopDto flowerShopDto) throws ParseException {
        FlowerShop flowerShop = flowerShopDto.toFlowerShop();
        return flowerShopRepository.save(flowerShop);
    }

    public List<FlowerShop> getFlowerShops() {

        List<FlowerShop> flowerShops = flowerShopRepository.findAll();
        return flowerShops;

    }

    public Optional<FlowerShop> getFlowerShop(Long shopId) {

        Optional<FlowerShop> flowerShop = flowerShopRepository.findById(shopId);
        return flowerShop;

    }

    // 반경 내에 있는 꽃집
    @Transactional(readOnly = true)
    public List<FlowerShop> getNearByFlowerShops(Double latitude, Double longitude, Double distance) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT * "
                        + "FROM flower_shop "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point)", FlowerShop.class);

        List<FlowerShop> flowerShops = query.getResultList();
        return flowerShops;
    }

    // 사용자 위치 기준 꽃집 거리순 정렬
    @Transactional(readOnly = true)
    public List<FlowerShop> getSortByNearFlowerShops(Double latitude, Double longitude) {
        Query query = em.createNativeQuery("SELECT *,"
                        + "(6371*acos(cos(radians(" + latitude + "))*cos(radians(flower_shop_latitude))*cos(radians(flower_shop_longitude)"
                        + "-radians(" + longitude + "))+sin(radians(" + latitude + "))*sin(radians(flower_shop_latitude))))"
                        + "AS distance "
                        + "FROM flower_shop "
                        + "ORDER BY distance", FlowerShop.class);

        List<FlowerShop> flowerShops = query.getResultList();
        return flowerShops;
    }

    // 사용자 위치 기반 인기 꽃집
    @Transactional(readOnly = true)
    public List<FlowerShop> getHotFlowerShops(Double latitude, Double longitude) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, 5.0, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, 5.0, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT * "
                        + "FROM flower_shop "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", flower_shop_point) ORDER BY clip_count ", FlowerShop.class)
                .setMaxResults(5);

        List<FlowerShop> flowerShops = query.getResultList();
        return flowerShops;
    }

}
