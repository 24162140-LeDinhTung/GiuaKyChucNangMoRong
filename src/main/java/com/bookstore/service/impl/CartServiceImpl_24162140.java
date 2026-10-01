package com.bookstore.service.impl;

import com.bookstore.dao.BookDAO_24162140;
import com.bookstore.dao.CartDAO_24162140;
import com.bookstore.dao.impl.BookDAOImpl_24162140;
import com.bookstore.dao.impl.CartDAOImpl_24162140;
import com.bookstore.entity.Book_24162140;
import com.bookstore.entity.Cart_24162140;
import com.bookstore.entity.CartItem_24162140;
import com.bookstore.service.CartService_24162140;

import java.math.BigDecimal;
import java.util.List;

/**
 * Triển khai CartService
 * MSSV: 24162140 - Lê Đình Tùng
 */
public class CartServiceImpl_24162140 implements CartService_24162140 {

    /** Giới hạn số lượng tối đa mỗi sách trong giỏ */
    private static final int MAX_QUANTITY = 10;

    private final CartDAO_24162140 cartDAO = new CartDAOImpl_24162140();
    private final BookDAO_24162140 bookDAO = new BookDAOImpl_24162140();

    @Override
    public void addToCart(Integer userId, Integer bookId, int quantity) {
        if (quantity < 1) quantity = 1;

        Book_24162140 book = bookDAO.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Sách không tồn tại");
        }

        // Giới hạn theo tồn kho
        int maxAllowed = Math.min(MAX_QUANTITY,
                book.getQuantity() != null ? book.getQuantity() : MAX_QUANTITY);

        Cart_24162140 cart = cartDAO.getOrCreateCart(userId);
        CartItem_24162140 existing = cartDAO.findItem(cart.getCartId(), bookId);

        if (existing != null) {
            int newQty = Math.min(existing.getQuantity() + quantity, maxAllowed);
            existing.setQuantity(newQty);
            cartDAO.saveItem(existing);
        } else {
            CartItem_24162140 item = new CartItem_24162140();
            item.setCartId(cart.getCartId());
            item.setBookId(bookId);
            item.setQuantity(Math.min(quantity, maxAllowed));
            cartDAO.saveItem(item);
        }
    }

    @Override
    public void updateQuantity(Integer userId, Integer cartItemId, int quantity) {
        Cart_24162140 cart = cartDAO.findByUserId(userId);
        if (cart == null) return;

        List<CartItem_24162140> items = cartDAO.findItems(cart.getCartId());
        for (CartItem_24162140 item : items) {
            if (item.getCartItemId().equals(cartItemId)) {
                Book_24162140 book = bookDAO.findById(item.getBookId());
                int maxAllowed = Math.min(MAX_QUANTITY,
                        book != null && book.getQuantity() != null
                            ? book.getQuantity() : MAX_QUANTITY);

                if (quantity < 1) quantity = 1;
                if (quantity > maxAllowed) quantity = maxAllowed;

                item.setQuantity(quantity);
                cartDAO.saveItem(item);
                return;
            }
        }
    }

    @Override
    public void removeItem(Integer userId, Integer cartItemId) {
        cartDAO.deleteItem(cartItemId);
    }

    @Override
    public void clearCart(Integer userId) {
        Cart_24162140 cart = cartDAO.findByUserId(userId);
        if (cart != null) cartDAO.clearCart(cart.getCartId());
    }

    @Override
    public List<CartItem_24162140> getItems(Integer userId) {
        Cart_24162140 cart = cartDAO.findByUserId(userId);
        if (cart == null) return List.of();

        List<CartItem_24162140> items = cartDAO.findItems(cart.getCartId());
        for (CartItem_24162140 item : items) {
            Book_24162140 book = bookDAO.findById(item.getBookId());
            item.setBook(book);
        }
        return items;
    }

    @Override
    public BigDecimal getTotal(Integer userId) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem_24162140 item : getItems(userId)) {
            total = total.add(item.getSubtotal());
        }
        return total;
    }

    @Override
    public long getTotalItems(Integer userId) {
        Cart_24162140 cart = cartDAO.findByUserId(userId);
        if (cart == null) return 0L;
        return cartDAO.countTotalItems(cart.getCartId());
    }
}