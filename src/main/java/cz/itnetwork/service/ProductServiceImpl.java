package cz.itnetwork.service;

import cz.itnetwork.dto.ProductDTO;
import cz.itnetwork.dto.ProductStatisticsDTO;
import cz.itnetwork.dto.mapper.ProductMapper;
import cz.itnetwork.entity.ProductEntity;
import cz.itnetwork.entity.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        ProductEntity productEntity = productMapper.toEntity(productDTO);
        productRepository.saveAndFlush(productEntity);
        return productMapper.toDTO(productEntity);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        ProductEntity productEntity = fetchProduct(productId);
        productMapper.updateProductEntity(productDTO, productEntity);
        productRepository.saveAndFlush(productEntity);
        return productMapper.toDTO(productEntity);
    }

    @Override
    public void deleteProduct(Long id) {
        ProductEntity productEntity = fetchProduct(id);
        productRepository.delete(productEntity);
    }

    @Override
    public List<ProductStatisticsDTO> getStatistics() {
        return productRepository.getProductStatistics();
    }

    private ProductEntity fetchProduct(Long id) {
        return productRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}