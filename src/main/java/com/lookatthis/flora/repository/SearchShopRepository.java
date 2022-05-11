package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.ClipItem;
import com.lookatthis.flora.model.SearchShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SearchShopRepository extends JpaRepository<SearchShop, Long> {

}
