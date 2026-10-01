package com.bookstore.controller;

import com.bookstore.service.BookService_24162140;
import com.bookstore.service.impl.BookServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet xóa sách (admin)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/admin/book-delete")
public class AdminBookDeleteServlet_24162140 extends HttpServlet {

    private final BookService_24162140 bookService = new BookServiceImpl_24162140();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            try {
                bookService.delete(Integer.parseInt(idParam));
            } catch (Exception ignored) {}
        }
        resp.sendRedirect(req.getContextPath() + "/admin/books");
    }
}