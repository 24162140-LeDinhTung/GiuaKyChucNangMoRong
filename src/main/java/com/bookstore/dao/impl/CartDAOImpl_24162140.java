package com.bookstore.dao.impl;

import com.bookstore.dao.CartDAO_24162140;
import com.bookstore.entity.Cart_24162140;
import com.bookstore.entity.CartItem_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Triển khai CartDAO
 * MSSV: 24162140 - Lê Đình Tùng
 */
public class CartDAOImpl_24162140 implements CartDAO_24162140 {

    @Override
    public Cart_24162140 getOrCreateCart(Integer userId) {
        Cart_24162140 cart = findByUserId(userId);
        if (cart != null) return cart;

        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Cart_24162140 newCart = new Cart_24162140();
            newCart.setUserId(userId);
            newCart.setCreatedAt(LocalDateTime.now());
            em.persist(newCart);
            tx.commit();
            return newCart;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Cart_24162140 findByUserId(Integer userId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT c FROM Cart_24162140 c WHERE c.userId = :uid",
                Cart_24162140.class
            ).setParameter("uid", userId).getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public List<CartItem_24162140> findItems(Integer cartId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT ci FROM CartItem_24162140 ci WHERE ci.cartId = :cid " +
                "ORDER BY ci.cartItemId",
                CartItem_24162140.class
            ).setParameter("cid", cartId).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162140 findItem(Integer cartId, Integer bookId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT ci FROM CartItem_24162140 ci " +
                "WHERE ci.cartId = :cid AND ci.bookId = :bid",
                CartItem_24162140.class
            ).setParameter("cid", cartId).setParameter("bid", bookId)
             .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public CartItem_24162140 saveItem(CartItem_24162140 item) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (item.getCartItemId() == null) {
                em.persist(item);
            } else {
                em.merge(item);
            }
            tx.commit();
            return item;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void deleteItem(Integer cartItemId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            CartItem_24162140 item = em.find(CartItem_24162140.class, cartItemId);
            if (item != null) em.remove(item);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void clearCart(Integer cartId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.createQuery("DELETE FROM CartItem_24162140 ci WHERE ci.cartId = :cid")
              .setParameter("cid", cartId).executeUpdate();
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public long countTotalItems(Integer cartId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COALESCE(SUM(ci.quantity), 0) FROM CartItem_24162140 ci " +
                "WHERE ci.cartId = :cid",
                Long.class
            ).setParameter("cid", cartId).getSingleResult();
            return count != null ? count : 0L;
        } finally {
            em.close();
        }
    }
}