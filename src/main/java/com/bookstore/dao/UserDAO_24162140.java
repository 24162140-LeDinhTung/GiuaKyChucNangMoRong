package com.bookstore.dao;

import com.bookstore.entity.User_24162140;

/**
 * Interface DAO cho bảng users
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface UserDAO_24162140 {

    /** Tìm user theo email */
    User_24162140 findByEmail(String email);

    /** Kiểm tra email tồn tại */
    boolean existsByEmail(String email);

    /** Thêm user mới, trả về user đã insert (có id) */
    User_24162140 save(User_24162140 user);

    /** Cập nhật user */
    User_24162140 update(User_24162140 user);
}