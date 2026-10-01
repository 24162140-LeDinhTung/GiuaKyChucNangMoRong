package com.bookstore.controller;

import com.bookstore.entity.User_24162140;
import com.bookstore.service.RatingService_24162140;
import com.bookstore.service.impl.RatingServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

/**
 * Servlet thêm / cập nhật review
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/add-review")
public class AddReviewServlet_24162140 extends HttpServlet {

    private final RatingService_24162140 ratingService = new RatingServiceImpl_24162140();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User_24162140 user = (User_24162140) session.getAttribute("user");

        String bookidParam = req.getParameter("bookid");
        String ratingParam = req.getParameter("rating");
        String reviewText  = req.getParameter("reviewText");

        if (bookidParam == null || ratingParam == null) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        try {
            Integer bookid = Integer.parseInt(bookidParam);
            Integer rating = Integer.parseInt(ratingParam);

            ratingService.addOrUpdateReview(user.getId(), bookid, rating, reviewText);

            // Quay lại trang chi tiết
            resp.sendRedirect(req.getContextPath() + "/book-detail?id=" + bookid);

        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}