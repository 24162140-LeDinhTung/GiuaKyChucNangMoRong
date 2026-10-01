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
 * Servlet trang chủ — hiển thị sách phân trang 6 sách/trang
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@WebServlet(urlPatterns = {"/home", ""})
public class HomeServlet_24162140 extends HttpServlet {

    private static final int PAGE_SIZE = 6;

    private final BookService_24162140 bookService = new BookServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Bắt buộc đăng nhập
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Lấy số trang
        int page = 1;
        String pageParam = req.getParameter("page");
        if (pageParam != null && !pageParam.isBlank()) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException ignored) {}
        }

        int totalPages = bookService.getTotalPages(PAGE_SIZE);
        if (page < 1) page = 1;
        if (totalPages > 0 && page > totalPages) page = totalPages;

        // Lấy dữ liệu
        List<Book_24162140> books = bookService.getPage(page, PAGE_SIZE);

        // Đẩy ra JSP
        req.setAttribute("books", books);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("pageSize", PAGE_SIZE);

        req.getRequestDispatcher("/WEB-INF/views/home.jsp").forward(req, resp);
    }
}