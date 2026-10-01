package com.bookstore.dao.impl;

import com.bookstore.dao.BookDAO_24162140;
import com.bookstore.entity.Book_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Triển khai BookDAO bằng JPA
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class BookDAOImpl_24162140 implements BookDAO_24162140 {

    @Override
    public long countAll() {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(b) FROM Book_24162140 b", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public long countByKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) return countAll();
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            String kw = "%" + keyword.toLowerCase() + "%";
            return em.createQuery(
                "SELECT COUNT(b) FROM Book_24162140 b " +
                "WHERE LOWER(b.title) LIKE :kw OR LOWER(b.publisher) LIKE :kw",
                Long.class
            ).setParameter("kw", kw).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book_24162140> findPage(int page, int pageSize) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            TypedQuery<Book_24162140> query = em.createQuery(
                "SELECT DISTINCT b FROM Book_24162140 b " +
                "LEFT JOIN FETCH b.authors " +
                "ORDER BY b.bookid",
                Book_24162140.class
            );
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Book_24162140> searchByKeyword(String keyword, int page, int pageSize) {
        if (keyword == null || keyword.isBlank()) return findPage(page, pageSize);

        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            String kw = "%" + keyword.toLowerCase() + "%";
            TypedQuery<Book_24162140> query = em.createQuery(
                "SELECT DISTINCT b FROM Book_24162140 b " +
                "LEFT JOIN FETCH b.authors " +
                "WHERE LOWER(b.title) LIKE :kw OR LOWER(b.publisher) LIKE :kw " +
                "ORDER BY b.bookid",
                Book_24162140.class
            );
            query.setParameter("kw", kw);
            query.setFirstResult((page - 1) * pageSize);
            query.setMaxResults(pageSize);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24162140 findById(Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            TypedQuery<Book_24162140> query = em.createQuery(
                "SELECT b FROM Book_24162140 b " +
                "LEFT JOIN FETCH b.authors " +
                "WHERE b.bookid = :id",
                Book_24162140.class
            );
            query.setParameter("id", bookid);
            List<Book_24162140> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24162140 save(Book_24162140 book) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(book);
            tx.commit();
            return book;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Book_24162140 update(Book_24162140 book) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book_24162140 merged = em.merge(book);
            tx.commit();
            return merged;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer bookid) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Book_24162140 book = em.find(Book_24162140.class, bookid);
            if (book != null) em.remove(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}