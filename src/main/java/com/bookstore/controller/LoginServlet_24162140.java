package com.bookstore.controller;

import com.bookstore.entity.User_24162140;
import com.bookstore.service.UserService_24162140;
import com.bookstore.service.impl.UserServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet đăng nhập
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/login")
public class LoginServlet_24162140 extends HttpServlet {

    private final UserService_24162140 userService = new UserServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email    = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập email và mật khẩu");
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            return;
        }

        try {
            User_24162140 user = userService.login(email, password);

            if (user == null) {
                req.setAttribute("error", "Email hoặc mật khẩu không đúng");
                req.setAttribute("email", email);
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
                return;
            }

            // Đăng nhập thành công → tạo session
            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(30 * 60); // 30 phút

            resp.sendRedirect(req.getContextPath() + "/home");

        } catch (IllegalStateException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
        }
    }
}