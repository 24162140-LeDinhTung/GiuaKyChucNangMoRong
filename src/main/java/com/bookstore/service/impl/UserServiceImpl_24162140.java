package com.bookstore.service.impl;

import com.bookstore.dao.UserDAO_24162140;
import com.bookstore.dao.impl.UserDAOImpl_24162140;
import com.bookstore.entity.User_24162140;
import com.bookstore.service.UserService_24162140;
import com.bookstore.util.EmailUtil_24162140;
import com.bookstore.util.OtpUtil_24162140;
import com.bookstore.util.PasswordUtil_24162140;

import java.time.LocalDateTime;

/**
 * Triển khai UserService
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class UserServiceImpl_24162140 implements UserService_24162140 {

    private final UserDAO_24162140 userDAO = new UserDAOImpl_24162140();

    // ==================== REGISTER ====================
    @Override
    public User_24162140 register(String email, String fullname, String phone, String password) {

        // 1. Kiểm tra email đã tồn tại
        if (userDAO.existsByEmail(email)) {
            throw new IllegalArgumentException("Email đã được đăng ký");
        }

        // 2. Sinh OTP
        String otp = OtpUtil_24162140.generate();

        // 3. Tạo user mới (chưa activated)
        User_24162140 user = new User_24162140();
        user.setEmail(email);
        user.setFullname(fullname);
        user.setPhone(phone != null && !phone.isBlank() ? Integer.valueOf(phone) : null);
        user.setPasswd(PasswordUtil_24162140.hash(password));
        user.setSignupDate(LocalDateTime.now());
        user.setIsAdmin(false);
        user.setActivated(false);
        user.setOtp(otp);
        user.setOtpExpiry(OtpUtil_24162140.expiryTime());

        User_24162140 saved = userDAO.save(user);

        // 4. Gửi OTP qua email
        EmailUtil_24162140.sendOtp(email, otp);

        return saved;
    }

    // ==================== ACTIVATE ====================
    @Override
    public boolean activate(String email, String otp) {

        User_24162140 user = userDAO.findByEmail(email);
        if (user == null) return false;

        // Đã kích hoạt rồi
        if (Boolean.TRUE.equals(user.getActivated())) return true;

        // Kiểm tra OTP
        if (user.getOtp() == null || !user.getOtp().equals(otp)) return false;

        // Kiểm tra hết hạn
        if (OtpUtil_24162140.isExpired(user.getOtpExpiry())) return false;

        // Kích hoạt
        user.setActivated(true);
        user.setOtp(null);
        user.setOtpExpiry(null);
        userDAO.update(user);
        return true;
    }

    // ==================== LOGIN ====================
    @Override
    public User_24162140 login(String email, String password) {

        User_24162140 user = userDAO.findByEmail(email);
        if (user == null) return null;

        // Chưa kích hoạt
        if (!Boolean.TRUE.equals(user.getActivated())) {
            throw new IllegalStateException("Tài khoản chưa được kích hoạt. Vui lòng kiểm tra email để nhập OTP.");
        }

        // Sai mật khẩu
        if (!PasswordUtil_24162140.matches(password, user.getPasswd())) {
            return null;
        }

        // Cập nhật last_login
        user.setLastLogin(LocalDateTime.now());
        userDAO.update(user);

        return user;
    }

    // ==================== RESEND OTP ====================
    @Override
    public void resendOtp(String email) {

        User_24162140 user = userDAO.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("Email không tồn tại");
        }
        if (Boolean.TRUE.equals(user.getActivated())) {
            throw new IllegalStateException("Tài khoản đã được kích hoạt");
        }

        String otp = OtpUtil_24162140.generate();
        user.setOtp(otp);
        user.setOtpExpiry(OtpUtil_24162140.expiryTime());
        userDAO.update(user);

        EmailUtil_24162140.sendOtp(email, otp);
    }
}