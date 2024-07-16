package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.PersonDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 21.0.1 (Eclipse Adoptium)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public InvoiceEntity toEntity(InvoiceDTO invoiceDTO) {
        if ( invoiceDTO == null ) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        invoiceEntity.setId( invoiceDTO.getId() );
        invoiceEntity.setInvoiceNumber( invoiceDTO.getInvoiceNumber() );
        invoiceEntity.setIssued( invoiceDTO.getIssued() );
        invoiceEntity.setDueDate( invoiceDTO.getDueDate() );
        invoiceEntity.setPrice( invoiceDTO.getPrice() );
        invoiceEntity.setVat( invoiceDTO.getVat() );
        invoiceEntity.setNote( invoiceDTO.getNote() );
        invoiceEntity.setProduct( productMapper.toEntity( invoiceDTO.getProduct() ) );
        invoiceEntity.setBuyer( personDTOToPersonEntity( invoiceDTO.getBuyer() ) );
        invoiceEntity.setSeller( personDTOToPersonEntity( invoiceDTO.getSeller() ) );

        return invoiceEntity;
    }

    @Override
    public InvoiceDTO toDTO(InvoiceEntity invoiceEntity) {
        if ( invoiceEntity == null ) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setId( invoiceEntity.getId() );
        invoiceDTO.setInvoiceNumber( invoiceEntity.getInvoiceNumber() );
        invoiceDTO.setIssued( invoiceEntity.getIssued() );
        invoiceDTO.setDueDate( invoiceEntity.getDueDate() );
        invoiceDTO.setProduct( productMapper.toDTO( invoiceEntity.getProduct() ) );
        invoiceDTO.setPrice( invoiceEntity.getPrice() );
        invoiceDTO.setVat( invoiceEntity.getVat() );
        invoiceDTO.setNote( invoiceEntity.getNote() );
        invoiceDTO.setBuyer( personEntityToPersonDTO( invoiceEntity.getBuyer() ) );
        invoiceDTO.setSeller( personEntityToPersonDTO( invoiceEntity.getSeller() ) );

        return invoiceDTO;
    }

    @Override
    public void updateInvoiceEntity(InvoiceDTO source, InvoiceEntity target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setInvoiceNumber( source.getInvoiceNumber() );
        target.setIssued( source.getIssued() );
        target.setDueDate( source.getDueDate() );
        target.setPrice( source.getPrice() );
        target.setVat( source.getVat() );
        target.setNote( source.getNote() );
    }

    @Override
    public void updateInvoiceDTO(InvoiceDTO source, InvoiceDTO target) {
        if ( source == null ) {
            return;
        }

        target.setId( source.getId() );
        target.setInvoiceNumber( source.getInvoiceNumber() );
        target.setIssued( source.getIssued() );
        target.setDueDate( source.getDueDate() );
        target.setProduct( source.getProduct() );
        target.setPrice( source.getPrice() );
        target.setVat( source.getVat() );
        target.setNote( source.getNote() );
        target.setBuyer( source.getBuyer() );
        target.setSeller( source.getSeller() );
    }

    protected PersonEntity personDTOToPersonEntity(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        PersonEntity personEntity = new PersonEntity();

        if ( personDTO.getId() != null ) {
            personEntity.setId( personDTO.getId() );
        }
        personEntity.setName( personDTO.getName() );
        personEntity.setIdentificationNumber( personDTO.getIdentificationNumber() );
        personEntity.setTaxNumber( personDTO.getTaxNumber() );
        personEntity.setAccountNumber( personDTO.getAccountNumber() );
        personEntity.setBankCode( personDTO.getBankCode() );
        personEntity.setIban( personDTO.getIban() );
        personEntity.setTelephone( personDTO.getTelephone() );
        personEntity.setMail( personDTO.getMail() );
        personEntity.setStreet( personDTO.getStreet() );
        personEntity.setZip( personDTO.getZip() );
        personEntity.setCity( personDTO.getCity() );
        personEntity.setCountry( personDTO.getCountry() );
        personEntity.setNote( personDTO.getNote() );

        return personEntity;
    }

    protected PersonDTO personEntityToPersonDTO(PersonEntity personEntity) {
        if ( personEntity == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId( personEntity.getId() );
        personDTO.setName( personEntity.getName() );
        personDTO.setIdentificationNumber( personEntity.getIdentificationNumber() );
        personDTO.setTaxNumber( personEntity.getTaxNumber() );
        personDTO.setAccountNumber( personEntity.getAccountNumber() );
        personDTO.setBankCode( personEntity.getBankCode() );
        personDTO.setIban( personEntity.getIban() );
        personDTO.setTelephone( personEntity.getTelephone() );
        personDTO.setMail( personEntity.getMail() );
        personDTO.setStreet( personEntity.getStreet() );
        personDTO.setZip( personEntity.getZip() );
        personDTO.setCity( personEntity.getCity() );
        personDTO.setCountry( personEntity.getCountry() );
        personDTO.setNote( personEntity.getNote() );

        return personDTO;
    }
}
