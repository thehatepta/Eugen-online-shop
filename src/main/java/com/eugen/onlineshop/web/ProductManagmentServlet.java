package com.eugen.onlineshop.web;

import com.eugen.onlineshop.service.ProductService;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagmentServlet extends HttpServlet {

    private final ProductService productManagmentService;
    private List<String> sessionList;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public ProductManagmentServlet(ProductService productsService, List<String> sessionList) {
        this.productManagmentService = productsService;
        this.sessionList = sessionList;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", productManagmentService.findAll());

        Writer writer = resp.getWriter();

        pageGenerator.addProduct(writer);
    }
}
