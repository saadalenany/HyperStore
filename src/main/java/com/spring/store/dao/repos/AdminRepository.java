package com.spring.store.dao.repos;

import com.spring.store.dao.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,String> {

    @Query("SELECT a FROM AdminEntity a WHERE a.email = ?1 AND a.password = ?2")
    AdminEntity getByEmailAndPassword(String email, String password);

    @Query("SELECT a FROM AdminEntity a WHERE a.name = ?1 AND a.password = ?2")
    AdminEntity getByNameAndPassword(String name, String password);

    @Query(value = "SELECT `admin`.* FROM `admin` " +
            "JOIN `product` " +
            "JOIN `payment` " +
            "ON `admin`.`id` = `product`.`admin_id` " +
            "AND `product`.`id` = `payment`.`product_id` " +
            "AND `payment`.`buy_date` >= DATE_SUB(NOW(),INTERVAL 1 DAY) " +
            "GROUP BY `admin`.`id`",
            nativeQuery = true)
    List<AdminEntity> getSellersOfLastHour();

    AdminEntity getByName(String name);
}
