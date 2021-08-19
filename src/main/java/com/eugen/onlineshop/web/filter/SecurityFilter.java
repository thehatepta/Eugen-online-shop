package com.eugen.onlineshop.web.filter;


import com.eugen.onlineshop.service.ServiceLocator;
import com.eugen.onlineshop.service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SecurityFilter implements Filter {

    private UserService userService = ServiceLocator.getService(UserService.class);;

    public SecurityFilter() {
    }

    public SecurityFilter(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String path = httpServletRequest.getRequestURI();
        if (path.equals("/login")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-token")) {
                    if (userService.isTokenExist(cookie.getValue())) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
        }
        httpServletResponse.sendRedirect("/login");
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void destroy() {

    }
}
