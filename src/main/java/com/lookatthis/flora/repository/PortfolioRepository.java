package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

}
