package com.spring.store.dao.models;

import org.apache.tomcat.util.codec.binary.Base64;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ProductModel {

    private String id;

    private String categoryId;

    private String adminId;

    private String name;

    private Integer price;

    private byte[] image;

    private String base64Image;

    private LocalDateTime creationDate;

    private Integer discountPrice;

    private Integer quantity;

    private Integer rate;

    private List<RatesModel> rates;

    private List<PaymentModel> payments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        String replaced = price.toString().replace(",", "");
        price = Integer.parseInt(replaced);
        return price;
    }

    public void setPrice(Integer price) {
        String replaced = price.toString().replace(",", "");
        this.price = Integer.parseInt(replaced);
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBase64Image() {
        if (image != null) {
            byte[] imgBytesAsBase64 = Base64.encodeBase64(image);
            String imgDataAsBase64 = new String(imgBytesAsBase64);
            return "data:image/png;base64," + imgDataAsBase64;
        }
        return null;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
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
        String replaced = quantity.toString().replace(",", "");
        quantity = Integer.parseInt(replaced);
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        String replaced = quantity.toString().replace(",", "");
        this.quantity = Integer.parseInt(replaced);
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public List<RatesModel> getRates() {
        return rates;
    }

    public void setRates(List<RatesModel> rates) {
        this.rates = rates;
    }

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }

    @Override
    public String toString() {
        return "ProductModel{" +
                "id='" + id + '\'' +
                ", categoryId='" + categoryId + '\'' +
                ", adminId='" + adminId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image=" + Arrays.toString(image) +
                ", base64Image='" + base64Image + '\'' +
                ", creationDate=" + creationDate +
                ", discountPrice=" + discountPrice +
                ", quantity=" + quantity +
                ", rate=" + rate +
                ", rates=" + rates +
                ", payments=" + payments +
                '}';
    }
}
