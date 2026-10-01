package com.bookstore.dao;

import com.bookstore.entity.Cart_24162140;
import com.bookstore.entity.CartItem_24162140;
import java.util.List;

/**
 * DAO cho Cart và CartItem
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface CartDAO_24162140 {

    /** Lấy hoặc tạo giỏ cho user */
    Cart_24162140 getOrCreateCart(Integer userId);

    /** Lấy giỏ theo user */
    Cart_24162140 findByUserId(Integer userId);

    /** Lấy danh sách items của giỏ */
    List<CartItem_24162140> findItems(Integer cartId);

    /** Tìm item theo cartId + bookId */
    CartItem_24162140 findItem(Integer cartId, Integer bookId);

    /** Thêm hoặc cập nhật item */
    CartItem_24162140 saveItem(CartItem_24162140 item);

    /** Xóa item */
    void deleteItem(Integer cartItemId);

    /** Xóa toàn bộ items của giỏ */
    void clearCart(Integer cartId);

    /** Đếm tổng số lượng sách trong giỏ */
    long countTotalItems(Integer cartId);
}