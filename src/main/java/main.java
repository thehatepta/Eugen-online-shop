import com.eugen.onlineshop.util.PropertiesLoader;
import com.eugen.onlineshop.dao.DaoProduct;
import com.eugen.onlineshop.dao.DaoUser;
import com.eugen.onlineshop.jdbc.JdbcConnector;
import com.eugen.onlineshop.service.ProductService;
import com.eugen.onlineshop.service.UserService;
import com.eugen.onlineshop.web.filter.SecurityFilter;
import com.eugen.onlineshop.web.productServlets.ProductAdditionServlet;
import com.eugen.onlineshop.web.productServlets.ProductDeleteServlet;
import com.eugen.onlineshop.web.productServlets.ProductEditServlet;
import com.eugen.onlineshop.web.productServlets.AllProductServlet;
import com.eugen.onlineshop.web.validationServlets.LoginServlet;
import com.eugen.onlineshop.web.validationServlets.RegistrationServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Properties;

public class main {

    public static void main(String[] args) throws Exception {

        PropertiesLoader propertiesLoader = new PropertiesLoader();
        Properties properties = propertiesLoader.load("application.properties");

        List<String> sessionList = new ArrayList<>();

        JdbcConnector jdbcConnector = new JdbcConnector(properties);
        DaoProduct daoProduct = new DaoProduct(jdbcConnector);
        DaoUser daoUser = new DaoUser(jdbcConnector);

        ProductService productsService = new ProductService(daoProduct);
        UserService userService  = new UserService(sessionList, daoUser);

        SecurityFilter securityFilter = new SecurityFilter(userService);


      /*  ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new AllProductServlet(productsService, sessionList)), "/products");
        context.addServlet(new ServletHolder(new ProductAdditionServlet(productsService, sessionList)), "/product/manage");
        context.addServlet(new ServletHolder(new ProductEditServlet(productsService, sessionList)), "/product/edit");
        context.addServlet(new ServletHolder(new ProductDeleteServlet(productsService, sessionList)), "/product/delete");
        context.addServlet(new ServletHolder(new LoginServlet(userService, sessionList)), "/login");
        context.addServlet(new ServletHolder(new RegistrationServlet(userService, sessionList)), "/registration");

        context.addFilter(new FilterHolder(securityFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(context);
        server.start();*/
    }
}
