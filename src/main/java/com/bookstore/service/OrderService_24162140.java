package com.bookstore.service;

import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.OrderStatus_24162140;
import java.util.List;
import java.util.Map;

/**
 * Service xử lý đơn hàng
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface OrderService_24162140 {

    /** Đặt hàng COD */
    Order_24162140 placeOrderCOD(Integer userId,
                                  String shippingAddress,
                                  String phone,
                                  String note);

    /** Lấy đơn hàng theo ID */
    Order_24162140 getById(Integer orderId);

    /** ⭐ Lấy TẤT CẢ đơn hàng của user */
    List<Order_24162140> getOrdersByUser(Integer userId);

    /** ⭐ Lấy đơn hàng của user theo trạng thái */
    List<Order_24162140> getOrdersByUserAndStatus(Integer userId,
                                                   OrderStatus_24162140 status);

    /** ⭐ Đếm số đơn theo từng trạng thái */
    Map<OrderStatus_24162140, Long> countByStatus(Integer userId);

    /** ⭐ Đếm tổng số đơn */
    long countByUser(Integer userId);
}