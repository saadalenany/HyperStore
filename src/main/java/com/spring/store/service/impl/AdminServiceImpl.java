package com.spring.store.service.impl;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.models.AdminModel;
import com.spring.store.dao.repos.AdminRepository;
import com.spring.store.mappers.AdminMapper;
import com.spring.store.service.api.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;

    private final AdminMapper mapper;

    @Autowired
    public AdminServiceImpl(AdminRepository repository, AdminMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public AdminModel save(AdminModel model) {
        AdminEntity adminEntity = mapper.toEntity(model);
        AdminEntity savedEntity = repository.save(adminEntity);
        return mapper.toModel(savedEntity);
    }

    @Override
    public AdminModel get(String id) {
        AdminEntity adminEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Admin found with this id %s",id)));
        return mapper.toModel(adminEntity);
    }

    @Override
    public List<AdminModel> list() {
        List<AdminEntity> adminEntities = repository.findAll();
        return mapper.toModels(adminEntities);
    }

    @Override
    public AdminModel delete(String id) {
        AdminEntity adminEntity = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("No Admin found with this id %s",id)));
        repository.delete(adminEntity);
        return mapper.toModel(adminEntity);
    }

    @Override
    public AdminModel getByEmailAndPassword(String email, String password) {
        AdminEntity byEmailAndPassword = repository.getByEmailAndPassword(email, password);
        return mapper.toModel(byEmailAndPassword);
    }

    @Override
    public AdminModel getByUsernameAndPassword(String name, String password) {
        AdminEntity byUsernameAndPassword = repository.getByNameAndPassword(name, password);
        return mapper.toModel(byUsernameAndPassword);
    }

    @Override
    public AdminModel getByUsername(String username) {
        AdminEntity byName = repository.getByName(username);
        return mapper.toModel(byName);
    }

    @Override
    public List<AdminModel> getSellersOfLastHour() {
        List<AdminEntity> sellersOfLastHour = repository.getSellersOfLastHour();
        return mapper.toModels(sellersOfLastHour);
    }
}
