package com.bookstore.controller;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.service.OrderService_24162140;
import com.bookstore.service.impl.OrderServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet lịch sử đơn hàng + chi tiết
 * GET /my-orders      → danh sách đơn
 * GET /order-detail   → chi tiết 1 đơn
 * MSSV: 24162140 - Lê Đình Tùng
 */
@WebServlet({"/my-orders", "/order-detail"})
public class OrderHistoryServlet_24162140 extends HttpServlet {

    private final OrderService_24162140 orderService = new OrderServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = getUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        String path = req.getServletPath();

        if ("/my-orders".equals(path)) {
            List<Order_24162140> orders = orderService.getOrdersByUser(user.getId());
            req.setAttribute("orders", orders);
            req.getRequestDispatcher("/WEB-INF/views/my-orders.jsp").forward(req, resp);

        } else if ("/order-detail".equals(path)) {
            String idParam = req.getParameter("id");
            if (idParam == null) { resp.sendRedirect(req.getContextPath() + "/my-orders"); return; }

            try {
                Integer orderId = Integer.parseInt(idParam);
                Order_24162140 order = orderService.getById(orderId);
                if (order == null || !order.getUserId().equals(user.getId())) {
                    resp.sendRedirect(req.getContextPath() + "/my-orders");
                    return;
                }
                req.setAttribute("order", order);
                req.getRequestDispatcher("/WEB-INF/views/order-detail.jsp").forward(req, resp);
            } catch (Exception e) {
                resp.sendRedirect(req.getContextPath() + "/my-orders");
            }
        }
    }

    private User_24162140 getUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null ? (User_24162140) s.getAttribute("user") : null;
    }
}