package com.eugen.onlineshop.dao;

import com.eugen.onlineshop.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface {
   List<Product> findAllProducts();

   Product get(int id);

   void delete(int id);

   void update(Product product);
}
