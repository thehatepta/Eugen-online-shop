package com.eugen.onlineshop.web.productServlets;

import com.eugen.onlineshop.entity.Product;
import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.util.ProductUtil;
import com.eugen.onlineshop.util.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.List;

import static com.eugen.onlineshop.util.ProductUtil.getProductForAddition;

public class ProductAdditionServlet extends HttpServlet {

    private ProductService productAdditionService = ServiceLocator.getService(ProductService.class);
    private List<String> sessionList;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public ProductAdditionServlet() {
    }

    public ProductAdditionServlet(ProductService productsService, List<String> sessionList) {
        this.productAdditionService = productsService;
        this.sessionList = sessionList;
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        Writer writer = resp.getWriter();

        pageGenerator.addProductPageGeneration(writer);
    }


    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){

        productAdditionService.add(getProductForAddition(req));

        resp.sendRedirect("/products");
    }

}
