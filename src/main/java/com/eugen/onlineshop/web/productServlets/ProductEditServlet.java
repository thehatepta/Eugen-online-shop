package com.eugen.onlineshop.web.productServlets;

import com.eugen.onlineshop.entity.Product;
import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.util.ProductUtil;
import com.eugen.onlineshop.util.PageGenerator;
import freemarker.template.TemplateException;
import lombok.SneakyThrows;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class ProductEditServlet extends HttpServlet {

    private  ProductService productModificationService = ServiceLocator.getService(ProductService.class);;
    private List<String> sessionList;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public ProductEditServlet() {
    }

    public ProductEditServlet(ProductService productsService, List<String> sessionList) {
        this.productModificationService = productsService;
        this.sessionList = sessionList;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Writer writer = resp.getWriter();
        if (req.getParameter("id") == null) {
            throw new ServletException("No can be found id ");
        } else {
            int id = Integer.valueOf(req.getParameter("id"));
            Product product = productModificationService.getById(id);
            if (product == null) {
                throw new ServletException("No product with id " + id);
            }
            try {
                PageGenerator.generateProductEditPage(product, writer);
            } catch (TemplateException e) {
                throw new RuntimeException("Template Error", e);
            }
        }
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        productModificationService.updateProduct(ProductUtil.getProductFromRequest(req));
        resp.sendRedirect("/products");
    }
    

}
