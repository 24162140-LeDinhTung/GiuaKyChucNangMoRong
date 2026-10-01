package com.bookstore.util;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * Utility sinh OTP 6 số
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class OtpUtil_24162140 {

    private static final SecureRandom RANDOM = new SecureRandom();

    /** Số phút OTP có hiệu lực */
    public static final int OTP_VALID_MINUTES = 5;

    /** Sinh OTP 6 số (có padding 0 ở đầu) */
    public static String generate() {
        int num = RANDOM.nextInt(1_000_000);
        return String.format("%06d", num);
    }

    /** Trả về thời điểm hết hạn của OTP vừa sinh */
    public static LocalDateTime expiryTime() {
        return LocalDateTime.now().plusMinutes(OTP_VALID_MINUTES);
    }

    /** Kiểm tra OTP đã hết hạn chưa */
    public static boolean isExpired(LocalDateTime expiry) {
        return expiry == null || expiry.isBefore(LocalDateTime.now());
    }
}