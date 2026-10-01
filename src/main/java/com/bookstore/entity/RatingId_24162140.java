package com.bookstore.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Composite key cho Rating
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class RatingId_24162140 implements Serializable {

    private Integer userid;
    private Integer bookid;

    public RatingId_24162140() {}

    public RatingId_24162140(Integer userid, Integer bookid) {
        this.userid = userid;
        this.bookid = bookid;
    }

    public Integer getUserid() { return userid; }
    public void setUserid(Integer userid) { this.userid = userid; }

    public Integer getBookid() { return bookid; }
    public void setBookid(Integer bookid) { this.bookid = bookid; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RatingId_24162140)) return false;
        RatingId_24162140 that = (RatingId_24162140) o;
        return Objects.equals(userid, that.userid) && Objects.equals(bookid, that.bookid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, bookid);
    }
}