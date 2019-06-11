package com.spring.store.service.impl;

import com.spring.store.dao.entities.RatesEntity;
import com.spring.store.dao.models.RatesModel;
import com.spring.store.dao.repos.RatesRepository;
import com.spring.store.mappers.RatesMapper;
import com.spring.store.service.api.RatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RatesServiceImpl implements RatesService {

    private final RatesRepository ratesRepository;

    private final RatesMapper ratesMapper;

    @Autowired
    public RatesServiceImpl(RatesRepository ratesRepository, RatesMapper ratesMapper) {
        this.ratesRepository = ratesRepository;
        this.ratesMapper = ratesMapper;
    }

    @Transactional
    @Override
    public RatesModel save(RatesModel model) {
        RatesEntity entity = ratesMapper.toEntity(model);
        return ratesMapper.toModel(ratesRepository.save(entity));
    }

    @Override
    public List<RatesModel> list() {
        return ratesMapper.toModels(ratesRepository.findAll());
    }

    @Override
    public RatesModel get(String rateId) {
        return ratesMapper.toModel(ratesRepository.findById(rateId).orElseThrow(() ->
                new RuntimeException(String.format("No Rate review found with this ID %s", rateId))));
    }

    @Override
    public RatesModel delete(String rateId) {
        RatesEntity ratesEntity = ratesRepository.findById(rateId).orElseThrow(() -> new RuntimeException(String.format("No Rate review found with this ID %s", rateId)));
        ratesRepository.delete(ratesEntity);
        return ratesMapper.toModel(ratesEntity);
    }
}
