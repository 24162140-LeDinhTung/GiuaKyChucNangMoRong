package com.bookstore.service;

import com.bookstore.entity.CartItem_24162140;
import java.math.BigDecimal;
import java.util.List;

/**
 * Service xử lý giỏ hàng
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface CartService_24162140 {

    /** Thêm sách vào giỏ. Nếu sách đã có, tăng số lượng. */
    void addToCart(Integer userId, Integer bookId, int quantity);

    /** Cập nhật số lượng 1 item */
    void updateQuantity(Integer userId, Integer cartItemId, int quantity);

    /** Xóa 1 item khỏi giỏ */
    void removeItem(Integer userId, Integer cartItemId);

    /** Xóa toàn bộ giỏ */
    void clearCart(Integer userId);

    /** Lấy danh sách items (đã load Book) */
    List<CartItem_24162140> getItems(Integer userId);

    /** Tổng tiền giỏ hàng */
    BigDecimal getTotal(Integer userId);

    /** Tổng số lượng sách */
    long getTotalItems(Integer userId);
}