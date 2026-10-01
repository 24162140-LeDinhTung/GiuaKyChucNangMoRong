package com.bookstore.dao;

import com.bookstore.entity.Author_24162140;
import java.util.List;

/**
 * Interface DAO cho bảng author
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface AuthorDAO_24162140 {

    long countAll();

    long countByKeyword(String keyword);

    List<Author_24162140> findPage(int page, int pageSize);

    List<Author_24162140> searchByKeyword(String keyword, int page, int pageSize);

    List<Author_24162140> findAll();   // Dùng cho dropdown chọn tác giả

    Author_24162140 findById(Integer authorId);

    Author_24162140 save(Author_24162140 author);

    Author_24162140 update(Author_24162140 author);

    void delete(Integer authorId);
}