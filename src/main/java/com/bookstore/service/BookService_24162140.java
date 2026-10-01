package com.bookstore.service;

import com.bookstore.entity.Book_24162140;
import java.util.List;

/**
 * Interface Service cho Book
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface BookService_24162140 {

    long countAll();

    List<Book_24162140> getPage(int page, int pageSize);

    int getTotalPages(int pageSize);

    /** Tìm kiếm + phân trang */
    List<Book_24162140> search(String keyword, int page, int pageSize);

    int getTotalPagesByKeyword(String keyword, int pageSize);

    Book_24162140 getById(Integer bookid);

    Book_24162140 create(Book_24162140 book);

    Book_24162140 update(Book_24162140 book);

    void delete(Integer bookid);
}