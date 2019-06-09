package com.spring.store.dao.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "favourites")
public class FavouritesEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity admin) {
        this.admin = admin;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }
}
