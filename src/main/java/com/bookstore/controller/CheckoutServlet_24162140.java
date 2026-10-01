package com.bookstore.controller;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.service.CartService_24162140;
import com.bookstore.service.OrderService_24162140;
import com.bookstore.service.impl.CartServiceImpl_24162140;
import com.bookstore.service.impl.OrderServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

/**
 * Servlet thanh toán COD
 * GET  /checkout  → hiển thị form
 * POST /checkout  → đặt hàng
 * MSSV: 24162140 - Lê Đình Tùng
 */
@WebServlet("/checkout")
public class CheckoutServlet_24162140 extends HttpServlet {

    private final CartService_24162140  cartService  = new CartServiceImpl_24162140();
    private final OrderService_24162140 orderService = new OrderServiceImpl_24162140();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = getUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        long count = cartService.getTotalItems(user.getId());
        if (count == 0) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        req.setAttribute("cartItems", cartService.getItems(user.getId()));
        req.setAttribute("cartTotal", cartService.getTotal(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = getUser(req);
        if (user == null) { resp.sendRedirect(req.getContextPath() + "/login"); return; }

        String address = req.getParameter("shippingAddress");
        String phone   = req.getParameter("phone");
        String note    = req.getParameter("note");

        if (address == null || address.isBlank()
                || phone == null || phone.isBlank()) {
            req.setAttribute("error", "Vui lòng nhập địa chỉ và số điện thoại");
            req.setAttribute("cartItems", cartService.getItems(user.getId()));
            req.setAttribute("cartTotal", cartService.getTotal(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
            return;
        }

        try {
            Order_24162140 order = orderService.placeOrderCOD(
                    user.getId(), address, phone, note);

            resp.sendRedirect(req.getContextPath()
                    + "/order-detail?id=" + order.getOrderId() + "&placed=true");
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            req.setAttribute("cartItems", cartService.getItems(user.getId()));
            req.setAttribute("cartTotal", cartService.getTotal(user.getId()));
            req.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(req, resp);
        }
    }

    private User_24162140 getUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null ? (User_24162140) s.getAttribute("user") : null;
    }
}