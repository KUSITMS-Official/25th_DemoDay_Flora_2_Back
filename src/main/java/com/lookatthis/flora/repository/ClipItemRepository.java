package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.ClipItem;
import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ClipItemRepository extends JpaRepository<ClipItem, Long> {

    Optional<ClipItem> findByUserIdAndPortfolioId(Long userId, Long portfolioId);

    void deleteByUserIdAndPortfolioId(Long userId, Long portfolioId);

    List<ClipItem> findAllByUserId(Long userId);
}
