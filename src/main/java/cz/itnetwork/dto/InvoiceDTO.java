package cz.itnetwork.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import cz.itnetwork.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDTO {

    @JsonProperty("_id")
    private Long id;

    private int invoiceNumber;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate issued;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    private ProductDTO product;

    private Long price;

    private int vat;

    private String note;

    private PersonDTO buyer;

    private PersonDTO seller;
}
