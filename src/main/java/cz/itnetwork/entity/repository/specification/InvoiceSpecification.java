package cz.itnetwork.entity.repository.specification;

import cz.itnetwork.dto.InvoiceFilter;
import cz.itnetwork.entity.*;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class InvoiceSpecification implements Specification<InvoiceEntity> {

    private final InvoiceFilter filter;
    @Override
    public Predicate toPredicate(Root<InvoiceEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter.getBuyerId() != null){
            Join<InvoiceEntity, PersonEntity> invoiceJoinPerson = root.join(InvoiceEntity_.buyer);
            predicates.add(invoiceJoinPerson.get(InvoiceEntity_.ID).in(filter.getBuyerId()));
        }

        if (filter.getSellerId() != null){
            Join<InvoiceEntity, PersonEntity> invoiceJoinPerson = root.join(InvoiceEntity_.seller);
            predicates.add(invoiceJoinPerson.get(InvoiceEntity_.ID).in(filter.getSellerId()));
        }

        /* Product search by String (not using now)
        if (filter.getProduct() != null){
            String searchTerm = "%" + filter.getProduct().toLowerCase().trim() + "%";
            Join<InvoiceEntity, ProductEntity> productJoin = root.join(InvoiceEntity_.product);
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(productJoin.get(ProductEntity_.name)), searchTerm));
        }
         */

        if (filter.getProductId() != null){
            Join<InvoiceEntity, ProductEntity> productJoin = root.join(InvoiceEntity_.product);
            predicates.add(productJoin.get(ProductEntity_.id).in(filter.getProductId()));
        }

        if (filter.getMinPrice() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.price), (filter.getMinPrice())));
        }

        if (filter.getMaxPrice() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.price), (filter.getMaxPrice())));
        }

        if (filter.getDateFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(InvoiceEntity_.issued), filter.getDateFrom()));
        }

        if (filter.getDateTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(InvoiceEntity_.issued), filter.getDateTo()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));

    }
}
