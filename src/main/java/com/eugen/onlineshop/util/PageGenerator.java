package com.eugen.onlineshop.util;

import com.eugen.onlineshop.entity.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PageGenerator {
    private static PageGenerator pageGenerator;
    private final Configuration configuration = new Configuration();

    public static PageGenerator instance() {
        if (pageGenerator == null) {
            pageGenerator = new PageGenerator();
        }
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, ?> data) {
        Writer stream = new StringWriter();

        try {
            configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));
            configuration.setDefaultEncoding("UTF-8");

            Template template = configuration.getTemplate(filename);
            template.process(data, stream);

        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();

    }

    public static void addProductPageGeneration(Writer writer) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(PageGenerator.class, "/templates");
        configuration.clearTemplateCache();
        Template template = configuration.getTemplate("/NewProduct.html");
        Map<String, Object> pageData = new HashMap<>();
        template.process(pageData, writer);
    }

    public static void generateProductEditPage(Product product, Writer writer) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(PageGenerator.class, "/templates");
        configuration.clearTemplateCache();
        Template template = configuration.getTemplate("/EditProduct.html");
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("product", product);
        template.process(pageData, writer);
    }

    public static void loginProductEditPage(Writer writer) throws IOException, TemplateException {
        Configuration configuration = new Configuration();
        configuration.setClassForTemplateLoading(PageGenerator.class, "/templates");
        configuration.clearTemplateCache();
        Template template = configuration.getTemplate("/LoginPage.html");
        Map<String, Object> pageData = new HashMap<>();
        template.process(pageData, writer);
    }
}
