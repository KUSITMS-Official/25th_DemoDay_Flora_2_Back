package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.Search;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchRepository extends JpaRepository<Search, Long> {

    void deleteAllByUserId(Long userId);

    void deleteByUserIdAndSearchWord(Long userId, String search);

    List<Search> findAllByUserId(Long userId);
}
