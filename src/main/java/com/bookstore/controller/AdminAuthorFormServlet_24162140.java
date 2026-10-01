package com.bookstore.controller;

import com.bookstore.entity.Author_24162140;
import com.bookstore.service.AuthorService_24162140;
import com.bookstore.service.impl.AuthorServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Servlet form thêm/sửa tác giả (admin)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/admin/author-form")
public class AdminAuthorFormServlet_24162140 extends HttpServlet {

    private final AuthorService_24162140 authorService = new AuthorServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        Author_24162140 author = null;
        if (idParam != null && !idParam.isBlank()) {
            try { author = authorService.getById(Integer.parseInt(idParam)); }
            catch (NumberFormatException ignored) {}
        }
        req.setAttribute("author", author);
        req.setAttribute("mode", author == null ? "create" : "edit");
        req.getRequestDispatcher("/WEB-INF/views/admin/author-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam   = req.getParameter("authorId");
        String name      = req.getParameter("authorName");
        String dobParam  = req.getParameter("dateOfBirth");

        Author_24162140 author = (idParam != null && !idParam.isBlank())
                ? authorService.getById(Integer.parseInt(idParam))
                : new Author_24162140();

        if (author == null) author = new Author_24162140();

        author.setAuthorName(name);
        try { author.setDateOfBirth(LocalDate.parse(dobParam)); } catch (Exception ignored) {}

        if (author.getAuthorId() == null) {
            authorService.create(author);
        } else {
            authorService.update(author);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/authors");
    }
}