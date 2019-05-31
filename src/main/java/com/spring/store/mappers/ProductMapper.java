package com.spring.store.mappers;

import com.spring.store.dao.entities.ProductEntity;
import com.spring.store.dao.models.ProductModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.List;

@Mapper(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface ProductMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "admin.id", target = "adminId")
    @Mapping(target = "base64Image", ignore = true)
    ProductModel toModel(ProductEntity entity);

    List<ProductModel> toModels(List<ProductEntity> entities);

    @Mapping(source = "categoryId", target = "category.id")
    @Mapping(source = "adminId", target = "admin.id")
    ProductEntity toEntity(ProductModel model);

    List<ProductEntity> toEntities(List<ProductModel> models);

}
