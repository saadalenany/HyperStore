package com.spring.store.service.impl;

import com.spring.store.dao.entities.PaymentEntity;
import com.spring.store.dao.models.PaymentModel;
import com.spring.store.dao.repos.PaymentRepository;
import com.spring.store.mappers.PaymentMapper;
import com.spring.store.service.api.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
    }

    @Transactional
    @Override
    public PaymentModel save(PaymentModel model) {
        PaymentEntity entity = paymentMapper.toEntity(model);
        return paymentMapper.toModel(paymentRepository.save(entity));
    }

    @Override
    public List<PaymentModel> list() {
        return paymentMapper.toModels(paymentRepository.findAll());
    }

    @Override
    public List<PaymentModel> listByAdminAsPage(String adminId, Integer page, Integer size) {
        Pageable pageWithElements = PageRequest.of(page, size);
        List<PaymentEntity> byAdmin = paymentRepository.getByAdminAsPage(adminId, pageWithElements);
        return paymentMapper.toModels(byAdmin);
    }

    @Override
    public List<PaymentModel> listByAdmin(String adminId) {
        List<PaymentEntity> byAdmin = paymentRepository.getByAdmin(adminId);
        return paymentMapper.toModels(byAdmin);
    }

    @Override
    public PaymentModel get(String paymentId) {
        return paymentMapper.toModel(paymentRepository.findById(paymentId).orElseThrow(() ->
                new RuntimeException(String.format("No Payment found with this ID %s", paymentId))));
    }

    @Override
    public PaymentModel delete(String paymentId) {
        PaymentEntity entity = paymentRepository.findById(paymentId).orElseThrow(() ->
                new RuntimeException(String.format("No Payment found with this ID %s", paymentId)));
        paymentRepository.delete(entity);
        return paymentMapper.toModel(entity);
    }

    @Override
    public List<PaymentModel> getByProduct(String productId) {
        List<PaymentEntity> byProduct = paymentRepository.findByProduct(productId);
        return paymentMapper.toModels(byProduct);
    }

    @Override
    public List<PaymentModel> getSubmittedByLastHour() {
        List<PaymentEntity> bySubmittedLastHour = paymentRepository.getBySubmittedLastHour();
        return paymentMapper.toModels(bySubmittedLastHour);
    }

    @Override
    public List<PaymentModel> getApprovedByLastHour() {
        List<PaymentEntity> byApprovedLastHour = paymentRepository.getByApprovedLastHour();
        return paymentMapper.toModels(byApprovedLastHour);
    }

}
