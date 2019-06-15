package com.spring.store.mappers;

import com.spring.store.dao.entities.AdminEntity;
import com.spring.store.dao.models.AdminModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.List;

@Mapper(uses = {ProductMapper.class, RatesMapper.class}, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface AdminMapper {

    @Mapping(target = "base64Image", ignore = true)
    AdminModel toModel(AdminEntity entity);

    List<AdminModel> toModels(List<AdminEntity> entities);

    AdminEntity toEntity(AdminModel model);

    List<AdminEntity> toEntities(List<AdminModel> models);

}
