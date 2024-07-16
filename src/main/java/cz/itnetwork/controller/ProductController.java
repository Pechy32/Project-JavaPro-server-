package cz.itnetwork.controller;

import cz.itnetwork.dto.ProductDTO;
import cz.itnetwork.dto.ProductStatisticsDTO;
import cz.itnetwork.service.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/products")
    public List<ProductDTO> getAllProducts(){
        return productService.getAll();
    }

    @PostMapping("/products")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO){
        return productService.addProduct(productDTO);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteProduct(@PathVariable Long productId, HttpServletResponse response){
        productService.deleteProduct(productId);
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @GetMapping("/products/statistics")
    public List<ProductStatisticsDTO> getProductStatistics(){
        return productService.getStatistics();
    }
}
