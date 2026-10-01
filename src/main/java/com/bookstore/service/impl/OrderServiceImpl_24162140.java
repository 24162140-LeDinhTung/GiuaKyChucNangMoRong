package com.bookstore.service.impl;

import com.bookstore.dao.BookDAO_24162140;
import com.bookstore.dao.CartDAO_24162140;
import com.bookstore.dao.OrderDAO_24162140;
import com.bookstore.dao.impl.BookDAOImpl_24162140;
import com.bookstore.dao.impl.CartDAOImpl_24162140;
import com.bookstore.dao.impl.OrderDAOImpl_24162140;
import com.bookstore.entity.*;
import com.bookstore.service.OrderService_24162140;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Triển khai OrderService
 * MSSV: 24162140 - Lê Đình Tùng
 */
public class OrderServiceImpl_24162140 implements OrderService_24162140 {

    private final OrderDAO_24162140 orderDAO = new OrderDAOImpl_24162140();
    private final CartDAO_24162140 cartDAO = new CartDAOImpl_24162140();
    private final BookDAO_24162140 bookDAO = new BookDAOImpl_24162140();

    @Override
    public Order_24162140 placeOrderCOD(Integer userId,
                                          String shippingAddress,
                                          String phone,
                                          String note) {

        Cart_24162140 cart = cartDAO.findByUserId(userId);
        if (cart == null) {
            throw new IllegalStateException("Giỏ hàng trống");
        }

        List<CartItem_24162140> cartItems = cartDAO.findItems(cart.getCartId());
        if (cartItems.isEmpty()) {
            throw new IllegalStateException("Giỏ hàng trống");
        }

        // Tạo order
        Order_24162140 order = new Order_24162140();
        order.setUserId(userId);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus_24162140.PENDING);
        order.setPaymentMethod("COD");
        order.setShippingAddress(shippingAddress);
        order.setPhone(phone);
        order.setNote(note);

        // Tạo order items + tính tổng tiền
        List<OrderItem_24162140> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem_24162140 ci : cartItems) {
            Book_24162140 book = bookDAO.findById(ci.getBookId());
            if (book == null) continue;

            OrderItem_24162140 oi = new OrderItem_24162140();
            oi.setBookId(ci.getBookId());
            oi.setQuantity(ci.getQuantity());
            oi.setPrice(book.getPrice());

            total = total.add(book.getPrice()
                    .multiply(BigDecimal.valueOf(ci.getQuantity())));
            orderItems.add(oi);
        }

        order.setTotalAmount(total);

        // Lưu order + items
        Order_24162140 saved = orderDAO.createOrder(order, orderItems);

        // Xóa giỏ hàng
        cartDAO.clearCart(cart.getCartId());

        return saved;
    }

    @Override
    public Order_24162140 getById(Integer orderId) {
        Order_24162140 order = orderDAO.findById(orderId);
        if (order != null && order.getItems() != null) {
            for (OrderItem_24162140 item : order.getItems()) {
                item.setBook(bookDAO.findById(item.getBookId()));
            }
        }
        return order;
    }

    @Override
    public List<Order_24162140> getOrdersByUser(Integer userId) {
        return orderDAO.findByUserId(userId);
    }
}