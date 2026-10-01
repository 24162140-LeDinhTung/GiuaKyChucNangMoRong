package com.bookstore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity mapping bảng carts
 * Mỗi user có 1 giỏ hàng duy nhất
 * MSSV: 24162140 - Lê Đình Tùng
 */
@Entity
@Table(name = "carts")
public class Cart_24162140 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "user_id", nullable = false, unique = true)
    private Integer userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "cartId", cascade = CascadeType.ALL,
               orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartItem_24162140> items = new ArrayList<>();

    public Cart_24162140() {}

    // ===== GETTERS / SETTERS =====
    public Integer getCartId() { return cartId; }
    public void setCartId(Integer cartId) { this.cartId = cartId; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<CartItem_24162140> getItems() { return items; }
    public void setItems(List<CartItem_24162140> items) { this.items = items; }
}