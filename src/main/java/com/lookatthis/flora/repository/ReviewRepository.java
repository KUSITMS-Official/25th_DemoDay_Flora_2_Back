package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.Review;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByUserId(Long userId, Sort sort);

    List<Review> findAllByPortfolioId(Long portfolioId, Sort sort);

    List<Review> findAllByFlowerShopId(Long flowerShopId, Sort sort);
}
