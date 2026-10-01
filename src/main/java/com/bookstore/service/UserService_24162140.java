package com.bookstore.service;

import com.bookstore.entity.User_24162140;

/**
 * Interface Service cho User
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface UserService_24162140 {

    /**
     * Đăng ký tài khoản mới
     * @return User đã lưu (chưa activated)
     * @throws IllegalArgumentException nếu email đã tồn tại
     */
    User_24162140 register(String email, String fullname, String phone, String password);

    /**
     * Kích hoạt tài khoản bằng OTP
     * @return true nếu thành công, false nếu OTP sai/hết hạn
     */
    boolean activate(String email, String otp);

    /**
     * Đăng nhập
     * @return User nếu đúng, null nếu sai
     */
    User_24162140 login(String email, String password);

    /**
     * Gửi lại OTP cho user chưa kích hoạt
     */
    void resendOtp(String email);
}