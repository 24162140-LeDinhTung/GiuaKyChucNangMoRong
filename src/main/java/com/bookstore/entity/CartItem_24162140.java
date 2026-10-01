package com.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity mapping bảng cart_items
 * MSSV: 24162140 - Lê Đình Tùng
 */
@Entity
@Table(name = "cart_items",
       uniqueConstraints = @UniqueConstraint(
           name = "uk_cart_book",
           columnNames = {"cart_id", "book_id"}))
public class CartItem_24162140 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Integer cartItemId;

    @Column(name = "cart_id", nullable = false)
    private Integer cartId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    // Không map với DB — load riêng trong Service
    @Transient
    private Book_24162140 book;

    public CartItem_24162140() {}

    /** Tổng tiền của item = giá sách × số lượng */
    public BigDecimal getSubtotal() {
        if (book == null || book.getPrice() == null || quantity == null) {
            return BigDecimal.ZERO;
        }
        return book.getPrice().multiply(BigDecimal.valueOf(quantity));
    }

    // ===== GETTERS / SETTERS =====
    public Integer getCartItemId() { return cartItemId; }
    public void setCartItemId(Integer cartItemId) { this.cartItemId = cartItemId; }

    public Integer getCartId() { return cartId; }
    public void setCartId(Integer cartId) { this.cartId = cartId; }

    public Integer getBookId() { return bookId; }
    public void setBookId(Integer bookId) { this.bookId = bookId; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Book_24162140 getBook() { return book; }
    public void setBook(Book_24162140 book) { this.book = book; }
}