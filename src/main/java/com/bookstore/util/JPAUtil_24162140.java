package com.bookstore.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * JPA Utility - Quản lý EntityManagerFactory (singleton)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class JPAUtil_24162140 {

    private static final EntityManagerFactory emf;

    static {
        try {
            emf = Persistence.createEntityManagerFactory("BookStorePU");
        } catch (Throwable ex) {
            System.err.println("Khởi tạo EntityManagerFactory thất bại: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public static void shutdown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}