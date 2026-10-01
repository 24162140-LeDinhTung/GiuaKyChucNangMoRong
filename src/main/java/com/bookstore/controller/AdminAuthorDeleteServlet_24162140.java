package com.bookstore.controller;

import com.bookstore.service.AuthorService_24162140;
import com.bookstore.service.impl.AuthorServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet xóa tác giả (admin)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/admin/author-delete")
public class AdminAuthorDeleteServlet_24162140 extends HttpServlet {

    private final AuthorService_24162140 authorService = new AuthorServiceImpl_24162140();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            try { authorService.delete(Integer.parseInt(idParam)); }
            catch (Exception ignored) {}
        }
        resp.sendRedirect(req.getContextPath() + "/admin/authors");
    }
}