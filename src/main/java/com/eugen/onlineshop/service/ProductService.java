package com.eugen.onlineshop.service;

import com.eugen.onlineshop.dao.DaoProductInterface;
import com.eugen.onlineshop.dao.DaoProduct;
import com.eugen.onlineshop.entity.Product;

import java.util.List;

public class ProductService {
    private final DaoProductInterface daoProduct;

    public ProductService(DaoProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    public List<Product> findAll() {
        return daoProduct.findAllProducts();
    }

    public Product getById(int id){
        return daoProduct.get(id);
    }

    public void add(Product product){
        daoProduct.add(product);
    }

    public void deleteById(int id){
        daoProduct.delete(id);
    }

    public void updateProduct(Product product){
        daoProduct.update(product);
    }
}
