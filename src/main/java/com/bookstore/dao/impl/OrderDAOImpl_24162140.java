package com.bookstore.dao.impl;

import com.bookstore.dao.OrderDAO_24162140;
import com.bookstore.entity.Order_24162140;
import com.bookstore.entity.OrderItem_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * Triển khai OrderDAO
 * MSSV: 24162140 - Lê Đình Tùng
 */
public class OrderDAOImpl_24162140 implements OrderDAO_24162140 {

    @Override
    public Order_24162140 createOrder(Order_24162140 order,
                                       List<OrderItem_24162140> items) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(order);
            em.flush();  // Lấy order_id

            for (OrderItem_24162140 item : items) {
                item.setOrderId(order.getOrderId());
                em.persist(item);
            }
            tx.commit();
            return order;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Order_24162140 findById(Integer orderId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT o FROM Order_24162140 o " +
                "LEFT JOIN FETCH o.items " +
                "WHERE o.orderId = :oid",
                Order_24162140.class
            ).setParameter("oid", orderId).getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Order_24162140> findByUserId(Integer userId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT DISTINCT o FROM Order_24162140 o " +
                "LEFT JOIN FETCH o.items " +
                "WHERE o.userId = :uid " +
                "ORDER BY o.orderId DESC",
                Order_24162140.class
            ).setParameter("uid", userId).getResultList();
        } finally {
            em.close();
        }
    }
}