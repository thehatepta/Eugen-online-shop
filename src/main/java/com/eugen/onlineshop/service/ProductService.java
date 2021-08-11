package com.eugen.onlineshop.service;

import com.eugen.onlineshop.dao.DaoProduct;
import com.eugen.onlineshop.entity.Product;

import javax.servlet.http.HttpServlet;
import java.sql.SQLException;
import java.util.List;

public class ProductService extends HttpServlet {
    private final DaoProduct daoProduct;

    public ProductService(DaoProduct daoProduct) {
        this.daoProduct = daoProduct;
    }

    public List<Product> findAll() throws SQLException {
        return daoProduct.findAllProducts();
    }

    public Product getById(int id){
        return daoProduct.get(id);
    }

    public void deleteById(int id){
        daoProduct.delete(id);
    }

    public void updateProduct(Product product){
        daoProduct.update(product);
    }
}
