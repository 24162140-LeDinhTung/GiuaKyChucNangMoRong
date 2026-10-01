package com.bookstore.controller;

import com.bookstore.entity.CartItem_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.service.CartService_24162140;
import com.bookstore.service.impl.CartServiceImpl_24162140;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * Servlet xử lý giỏ hàng
 * GET  /cart           → xem giỏ
 * POST /cart/add       → thêm
 * POST /cart/update    → cập nhật số lượng
 * POST /cart/remove    → xóa 1 item
 * POST /cart/clear     → xóa toàn bộ
 * MSSV: 24162140 - Lê Đình Tùng
 */
@WebServlet({"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartServlet_24162140 extends HttpServlet {

    private final CartService_24162140 cartService = new CartServiceImpl_24162140();

    private User_24162140 currentUser(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s != null ? (User_24162140) s.getAttribute("user") : null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();
        if ("/cart".equals(path)) {
            showCart(req, resp, user);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        User_24162140 user = currentUser(req);
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        String path = req.getServletPath();

        try {
            switch (path) {
                case "/cart/add": {
                    Integer bookId = Integer.parseInt(req.getParameter("bookId"));
                    int qty = 1;
                    try { qty = Integer.parseInt(req.getParameter("quantity")); }
                    catch (Exception ignored) {}

                    cartService.addToCart(user.getId(), bookId, qty);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    break;
                }
                case "/cart/update": {
                    Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                    int qty = Integer.parseInt(req.getParameter("quantity"));
                    cartService.updateQuantity(user.getId(), cartItemId, qty);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    break;
                }
                case "/cart/remove": {
                    Integer cartItemId = Integer.parseInt(req.getParameter("cartItemId"));
                    cartService.removeItem(user.getId(), cartItemId);
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    break;
                }
                case "/cart/clear": {
                    cartService.clearCart(user.getId());
                    resp.sendRedirect(req.getContextPath() + "/cart");
                    break;
                }
                default:
                    resp.sendRedirect(req.getContextPath() + "/cart");
            }
        } catch (Exception e) {
            req.setAttribute("error", e.getMessage());
            showCart(req, resp, user);
        }
    }

    private void showCart(HttpServletRequest req, HttpServletResponse resp,
                           User_24162140 user) throws ServletException, IOException {
        List<CartItem_24162140> items = cartService.getItems(user.getId());
        req.setAttribute("cartItems", items);
        req.setAttribute("cartTotal", cartService.getTotal(user.getId()));
        req.setAttribute("cartCount", cartService.getTotalItems(user.getId()));
        req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
    }
}