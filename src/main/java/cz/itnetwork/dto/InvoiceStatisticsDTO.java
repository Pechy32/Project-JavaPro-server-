package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceStatisticsDTO {
    private int year;
    private Long invoicesCount;
    private Long invoicesSum;
    private String topProduct;
    private Long topProductSum;
}




