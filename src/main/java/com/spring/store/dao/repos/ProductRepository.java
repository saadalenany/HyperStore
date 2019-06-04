package com.spring.store.dao.repos;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.entities.ProductEntity;
import org.springframework.data.domain.Pageable;
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

    @Query("SELECT p FROM ProductEntity p WHERE p.rate >= ?1")
    List<ProductEntity> getByRate(Integer rate);

    @Query("SELECT p FROM ProductEntity p WHERE p.rate >= ?1 AND p.id <> ?2")
    List<ProductEntity> getByRateAsPage(Integer rate, String exceptProduct, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.discountPrice >= ?1")
    List<ProductEntity> getByDiscount(Integer discountPrice);

    @Query("SELECT p FROM ProductEntity p WHERE p.price >= ?1 AND p.price < ?2")
    List<ProductEntity> getByPriceBetweenAsPage(Integer low, Integer high, Pageable pageable);

    @Query("SELECT p FROM ProductEntity p WHERE p.price >= ?1 AND p.price < ?2")
    List<ProductEntity> getByPriceBetween(Integer low, Integer high);

    List<ProductEntity> findByCategory(CategoryEntity category, Pageable pageable);

    List<ProductEntity> findByCategory(CategoryEntity category);

    @Query("SELECT COUNT(p) FROM ProductEntity")
    Integer getProductCount();

}
