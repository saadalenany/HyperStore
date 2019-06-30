package com.spring.store.service.api;

import com.spring.store.dao.models.PaymentModel;

import java.util.List;

public interface PaymentService {

    PaymentModel save(PaymentModel model);

    List<PaymentModel> list();

    List<PaymentModel> listByAdminAsPage(String adminId, Integer page, Integer size);

    List<PaymentModel> listByAdmin(String adminId);

    PaymentModel get(String rateId);

    PaymentModel delete(String rateId);

    List<PaymentModel> getByProduct(String productId);

    List<PaymentModel> getSubmittedByLastHour();

    List<PaymentModel> getApprovedByLastHour();
}
