package com.bookstore.controller;

import com.bookstore.entity.Book_24162140;
import com.bookstore.service.BookService_24162140;
import com.bookstore.service.RatingService_24162140;
import com.bookstore.service.impl.BookServiceImpl_24162140;
import com.bookstore.service.impl.RatingServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

/**
 * Servlet trang chi tiết sách
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/book-detail")
public class BookDetailServlet_24162140 extends HttpServlet {

    private final BookService_24162140   bookService   = new BookServiceImpl_24162140();
    private final RatingService_24162140 ratingService = new RatingServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        try {
            Integer bookid = Integer.parseInt(idParam);
            Book_24162140 book = bookService.getById(bookid);

            if (book == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }

            // Lấy danh sách review: [email, fullname, rating, review_text]
            List<Object[]> reviews = ratingService.getReviews(bookid);

            req.setAttribute("book", book);
            req.setAttribute("reviews", reviews);
            req.getRequestDispatcher("/WEB-INF/views/book-detail.jsp").forward(req, resp);

        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}