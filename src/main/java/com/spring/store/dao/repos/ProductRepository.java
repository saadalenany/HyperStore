package com.spring.store.dao.repos;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,String> {

    @Query("SELECT p FROM ProductEntity p WHERE p.creationDate >= ?1 AND p.creationDate < CURDATE()")
    List<ProductEntity> getProductsFromLast7Days(LocalDateTime time);

    @Query("SELECT p FROM ProductEntity p WHERE p.creationDate >= ?1")
    List<ProductEntity> getProductsFromSpecificDate(LocalDateTime time);

    List<ProductEntity> getProductsByAdmin(AdminEntity admin);

    List<ProductEntity> getProductsByCategory(CategoryEntity category);

}
