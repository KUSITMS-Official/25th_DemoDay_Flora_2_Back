package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.ClipItem;
import com.lookatthis.flora.model.SearchItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SearchItemRepository extends JpaRepository<SearchItem, Long> {

}
