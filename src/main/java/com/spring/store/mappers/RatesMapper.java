package com.spring.store.mappers;

import com.spring.store.dao.entities.RatesEntity;
import com.spring.store.dao.models.RatesModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.List;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface RatesMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "admin.id", target = "adminId")
    RatesModel toModel(RatesEntity entity);

    List<RatesModel> toModels(List<RatesEntity> entities);

    @Mapping(source = "productId", target = "product.id")
    @Mapping(source = "adminId", target = "admin.id")
    RatesEntity toEntity(RatesModel model);

    List<RatesEntity> toEntities(List<RatesModel> models);

}
