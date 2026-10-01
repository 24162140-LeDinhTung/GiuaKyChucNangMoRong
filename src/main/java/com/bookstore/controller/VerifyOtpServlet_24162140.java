package com.bookstore.controller;

import com.bookstore.service.UserService_24162140;
import com.bookstore.service.impl.UserServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet xác nhận OTP
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/verify-otp")
public class VerifyOtpServlet_24162140 extends HttpServlet {

    private final UserService_24162140 userService = new UserServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String otp   = req.getParameter("otp");
        String action = req.getParameter("action");
        
        if ("resend".equals(action)) {
            try {
                userService.resendOtp(email);
                req.setAttribute("success", "Đã gửi lại OTP mới đến email của bạn");
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
            }
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        // Nếu không có email từ form, lấy từ session
        if (email == null || email.isBlank()) {
            HttpSession session = req.getSession(false);
            if (session != null) email = (String) session.getAttribute("pendingEmail");
        }

        if (email == null || otp == null || otp.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập OTP");
            req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
            return;
        }

        boolean ok = userService.activate(email, otp);

        if (ok) {
            HttpSession session = req.getSession();
            session.removeAttribute("pendingEmail");
            req.getSession().setAttribute("successMessage",
                    "Kích hoạt thành công. Vui lòng đăng nhập.");
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("error", "OTP không đúng hoặc đã hết hạn");
            req.setAttribute("email", email);
            req.getRequestDispatcher("/WEB-INF/views/verify-otp.jsp").forward(req, resp);
        }
    }
}