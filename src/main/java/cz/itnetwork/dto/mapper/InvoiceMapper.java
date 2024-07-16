package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface InvoiceMapper {
    InvoiceEntity toEntity(InvoiceDTO invoiceDTO);

    InvoiceDTO toDTO(InvoiceEntity invoiceEntity);

    @Mapping(target = "buyer", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "product", ignore = true)
    void updateInvoiceEntity(InvoiceDTO source, @MappingTarget InvoiceEntity target);

    void updateInvoiceDTO(InvoiceDTO source, @MappingTarget InvoiceDTO target);
}
