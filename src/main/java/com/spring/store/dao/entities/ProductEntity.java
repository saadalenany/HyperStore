package com.spring.store.dao.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "admin_id")
    private AdminEntity admin;

    @Column(name="name")
    private String name;

    @Column(name="price")
    private Integer price;

    @Column(name="image")
    @Lob
    private byte[] image;

    @Column(name="creation_date")
    private LocalDateTime creationDate;

    @Column(name="discount_price")
    private Integer discountPrice;

    @Column(name="quantity")
    private Integer quantity;

}
