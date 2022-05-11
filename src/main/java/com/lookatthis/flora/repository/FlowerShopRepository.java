package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.FlowerShop;
import com.lookatthis.flora.model.Location;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerShopRepository extends JpaRepository<FlowerShop, Long> {

}
