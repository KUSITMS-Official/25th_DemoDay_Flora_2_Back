package com.lookatthis.flora.repository;

import com.lookatthis.flora.model.ClipItem;
import com.lookatthis.flora.model.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Long> {

}
