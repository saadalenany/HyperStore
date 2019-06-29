package com.spring.store.dao.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "uuid2")
    @Column(name = "id")
    private String id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @ManyToOne
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

    @Column(name = "reserved")
    private Integer reserved;

    @Column(name = "rate")
    private Integer rate;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RatesEntity> rates = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentEntity> payments = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Integer discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getReserved() {
        return reserved;
    }

    public void setReserved(Integer reserved) {
        this.reserved = reserved;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity newCategory) {
        this.category = newCategory;
    }

    public AdminEntity getAdmin() {
        return admin;
    }

    public void setAdmin(AdminEntity newAdmin) {
        this.admin = newAdmin;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<RatesEntity> getRates() {
        return rates;
    }

    public void setRates(List<RatesEntity> newRates) {
        this.rates.clear();
        if (newRates != null) {
            newRates.forEach(pro -> pro.setProduct(this));
            this.rates.addAll(newRates);
        }
    }

    public List<PaymentEntity> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentEntity> newPayments) {
        this.payments.clear();
        if (newPayments != null) {
            newPayments.forEach(pro -> pro.setProduct(this));
            this.payments.addAll(newPayments);
        }
    }
}
