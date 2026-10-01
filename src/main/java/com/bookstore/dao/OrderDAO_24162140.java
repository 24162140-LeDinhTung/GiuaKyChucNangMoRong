package com.bookstore.dao;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.OrderItem_24162140;
import java.util.List;

/**
 * DAO cho Order và OrderItem
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface OrderDAO_24162140 {

    /** Tạo đơn hàng (kèm items) trong 1 transaction */
    Order_24162140 createOrder(Order_24162140 order, List<OrderItem_24162140> items);

    /** Lấy đơn hàng theo ID (kèm items) */
    Order_24162140 findById(Integer orderId);

    /** Lấy danh sách đơn hàng của user (mới nhất trước) */
    List<Order_24162140> findByUserId(Integer userId);
}