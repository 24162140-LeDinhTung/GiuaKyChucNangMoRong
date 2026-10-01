package com.bookstore.dao;

import com.bookstore.entity.Rating_24162140;
import java.util.List;

/**
 * Interface DAO cho bảng rating
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface RatingDAO_24162140 {

    /** Đếm số review của 1 sách */
    long countByBook(Integer bookid);

    /** Điểm trung bình của 1 sách */
    double avgByBook(Integer bookid);

    /** Lấy danh sách review của 1 sách (kèm thông tin user) */
    List<Object[]> findReviewsByBook(Integer bookid);

    /** Lưu hoặc cập nhật review */
    Rating_24162140 save(Rating_24162140 rating);

    /** Kiểm tra user đã review sách chưa */
    Rating_24162140 findByUserAndBook(Integer userid, Integer bookid);
}