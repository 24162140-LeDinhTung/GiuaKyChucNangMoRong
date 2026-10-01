package com.bookstore.dao.impl;

import com.bookstore.dao.RatingDAO_24162140;
import com.bookstore.entity.Rating_24162140;
import com.bookstore.entity.RatingId_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import java.util.List;

/**
 * Triển khai RatingDAO
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class RatingDAOImpl_24162140 implements RatingDAO_24162140 {

    @Override
    public long countByBook(Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(r) FROM Rating_24162140 r WHERE r.bookid = :id",
                Long.class
            ).setParameter("id", bookid).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public double avgByBook(Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            Double avg = em.createQuery(
                "SELECT AVG(r.rating) FROM Rating_24162140 r WHERE r.bookid = :id",
                Double.class
            ).setParameter("id", bookid).getSingleResult();
            return avg != null ? avg : 0.0;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Object[]> findReviewsByBook(Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            // Trả về: [email, fullname, rating, review_text]
            return em.createQuery(
                "SELECT u.email, u.fullname, r.rating, r.reviewText " +
                "FROM Rating_24162140 r " +
                "JOIN User_24162140 u ON u.id = r.userid " +
                "WHERE r.bookid = :id " +
                "ORDER BY r.rating DESC",
                Object[].class
            ).setParameter("id", bookid).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Rating_24162140 save(Rating_24162140 rating) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Rating_24162140 existing = em.find(
                Rating_24162140.class,
                new RatingId_24162140(rating.getUserid(), rating.getBookid())
            );
            if (existing != null) {
                existing.setRating(rating.getRating());
                existing.setReviewText(rating.getReviewText());
                em.merge(existing);
                tx.commit();
                return existing;
            } else {
                em.persist(rating);
                tx.commit();
                return rating;
            }
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Rating_24162140 findByUserAndBook(Integer userid, Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT r FROM Rating_24162140 r " +
                "WHERE r.userid = :uid AND r.bookid = :bid",
                Rating_24162140.class
            )
            .setParameter("uid", userid)
            .setParameter("bid", bookid)
            .getResultStream()
            .findFirst()
            .orElse(null);
        } finally {
            em.close();
        }
    }
}