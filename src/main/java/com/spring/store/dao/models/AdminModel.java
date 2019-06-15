package com.spring.store.dao.models;

import org.apache.tomcat.util.codec.binary.Base64;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class AdminModel {

    private String id;

    private String name;

    private String phone;

    private String email;

    private byte[] image;

    private String base64Image;

    private LocalDateTime emailVerifiedAt;

    private String password;

    private String address;

    private List<ProductModel> products;

    private List<RatesModel> rates;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public LocalDateTime getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<RatesModel> getRates() {
        return rates;
    }

    public void setRates(List<RatesModel> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "AdminModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", image=" + Arrays.toString(image) +
                ", base64Image='" + base64Image + '\'' +
                ", emailVerifiedAt=" + emailVerifiedAt +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", products=" + products +
                ", rates=" + rates +
                '}';
    }
}
