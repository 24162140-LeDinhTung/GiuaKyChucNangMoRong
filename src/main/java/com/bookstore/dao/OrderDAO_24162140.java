package com.bookstore.dao;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.OrderItem_24162140;
import com.bookstore.entity.OrderStatus_24162140;
import java.util.List;
import java.util.Map;

/**
 * DAO cho Order và OrderItem
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface OrderDAO_24162140 {

    /** Tạo đơn hàng (kèm items) trong 1 transaction */
    Order_24162140 createOrder(Order_24162140 order, List<OrderItem_24162140> items);

    /** Lấy đơn hàng theo ID (kèm items) */
    Order_24162140 findById(Integer orderId);

    /** Lấy TẤT CẢ đơn hàng của user (mới nhất trước) */
    List<Order_24162140> findByUserId(Integer userId);

    /** ⭐ Lấy đơn hàng của user theo trạng thái */
    List<Order_24162140> findByUserIdAndStatus(Integer userId, OrderStatus_24162140 status);

    /** ⭐ Đếm số đơn của user theo từng trạng thái */
    Map<OrderStatus_24162140, Long> countByStatusForUser(Integer userId);

    /** ⭐ Đếm tổng số đơn của user */
    long countByUser(Integer userId);
}