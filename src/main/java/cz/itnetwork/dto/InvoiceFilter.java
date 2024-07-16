package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class InvoiceFilter {

    private Long buyerId;
    private Long sellerId;
    private Long productId; // changed from product to productId (also changed in filter component on front-end)
    private Long minPrice;
    private Long maxPrice;
    private Integer limit = Integer.MAX_VALUE;

    private LocalDate dateFrom;
    private LocalDate dateTo;
}
