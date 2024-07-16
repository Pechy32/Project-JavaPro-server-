package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.ProductDTO;
import cz.itnetwork.entity.ProductEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Eclipse Adoptium)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toEntity(ProductDTO source) {
        if ( source == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( source.getId() );
        productEntity.setName( source.getName() );

        return productEntity;
    }

    @Override
    public ProductDTO toDTO(ProductEntity source) {
        if ( source == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setId( source.getId() );
        productDTO.setName( source.getName() );

        return productDTO;
    }

    @Override
    public void updateProductEntity(ProductDTO source, ProductEntity target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setName( source.getName() );
    }
}
