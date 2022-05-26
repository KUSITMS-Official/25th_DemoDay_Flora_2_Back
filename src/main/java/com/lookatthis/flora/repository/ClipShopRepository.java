package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.ClipShop;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClipShopRepository extends JpaRepository<ClipShop, Long> {

    void deleteByUserIdAndFlowerShopId(Long userId, Long flowerShopId);

    Optional<ClipShop> findByUserIdAndFlowerShopId(Long userId, Long flowerShopId);

    List<ClipShop> findAllByUserId(Long userId);

    boolean existsByUserIdAndFlowerShopId(Long userId, Long flowerShopId);
}
