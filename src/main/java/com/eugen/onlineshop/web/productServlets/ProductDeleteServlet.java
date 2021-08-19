package com.eugen.onlineshop.web.productServlets;

import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.util.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductDeleteServlet extends HttpServlet {
    private ProductService productEditService  = ServiceLocator.getService(ProductService.class);
    private List<String> sessionList;
    private PageGenerator pageGenerator = PageGenerator.instance();


    public ProductDeleteServlet(ProductService productsService, List<String> sessionList) {
        this.productEditService = productsService;
        this.sessionList = sessionList;
    }

    public ProductDeleteServlet() {
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

        productEditService.deleteById(Integer.parseInt(req.getParameter("id")));
        resp.sendRedirect("/products");

    }
}
