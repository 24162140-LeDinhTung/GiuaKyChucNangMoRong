package com.bookstore.service.impl;

import com.bookstore.dao.RatingDAO_24162140;
import com.bookstore.dao.impl.RatingDAOImpl_24162140;
import com.bookstore.entity.Rating_24162140;
import com.bookstore.service.RatingService_24162140;

import java.util.List;

/**
 * Triển khai RatingService
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class RatingServiceImpl_24162140 implements RatingService_24162140 {

    private final RatingDAO_24162140 ratingDAO = new RatingDAOImpl_24162140();

    @Override
    public List<Object[]> getReviews(Integer bookid) {
        return ratingDAO.findReviewsByBook(bookid);
    }

    @Override
    public void addOrUpdateReview(Integer userid, Integer bookid,
                                   Integer rating, String reviewText) {
        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Đánh giá phải từ 1 đến 5 sao");
        }

        Rating_24162140 r = new Rating_24162140();
        r.setUserid(userid);
        r.setBookid(bookid);
        r.setRating(rating);
        r.setReviewText(reviewText);

        ratingDAO.save(r);
    }
}