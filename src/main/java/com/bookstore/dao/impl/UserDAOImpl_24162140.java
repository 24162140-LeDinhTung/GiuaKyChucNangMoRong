package com.bookstore.dao.impl;

import com.bookstore.dao.UserDAO_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

/**
 * Triển khai UserDAO bằng JPA
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class UserDAOImpl_24162140 implements UserDAO_24162140 {

    @Override
    public User_24162140 findByEmail(String email) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            TypedQuery<User_24162140> query = em.createQuery(
                "SELECT u FROM User_24162140 u WHERE u.email = :email",
                User_24162140.class
            );
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM User_24162140 u WHERE u.email = :email",
                Long.class
            ).setParameter("email", email).getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    @Override
    public User_24162140 save(User_24162140 user) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(user);
            tx.commit();
            return user;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public User_24162140 update(User_24162140 user) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            User_24162140 merged = em.merge(user);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}