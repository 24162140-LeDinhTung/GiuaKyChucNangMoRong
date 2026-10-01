package com.bookstore.service.impl;

import com.bookstore.dao.BookDAO_24162140;
import com.bookstore.dao.RatingDAO_24162140;
import com.bookstore.dao.impl.BookDAOImpl_24162140;
import com.bookstore.dao.impl.RatingDAOImpl_24162140;
import com.bookstore.entity.Book_24162140;
import com.bookstore.service.BookService_24162140;

import java.util.List;

/**
 * Triển khai BookService
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class BookServiceImpl_24162140 implements BookService_24162140 {

    private final BookDAO_24162140   bookDAO   = new BookDAOImpl_24162140();
    private final RatingDAO_24162140 ratingDAO = new RatingDAOImpl_24162140();

    @Override
    public long countAll() {
        return bookDAO.countAll();
    }

    @Override
    public List<Book_24162140> getPage(int page, int pageSize) {
        List<Book_24162140> books = bookDAO.findPage(page, pageSize);
        enrichReviews(books);
        return books;
    }

    @Override
    public int getTotalPages(int pageSize) {
        long total = countAll();
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public List<Book_24162140> search(String keyword, int page, int pageSize) {
        List<Book_24162140> books = bookDAO.searchByKeyword(keyword, page, pageSize);
        enrichReviews(books);
        return books;
    }

    @Override
    public int getTotalPagesByKeyword(String keyword, int pageSize) {
        long total = bookDAO.countByKeyword(keyword);
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public Book_24162140 getById(Integer bookid) {
        Book_24162140 b = bookDAO.findById(bookid);
        if (b != null) {
            b.setReviewCount(ratingDAO.countByBook(b.getBookid()));
            b.setAvgRating(ratingDAO.avgByBook(b.getBookid()));
        }
        return b;
    }

    @Override
    public Book_24162140 create(Book_24162140 book) {
        return bookDAO.save(book);
    }

    @Override
    public Book_24162140 update(Book_24162140 book) {
        return bookDAO.update(book);
    }

    @Override
    public void delete(Integer bookid) {
        bookDAO.delete(bookid);
    }

    /** Bổ sung review count + avg rating cho danh sách sách */
    private void enrichReviews(List<Book_24162140> books) {
        for (Book_24162140 b : books) {
            b.setReviewCount(ratingDAO.countByBook(b.getBookid()));
            b.setAvgRating(ratingDAO.avgByBook(b.getBookid()));
        }
    }
}