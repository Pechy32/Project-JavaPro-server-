package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductStatisticsDTO {
    private Long productId;
    private String productName;
    private Long totalTurnover;
    private Long currentYearTurnover;
}
