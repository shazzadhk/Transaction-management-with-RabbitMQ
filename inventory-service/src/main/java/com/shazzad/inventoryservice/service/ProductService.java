package com.shazzad.inventoryservice.service;

import com.shazzad.inventoryservice.model.Product;
import com.shazzad.inventoryservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product){
        log.info("saving product with product : {}",product);
       return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getSingleProduct(Long id) {
        return productRepository.findById(id).get();
    }
}
