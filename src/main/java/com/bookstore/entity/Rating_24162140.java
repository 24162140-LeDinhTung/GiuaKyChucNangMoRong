package com.bookstore.entity;

import jakarta.persistence.*;

/**
 * Entity mapping bảng rating
 * Khóa chính phức hợp: (userid, bookid)
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@Entity
@Table(name = "rating")
@IdClass(RatingId_24162140.class)
public class Rating_24162140 {

    @Id
    @Column(name = "userid")
    private Integer userid;

    @Id
    @Column(name = "bookid")
    private Integer bookid;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "review_text", columnDefinition = "TEXT")
    private String reviewText;

    // ===== CONSTRUCTORS =====
    public Rating_24162140() {}

    // ===== GETTERS / SETTERS =====
    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public Integer getBookid() { return bookid; }
    public void setBookid(Integer bookid) { this.bookid = bookid; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getReviewText() { return reviewText; }
    public void setReviewText(String reviewText) { this.reviewText = reviewText; }
}