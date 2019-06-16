package com.spring.store.service.api;

import com.spring.store.dao.models.RatesModel;

import java.util.List;

public interface RatesService {

    RatesModel save(RatesModel model);

    List<RatesModel> list();

    RatesModel get(String rateId);

    RatesModel delete(String rateId);

    List<RatesModel> getByProduct(String productId);

    List<RatesModel> getByProductAndAdmin(String productId, String adminId);
}
