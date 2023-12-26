package com.example.securityformlogin.services.impls;

import com.example.securityformlogin.models.Product;
import com.example.securityformlogin.repositories.ProductRepository;
import com.example.securityformlogin.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

}
