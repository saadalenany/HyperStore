package com.spring.store.dao.repos;

import com.spring.store.dao.entities.RatesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatesRepository extends JpaRepository<RatesEntity, String> {

    @Query("SELECT r FROM RatesEntity r WHERE r.product.id = ?1")
    List<RatesEntity> findByProduct(String productId);

    @Modifying
    @Query("DELETE FROM RatesEntity r WHERE r.id = ?1")
    void deleteById(String id);
}
