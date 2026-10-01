package com.bookstore.service;

import com.bookstore.entity.Author_24162140;
import java.util.List;

/**
 * Interface Service cho Author
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface AuthorService_24162140 {

    List<Author_24162140> getPage(int page, int pageSize);

    int getTotalPages(int pageSize);

    List<Author_24162140> search(String keyword, int page, int pageSize);

    int getTotalPagesByKeyword(String keyword, int pageSize);

    List<Author_24162140> getAll();

    Author_24162140 getById(Integer authorId);

    Author_24162140 create(Author_24162140 author);

    Author_24162140 update(Author_24162140 author);

    void delete(Integer authorId);
}