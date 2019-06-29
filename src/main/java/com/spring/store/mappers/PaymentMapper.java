package com.spring.store.mappers;

import com.spring.store.dao.entities.PaymentEntity;
import com.spring.store.dao.models.PaymentModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.List;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface PaymentMapper {

    @Mapping(source = "product.id", target = "productId")
    @Mapping(ignore = true, target = "productModel")
    PaymentModel toModel(PaymentEntity entity);

    List<PaymentModel> toModels(List<PaymentEntity> entities);

    @Mapping(source = "productId", target = "product.id")
    PaymentEntity toEntity(PaymentModel model);

    List<PaymentEntity> toEntities(List<PaymentModel> models);

}
