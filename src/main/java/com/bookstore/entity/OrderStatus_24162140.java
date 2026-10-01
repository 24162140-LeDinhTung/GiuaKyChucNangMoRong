package com.bookstore.entity;

/**
 * Trạng thái đơn hàng — 8 trạng thái
 * MSSV: 24162140 - Lê Đình Tùng
 */
public enum OrderStatus_24162140 {

    PENDING("Đơn hàng mới"),
    CONFIRMED("Đã xác nhận"),
    PREPARING("Chuẩn bị hàng"),
    SHIPPING("Vận chuyển"),
    DELIVERING("Đang giao hàng"),
    DELIVERED("Đã giao"),
    CANCELLED("Đơn hàng hủy"),
    RETURNED("Đơn hàng hoàn");

    private final String label;

    OrderStatus_24162140(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}