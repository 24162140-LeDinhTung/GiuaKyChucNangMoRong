package com.bookstore.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity mapping bảng author
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
@Entity
@Table(name = "author")
public class Author_24162140 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "author_name", nullable = false, length = 100)
    private String authorName;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /** Quan hệ N-N ngược lại với Book (không bắt buộc, dùng cho Câu 6) */
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    private List<Book_24162140> books = new ArrayList<>();

    // ===== CONSTRUCTORS =====
    public Author_24162140() {}

    // ===== GETTERS / SETTERS =====
    public Integer getAuthorId() { return authorId; }
    public void setAuthorId(Integer authorId) { this.authorId = authorId; }

    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public List<Book_24162140> getBooks() { return books; }
    public void setBooks(List<Book_24162140> books) { this.books = books; }
}