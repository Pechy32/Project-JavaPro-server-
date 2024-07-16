package cz.itnetwork.service;

import cz.itnetwork.dto.ProductDTO;
import cz.itnetwork.dto.ProductStatisticsDTO;

import java.util.List;

public interface ProductService {
    /**
     * get all products from the database
     * @return a List of products
     */
    List<ProductDTO> getAll();

    /**
     * create a product and save it to the database
     * @return created product
     */
    ProductDTO addProduct(ProductDTO product);

    /**
     * update and save product to the database
     * @param id product id
     * @param productDTO data to update
     * @return updated product
     */
    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    /**
     * delete a product from the database
     * @param id product id
     */
    void deleteProduct(Long id);

    /**
     * get product statistics
     * @return product statistics (totalTurnover and current year turnover for each)
     */
    List<ProductStatisticsDTO> getStatistics();

}
