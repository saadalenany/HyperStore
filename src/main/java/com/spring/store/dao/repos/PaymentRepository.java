package com.spring.store.dao.repos;

import com.spring.store.dao.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    @Query("SELECT r FROM PaymentEntity r WHERE r.product.id = ?1")
    List<PaymentEntity> findByProduct(String productId);

    @Modifying
    @Query("DELETE FROM PaymentEntity r WHERE r.id = ?1")
    void deleteById(String id);

}
