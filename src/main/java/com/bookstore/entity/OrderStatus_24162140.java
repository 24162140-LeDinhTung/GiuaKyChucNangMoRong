package com.bookstore.entity;

/**
 * Trạng thái đơn hàng
 * MSSV: 24162140 - Lê Đình Tùng
 */
public enum OrderStatus_24162140 {
    PENDING,     // Chờ xác nhận
    CONFIRMED,   // Đã xác nhận
    SHIPPING,    // Đang giao
    DELIVERED,   // Đã giao
    CANCELLED    // Đã hủy
}