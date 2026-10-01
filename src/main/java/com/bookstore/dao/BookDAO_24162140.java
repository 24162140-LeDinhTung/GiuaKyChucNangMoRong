package com.bookstore.dao;

import com.bookstore.entity.Book_24162140;
import java.util.List;

/**
 * Interface DAO cho bảng books
 * MSSV: 24162140 - Lê Đình Tùng - Đề 01
 */
public interface BookDAO_24162140 {

    /** Đếm tổng số sách */
    long countAll();

    /** Đếm theo keyword (title, publisher) */
    long countByKeyword(String keyword);

    /** Lấy danh sách sách theo trang */
    List<Book_24162140> findPage(int page, int pageSize);

    /** Tìm kiếm + phân trang */
    List<Book_24162140> searchByKeyword(String keyword, int page, int pageSize);

    /** Lấy chi tiết sách theo ID */
    Book_24162140 findById(Integer bookid);

    /** Thêm sách mới, trả về sách đã insert (có id) */
    Book_24162140 save(Book_24162140 book);

    /** Cập nhật sách */
    Book_24162140 update(Book_24162140 book);

    /** Xóa sách */
    void delete(Integer bookid);
}