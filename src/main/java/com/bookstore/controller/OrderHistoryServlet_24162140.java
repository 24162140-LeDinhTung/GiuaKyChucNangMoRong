package com.bookstore.controller;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.OrderStatus_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.service.OrderService_24162140;
import com.bookstore.service.impl.OrderServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Servlet lịch sử đơn hàng + chi tiết + lọc theo trạng thái
 * GET /my-orders?status=PENDING
 * GET /order-detail?id=...
 * MSSV: 24162140 - Lê Đình Tùng
 */
@WebServlet({"/my-orders", "/order-detail"})
public class OrderHistoryServlet_24162140 extends HttpServlet {

    private final OrderService_24162140 orderService = new OrderServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = getUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();

        if ("/my-orders".equals(path)) {
            showMyOrders(req, resp, user);
        } else if ("/order-detail".equals(path)) {
            showOrderDetail(req, resp, user);
        }
    }

    private void showMyOrders(HttpServletRequest req, HttpServletResponse resp,
                               User_24162140 user) throws ServletException, IOException {

        // Lấy status filter
        String statusParam = req.getParameter("status");
        OrderStatus_24162140 filterStatus = null;
        if (statusParam != null && !statusParam.isBlank()) {
            try {
                filterStatus = OrderStatus_24162140.valueOf(statusParam);
            } catch (IllegalArgumentException ignored) {}
        }

        // Lấy danh sách đơn
        List<Order_24162140> orders;
        if (filterStatus != null) {
            orders = orderService.getOrdersByUserAndStatus(user.getId(), filterStatus);
        } else {
            orders = orderService.getOrdersByUser(user.getId());
        }

        // Đếm theo trạng thái
        Map<OrderStatus_24162140, Long> statusCounts =
                orderService.countByStatus(user.getId());
        long totalOrders = orderService.countByUser(user.getId());

        req.setAttribute("orders", orders);
        req.setAttribute("filterStatus", filterStatus);
        req.setAttribute("statusCounts", statusCounts);
        req.setAttribute("totalOrders", totalOrders);
        req.setAttribute("allStatuses", OrderStatus_24162140.values());

        req.getRequestDispatcher("/WEB-INF/views/my-orders.jsp").forward(req, resp);
    }

    private void showOrderDetail(HttpServletRequest req, HttpServletResponse resp,
                                   User_24162140 user)
            throws ServletException, IOException {

        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendRedirect(req.getContextPath() + "/my-orders");
            return;
        }

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

    private User_24162140 getUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null ? (User_24162140) s.getAttribute("user") : null;
    }
}