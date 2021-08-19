package com.eugen.onlineshop.web.productServlets;

import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.util.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AllProductServlet extends HttpServlet {
    private ProductService productService = ServiceLocator.getService(ProductService.class);
    private List<String> sessionList;

    private PageGenerator pageGenerator = PageGenerator.instance();

    public AllProductServlet(ProductService productsService, List<String> sessionList) {
        this.productService = productsService;
        this.sessionList = sessionList;
    }

    public AllProductServlet() {
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", productService.findAll());

        String page = pageGenerator.getPage("MainPage.html", parametersMap);
        resp.getWriter().println(page);
    }
}
