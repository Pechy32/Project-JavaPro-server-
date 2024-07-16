package cz.itnetwork.entity.repository;

import cz.itnetwork.dto.ProductStatisticsDTO;
import cz.itnetwork.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, PagingAndSortingRepository<ProductEntity, Long> {

    @Query(value = "SELECT NEW cz.itnetwork.dto.ProductStatisticsDTO("
            + "pr.id, " // id
            + "pr.name, " // name
            + "(SELECT SUM(i.price) FROM invoice i WHERE i.product = pr), " //total turnover
            + "(SELECT SUM(i.price) FROM invoice i WHERE i.product = pr AND YEAR(i.issued) = YEAR(CURRENT_DATE))" //current year turnover
            + ") "
            + "FROM product pr "
            + "LEFT JOIN pr.productInvoices i "
            + "GROUP BY pr.id, pr.name")
    List<ProductStatisticsDTO> getProductStatistics();
}
