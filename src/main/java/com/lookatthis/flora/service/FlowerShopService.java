package com.lookatthis.flora.service;

import com.lookatthis.flora.dto.FlowerShopDto;
import com.lookatthis.flora.model.Direction;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Location;
import com.lookatthis.flora.model.User;
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

    @Transactional(readOnly = true)
    public List<FlowerShop> getNearByFlowerShops(Double latitude, Double longitude, Double distance, int count) {
        Location northEast = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.NORTHEAST.getBearing());
        Location southWest = GeometryUtil
                .calculate(latitude, longitude, distance, Direction.SOUTHWEST.getBearing());

        double x1 = northEast.getLatitude();
        double y1 = northEast.getLongitude();
        double x2 = southWest.getLatitude();
        double y2 = southWest.getLongitude();

        String pointFormat = String.format("'LINESTRING(%f %f, %f %f)')", x1, y1, x2, y2);
        Query query = em.createNativeQuery("SELECT f.id, f.shop_name, f.shop_address, "
                        + "f.shop_number, f.shop_open_time, f.shop_close_time, f.shop_rest_time, "
                        + "f.shop_image, f.point"
                        + "FROM flowershop AS f "
                        + "WHERE MBRContains(ST_LINESTRINGFROMTEXT(" + pointFormat + ", f.point)", FlowerShop.class)
                .setMaxResults(count);

        List<FlowerShop> flowerShops = query.getResultList();
        return flowerShops;
    }
}
