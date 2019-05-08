package com.spring.store.dao.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(exclude = "products")
@Getter
@Setter
@Entity
@Table(name = "admin")
public class AdminEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @Column(name="name")
    private String name;

    @Column(name="phone")
    private String phone;

    @Column(name="email")
    private String email;

    @Column(name="email_verified_at")
    private LocalDateTime emailVerifiedAt;

    @Column(name="password")
    private String password;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

}
