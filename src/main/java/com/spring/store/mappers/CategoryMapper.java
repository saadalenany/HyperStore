package com.spring.store.mappers;

import com.spring.store.dao.entities.CategoryEntity;
import com.spring.store.dao.models.CategoryModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingInheritanceStrategy;

import java.util.List;

@Mapper(uses = ProductMapper.class, mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_ALL_FROM_CONFIG)
public interface CategoryMapper {

    CategoryModel toModel(CategoryEntity entity);

    List<CategoryModel> toModels(List<CategoryEntity> entities);

    CategoryEntity toEntity(CategoryModel model);

    List<CategoryEntity> toEntities(List<CategoryModel> models);

}
