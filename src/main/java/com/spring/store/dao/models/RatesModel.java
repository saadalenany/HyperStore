package com.spring.store.dao.models;

public class RatesModel {

    private String id;

    private String productId;

    private String adminId;

    private String review;

    private Integer star;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    @Override
    public String toString() {
        return "RatesModel{" +
                "id='" + id + '\'' +
                ", productId='" + productId + '\'' +
                ", adminId=" + adminId +
                ", review='" + review + '\'' +
                ", star=" + star +
                '}';
    }
}
