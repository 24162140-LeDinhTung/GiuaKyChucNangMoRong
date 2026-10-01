package com.bookstore.dao.impl;

import com.bookstore.dao.AuthorDAO_24162140;
import com.bookstore.entity.Author_24162140;
import com.bookstore.util.JPAUtil_24162140;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 * Triển khai AuthorDAO
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class AuthorDAOImpl_24162140 implements AuthorDAO_24162140 {

    @Override
    public long countAll() {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT COUNT(a) FROM Author_24162140 a", Long.class
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
                "SELECT COUNT(a) FROM Author_24162140 a " +
                "WHERE LOWER(a.authorName) LIKE :kw",
                Long.class
            ).setParameter("kw", kw).getSingleResult();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24162140> findPage(int page, int pageSize) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT a FROM Author_24162140 a ORDER BY a.authorId",
                Author_24162140.class
            )
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24162140> searchByKeyword(String keyword, int page, int pageSize) {
        if (keyword == null || keyword.isBlank()) return findPage(page, pageSize);

        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            String kw = "%" + keyword.toLowerCase() + "%";
            return em.createQuery(
                "SELECT a FROM Author_24162140 a " +
                "WHERE LOWER(a.authorName) LIKE :kw " +
                "ORDER BY a.authorId",
                Author_24162140.class
            )
            .setParameter("kw", kw)
            .setFirstResult((page - 1) * pageSize)
            .setMaxResults(pageSize)
            .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Author_24162140> findAll() {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.createQuery(
                "SELECT a FROM Author_24162140 a ORDER BY a.authorName",
                Author_24162140.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162140 findById(Integer authorId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        try {
            return em.find(Author_24162140.class, authorId);
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162140 save(Author_24162140 author) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(author);
            tx.commit();
            return author;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Author_24162140 update(Author_24162140 author) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Author_24162140 merged = em.merge(author);
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
    public void delete(Integer authorId) {
        EntityManager em = JPAUtil_24162140.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Author_24162140 a = em.find(Author_24162140.class, authorId);
            if (a != null) em.remove(a);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}