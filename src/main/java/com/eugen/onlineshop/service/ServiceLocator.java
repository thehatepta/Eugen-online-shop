package com.eugen.onlineshop.service;

import com.eugen.onlineshop.dao.DaoProduct;
import com.eugen.onlineshop.dao.DaoUser;
import com.eugen.onlineshop.jdbc.JdbcConnector;
import com.eugen.onlineshop.util.PropertiesLoader;
import com.eugen.onlineshop.web.filter.SecurityFilter;


import java.util.*;

public class ServiceLocator {
    private static final Map<Class<?>, Object> SERVICES = new HashMap<>();

    static {
        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.load("application.properties");

        List<String> sessionList = new ArrayList<>();

        JdbcConnector jdbcConnector = new JdbcConnector(properties);
        DaoProduct daoProduct = new DaoProduct(jdbcConnector);
        DaoUser daoUser = new DaoUser(jdbcConnector);

        ProductService productsService = new ProductService(daoProduct);
        UserService userService  = new UserService(sessionList, daoUser);

        SecurityFilter securityFilter = new SecurityFilter(userService);

        addService(ProductService.class, productsService);
        addService(UserService.class, userService);
    }

    public static <T> T getService(Class<T> serviceType) {
        return serviceType.cast(SERVICES.get(serviceType));
    }

    public static void addService(Class<?> serviceName, Object service) {
        SERVICES.put(serviceName, service);
    }
}
