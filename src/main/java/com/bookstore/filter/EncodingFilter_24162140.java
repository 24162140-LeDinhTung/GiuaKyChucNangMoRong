package com.bookstore.filter;

import jakarta.servlet.*;
import java.io.IOException;

/**
 * Filter đảm bảo request/response dùng UTF-8
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class EncodingFilter_24162140 implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}