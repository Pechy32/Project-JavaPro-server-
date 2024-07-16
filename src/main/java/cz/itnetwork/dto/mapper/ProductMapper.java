package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.ProductDTO;
import cz.itnetwork.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(ProductDTO source);
    ProductDTO toDTO(ProductEntity source);
    void updateProductEntity(ProductDTO source, @MappingTarget ProductEntity target);
}
