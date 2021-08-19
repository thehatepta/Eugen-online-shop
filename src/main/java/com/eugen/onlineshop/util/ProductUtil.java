package com.eugen.onlineshop.util;

import com.eugen.onlineshop.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

public class ProductUtil {
    public static Product getProductFromRequest(HttpServletRequest req) throws ServletException {
        Product product;
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        double price = Double.parseDouble(priceString);
        int id = Integer.parseInt(req.getParameter("id"));

        product = new Product(id, name, price);
        return product;
    }

    public static Product getProductForAddition(HttpServletRequest req) throws ServletException {
        Product product;
        double price;
        String name = req.getParameter("name");
        String priceString = req.getParameter("price");
        try {
           price= Double.parseDouble(priceString);
        }catch(NumberFormatException e){
            throw new ServletException("price is not number");
        }

        product = new Product(name, price);
        return product;
    }
}
