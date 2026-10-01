package com.bookstore.service.impl;

import com.bookstore.dao.AuthorDAO_24162140;
import com.bookstore.dao.impl.AuthorDAOImpl_24162140;
import com.bookstore.entity.Author_24162140;
import com.bookstore.service.AuthorService_24162140;

import java.util.List;

/**
 * Triển khai AuthorService
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public class AuthorServiceImpl_24162140 implements AuthorService_24162140 {

    private final AuthorDAO_24162140 authorDAO = new AuthorDAOImpl_24162140();

    @Override
    public List<Author_24162140> getPage(int page, int pageSize) {
        return authorDAO.findPage(page, pageSize);
    }

    @Override
    public int getTotalPages(int pageSize) {
        long total = authorDAO.countAll();
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public List<Author_24162140> search(String keyword, int page, int pageSize) {
        return authorDAO.searchByKeyword(keyword, page, pageSize);
    }

    @Override
    public int getTotalPagesByKeyword(String keyword, int pageSize) {
        long total = authorDAO.countByKeyword(keyword);
        return (int) Math.ceil((double) total / pageSize);
    }

    @Override
    public List<Author_24162140> getAll() {
        return authorDAO.findAll();
    }

    @Override
    public Author_24162140 getById(Integer authorId) {
        return authorDAO.findById(authorId);
    }

    @Override
    public Author_24162140 create(Author_24162140 author) {
        return authorDAO.save(author);
    }

    @Override
    public Author_24162140 update(Author_24162140 author) {
        return authorDAO.update(author);
    }

    @Override
    public void delete(Integer authorId) {
        authorDAO.delete(authorId);
    }
}