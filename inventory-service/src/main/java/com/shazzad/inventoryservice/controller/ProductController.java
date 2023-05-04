package com.shazzad.inventoryservice.controller;

import com.shazzad.inventoryservice.model.Product;
import com.shazzad.inventoryservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Product> getAProduct(@PathVariable("id") Long id){
        return new ResponseEntity<>(productService.getSingleProduct(id),HttpStatus.OK);
    }


}
