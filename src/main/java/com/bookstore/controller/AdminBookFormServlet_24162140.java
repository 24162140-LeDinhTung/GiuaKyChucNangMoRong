package com.bookstore.controller;

import com.bookstore.entity.Author_24162140;
import com.bookstore.entity.Book_24162140;
import com.bookstore.service.AuthorService_24162140;
import com.bookstore.service.BookService_24162140;
import com.bookstore.service.impl.AuthorServiceImpl_24162140;
import com.bookstore.service.impl.BookServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet form thêm/sửa sách (admin)
 * GET  /admin/book-form?id=...  → hiển thị form
 * POST /admin/book-form         → lưu
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/admin/book-form")
public class AdminBookFormServlet_24162140 extends HttpServlet {

    private final BookService_24162140   bookService   = new BookServiceImpl_24162140();
    private final AuthorService_24162140 authorService = new AuthorServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        Book_24162140 book = null;
        if (idParam != null && !idParam.isBlank()) {
            try {
                book = bookService.getById(Integer.parseInt(idParam));
            } catch (NumberFormatException ignored) {}
        }

        req.setAttribute("book", book);
        req.setAttribute("allAuthors", authorService.getAll());
        req.setAttribute("mode", book == null ? "create" : "edit");
        req.getRequestDispatcher("/WEB-INF/views/admin/book-form.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idParam      = req.getParameter("bookid");
        String title        = req.getParameter("title");
        String isbnParam    = req.getParameter("isbn");
        String publisher    = req.getParameter("publisher");
        String priceParam   = req.getParameter("price");
        String desc         = req.getParameter("description");
        String pubDateParam = req.getParameter("publishDate");
        String coverImage   = req.getParameter("coverImage");
        String qtyParam     = req.getParameter("quantity");
        String[] authorIds  = req.getParameterValues("authorIds");

        Book_24162140 book = (idParam != null && !idParam.isBlank())
                ? bookService.getById(Integer.parseInt(idParam))
                : new Book_24162140();

        if (book == null) book = new Book_24162140();

        book.setTitle(title);
        book.setPublisher(publisher);
        book.setDescription(desc);
        book.setCoverImage(coverImage);

        try { book.setIsbn(Integer.parseInt(isbnParam)); } catch (Exception ignored) {}
        try { book.setPrice(new BigDecimal(priceParam)); } catch (Exception ignored) {}
        try { book.setQuantity(Integer.parseInt(qtyParam)); } catch (Exception ignored) {}
        try { book.setPublishDate(LocalDate.parse(pubDateParam)); } catch (Exception ignored) {}

        // Gán authors
        if (authorIds != null) {
            List<Author_24162140> selected = new ArrayList<>();
            for (String aid : authorIds) {
                try {
                    Author_24162140 a = authorService.getById(Integer.parseInt(aid));
                    if (a != null) selected.add(a);
                } catch (Exception ignored) {}
            }
            book.setAuthors(selected);
        }

        if (book.getBookid() == null) {
            bookService.create(book);
        } else {
            bookService.update(book);
        }

        resp.sendRedirect(req.getContextPath() + "/admin/books");
    }
}