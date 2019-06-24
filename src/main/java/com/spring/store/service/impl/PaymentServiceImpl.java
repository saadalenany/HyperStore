package com.spring.store.service.impl;

import com.spring.store.dao.entities.PaymentEntity;
import com.spring.store.dao.models.PaymentModel;
import com.spring.store.dao.repos.PaymentRepository;
import com.spring.store.mappers.PaymentMapper;
import com.spring.store.service.api.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
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

}
