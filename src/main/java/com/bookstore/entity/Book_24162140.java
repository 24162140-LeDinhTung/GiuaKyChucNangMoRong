package com.bookstore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity mapping bảng books
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@Entity
@Table(name = "books")
public class Book_24162140 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookid")
    private Integer bookid;

    @Column(name = "isbn")
    private Integer isbn;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "publisher", length = 100)
    private String publisher;

    // ⭐ ĐÃ SỬA: precision = 12 (thay vì 6)
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "cover_image", length = 100)
    private String coverImage;

    @Column(name = "quantity")
    private Integer quantity;

    // ===== QUAN HỆ N-N với Author (qua bảng book_author) =====
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_author",
        joinColumns = @JoinColumn(name = "bookid"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author_24162140> authors = new ArrayList<>();

    // ===== TRANSIENT FIELD (không map cột DB) =====
    @Transient
    private long reviewCount = 0;

    @Transient
    private double avgRating = 0.0;

    // ===== CONSTRUCTORS =====
    public Book_24162140() {}

    // ===== GETTERS / SETTERS =====
    public Integer getBookid() { return bookid; }
    public void setBookid(Integer bookid) { this.bookid = bookid; }

    public Integer getIsbn() { return isbn; }
    public void setIsbn(Integer isbn) { this.isbn = isbn; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }

    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public List<Author_24162140> getAuthors() { return authors; }
    public void setAuthors(List<Author_24162140> authors) { this.authors = authors; }

    public long getReviewCount() { return reviewCount; }
    public void setReviewCount(long reviewCount) { this.reviewCount = reviewCount; }

    public double getAvgRating() { return avgRating; }
    public void setAvgRating(double avgRating) { this.avgRating = avgRating; }

    // ===== TIỆN ÍCH CHO JSP =====

    /** Trả về tên tác giả dạng "Nguyễn Du, Nam Cao" */
    public String getAuthorsAsString() {
        if (authors == null || authors.isEmpty()) return "Chưa rõ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < authors.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(authors.get(i).getAuthorName());
        }
        return sb.toString();
    }

    // ⭐ ĐÃ THÊM: Format publishDate cho JSP
    // Vì JSTL fmt:formatDate không hỗ trợ LocalDate
    public String getPublishDateFormatted() {
        if (publishDate == null) return "";
        return publishDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}