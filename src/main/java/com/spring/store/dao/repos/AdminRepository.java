package com.spring.store.dao.repos;

import com.spring.store.dao.entities.AdminEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity,String> {

    @Query("SELECT a FROM AdminEntity a WHERE a.email = ?1 AND a.password = ?2")
    AdminEntity getByEmailAndPassword(String email, String password);

}
