package com.bookstore.controller;

import com.bookstore.entity.User_24162140;
import com.bookstore.service.UserService_24162140;
import com.bookstore.service.impl.UserServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet đăng ký tài khoản
 * GET  /register  → hiển thị form
 * POST /register  → xử lý đăng ký
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/register")
public class RegisterServlet_24162140 extends HttpServlet {

    private final UserService_24162140 userService = new UserServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email    = req.getParameter("email");
        String fullname = req.getParameter("fullname");
        String phone    = req.getParameter("phone");
        String password = req.getParameter("password");
        String confirm  = req.getParameter("confirm");

        // Validate
        if (email == null || email.isBlank()
                || fullname == null || fullname.isBlank()
                || password == null || password.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập đầy đủ thông tin bắt buộc");
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        if (!password.equals(confirm)) {
            req.setAttribute("error", "Mật khẩu xác nhận không khớp");
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
            return;
        }

        try {
            User_24162140 user = userService.register(email, fullname, phone, password);

            // Lưu email vào session để dùng cho trang verify-otp
            HttpSession session = req.getSession();
            session.setAttribute("pendingEmail", user.getEmail());

            // Chuyển đến trang nhập OTP
            resp.sendRedirect(req.getContextPath() + "/verify-otp");

        } catch (IllegalArgumentException e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("email", email);
            req.setAttribute("fullname", fullname);
            req.setAttribute("phone", phone);
            req.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(req, resp);
        }
    }
}