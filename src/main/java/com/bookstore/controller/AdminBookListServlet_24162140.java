package com.bookstore.controller;

import com.bookstore.entity.Book_24162140;
import com.bookstore.service.BookService_24162140;
import com.bookstore.service.impl.BookServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet danh sách sách admin (phân trang)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet("/admin/books")
public class AdminBookListServlet_24162140 extends HttpServlet {

    private static final int PAGE_SIZE = 6;
    private final BookService_24162140 bookService = new BookServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";

        int page = 1;
        try { page = Integer.parseInt(req.getParameter("page")); } catch (Exception ignored) {}

        int totalPages = bookService.getTotalPagesByKeyword(keyword, PAGE_SIZE);
        if (page < 1) page = 1;
        if (totalPages > 0 && page > totalPages) page = totalPages;

        List<Book_24162140> books = bookService.search(keyword, page, PAGE_SIZE);

        req.setAttribute("books", books);
        req.setAttribute("keyword", keyword);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", PAGE_SIZE);

        req.getRequestDispatcher("/WEB-INF/views/admin/book-list.jsp").forward(req, resp);
    }
}