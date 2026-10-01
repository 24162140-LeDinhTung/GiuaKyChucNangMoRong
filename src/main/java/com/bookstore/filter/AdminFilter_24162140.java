package com.bookstore.filter;

import com.bookstore.entity.User_24162140;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Filter chặn truy cập /admin/* nếu không phải admin
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class AdminFilter_24162140 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest  req  = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        User_24162140 user = (session != null)
                ? (User_24162140) session.getAttribute("user")
                : null;

        if (user != null && user.isAdmin()) {
            chain.doFilter(request, response);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}