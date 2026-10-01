package com.bookstore.service;

import com.bookstore.entity.Order_24162140;
import java.util.List;

/**
 * Service xử lý đơn hàng
 * MSSV: 24162140 - Lê Đình Tùng
 */
public interface OrderService_24162140 {

    /** Đặt hàng COD — tự động lấy items từ giỏ + xóa giỏ */
    Order_24162140 placeOrderCOD(Integer userId,
                                  String shippingAddress,
                                  String phone,
                                  String note);

    /** Lấy đơn hàng theo ID */
    Order_24162140 getById(Integer orderId);

    /** Lịch sử đơn hàng của user */
    List<Order_24162140> getOrdersByUser(Integer userId);
}