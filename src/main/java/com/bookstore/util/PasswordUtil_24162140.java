package com.bookstore.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility mã hóa & kiểm tra mật khẩu bằng BCrypt
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class PasswordUtil_24162140 {

    /** Hash mật khẩu */
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    /** Kiểm tra mật khẩu có khớp với hash không */
    public static boolean matches(String plainPassword, String hashed) {
        if (plainPassword == null || hashed == null) return false;
        try {
            return BCrypt.checkpw(plainPassword, hashed);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}