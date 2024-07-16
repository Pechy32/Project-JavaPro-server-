package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonStatisticsDTO {
    private Long personId;
    private String personName;
    private Long revenue;
    private Long currentYearRevenue;
    private Long profit;
    private Long currentYearProfit;
    private String allTimeTopProduct;
    private Long allTimeTopProductSum;
    private String currentYearTopProduct;
    private Long currentYearTopProductSum;
}
