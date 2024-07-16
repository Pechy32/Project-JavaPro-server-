package cz.itnetwork.entity;

import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import javax.annotation.processing.Generated;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductEntity.class)
public abstract class ProductEntity_ {

	public static volatile ListAttribute<ProductEntity, InvoiceEntity> productInvoices;
	public static volatile SingularAttribute<ProductEntity, String> name;
	public static volatile SingularAttribute<ProductEntity, Long> id;

	public static final String PRODUCT_INVOICES = "productInvoices";
	public static final String NAME = "name";
	public static final String ID = "id";

}

