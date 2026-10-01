package com.bookstore.service;

import java.util.List;

/**
 * Interface Service cho Rating
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface RatingService_24162140 {

    /** Lấy danh sách review của sách: [email, fullname, rating, review_text] */
    List<Object[]> getReviews(Integer bookid);

    /** Thêm hoặc cập nhật review */
    void addOrUpdateReview(Integer userid, Integer bookid, Integer rating, String reviewText);
}