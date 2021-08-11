import com.eugen.onlineshop.PropertiesLoader;
import com.eugen.onlineshop.dao.DaoProduct;
import com.eugen.onlineshop.jdbc.JdbcConnector;
import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.web.ProductManagmentServlet;
import com.eugen.onlineshop.web.ProductServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class main {

    public static void main(String[] args) throws Exception {

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.load("application.properties");

        JdbcConnector jdbcConnector = new JdbcConnector(properties);
        DaoProduct daoProduct = new DaoProduct(jdbcConnector);
        ProductService productsService = new ProductService(daoProduct);

        List<String> sessionList = new ArrayList<>();

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new ProductServlet(productsService, sessionList)), "/products");
        context.addServlet(new ServletHolder(new ProductManagmentServlet(productsService, sessionList)), "/product/add");

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();
    }
}
