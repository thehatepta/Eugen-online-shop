package com.eugen.onlineshop.web.validationServlets;

import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.service.UserService;
import com.eugen.onlineshop.util.PageGenerator;
import lombok.SneakyThrows;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class LoginServlet extends HttpServlet {

    private UserService userService = ServiceLocator.getService(UserService.class);
    private List<String> sessionList;

    private PageGenerator pageGenerator = PageGenerator.instance();

    public LoginServlet(UserService userService, List<String> sessionList) {
        this.userService = userService;
        this.sessionList = sessionList;
    }

    public LoginServlet() {
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        Writer writer = resp.getWriter();

        pageGenerator.loginProductEditPage(writer);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (userService.checkPassword(request.getParameter("name"), request.getParameter("password"))) {
            Cookie cookie = new Cookie("user-token", userService.generateToken());
            cookie.setPath("/");
            response.addCookie(cookie);
            response.sendRedirect("/products");
        }
    }
}
