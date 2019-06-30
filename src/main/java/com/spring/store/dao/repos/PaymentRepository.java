package com.spring.store.dao.repos;

import com.spring.store.dao.entities.PaymentEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {

    @Query("SELECT r FROM PaymentEntity r WHERE r.product.id = ?1")
    List<PaymentEntity> findByProduct(String productId);

    @Query("SELECT r FROM PaymentEntity r JOIN ProductEntity p ON p.id = r.product.id AND p.admin.id = ?1")
    List<PaymentEntity> getByAdmin(String adminId);

    @Query("SELECT r FROM PaymentEntity r JOIN ProductEntity p ON p.id = r.product.id AND p.admin.id = ?1")
    List<PaymentEntity> getByAdminAsPage(String adminId, Pageable pageable);

    @Query(value = "SELECT * FROM `payment` WHERE `buy_date` >= DATE_SUB(NOW(),INTERVAL 1 DAY)", nativeQuery = true)
    List<PaymentEntity> getBySubmittedLastHour();

    @Query(value = "SELECT * FROM `payment` WHERE `paid_date` >= DATE_SUB(NOW(),INTERVAL 1 DAY)", nativeQuery = true)
    List<PaymentEntity> getByApprovedLastHour();

    @Modifying
    @Query("DELETE FROM PaymentEntity r WHERE r.id = ?1")
    void deleteById(String id);

}
