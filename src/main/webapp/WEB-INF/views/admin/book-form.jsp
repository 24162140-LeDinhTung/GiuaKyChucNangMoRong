<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>${mode == 'edit' ? '✏️ Sửa sách' : '➕ Thêm sách mới'}</h1>

<a class="back-link" href="${pageContext.request.contextPath}/admin/books">
    ← Quay lại danh sách
</a>

<form class="admin-form" method="post"
      action="${pageContext.request.contextPath}/admin/book-form">

    <c:if test="${mode == 'edit'}">
        <input type="hidden" name="bookid" value="${book.bookid}">
    </c:if>

    <div class="form-grid">
        <div class="form-group">
            <label>Tiêu đề <span class="req">*</span></label>
            <input type="text" name="title" value="${book.title}" required>
        </div>

        <div class="form-group">
            <label>ISBN</label>
            <input type="number" name="isbn" value="${book.isbn}">
        </div>

        <div class="form-group">
            <label>Publisher</label>
            <input type="text" name="publisher" value="${book.publisher}">
        </div>

        <div class="form-group">
            <label>Giá (VNĐ)</label>
            <input type="number" step="0.01" name="price" value="${book.price}">
        </div>

        <div class="form-group">
            <label>Publish date</label>
            <input type="date" name="publishDate" value="${book.publishDate}">
        </div>

        <div class="form-group">
            <label>Quantity</label>
            <input type="number" name="quantity" value="${book.quantity}">
        </div>

        <div class="form-group full-width">
            <label>Cover image (tên file)</label>
            <input type="text" name="coverImage" value="${book.coverImage}"
                   placeholder="VD: truyen-kieu.jpg">
        </div>

        <div class="form-group full-width">
            <label>Mô tả</label>
            <textarea name="description" rows="4">${book.description}</textarea>
        </div>

        <div class="form-group full-width">
            <label>Tác giả (giữ Ctrl để chọn nhiều)</label>
            <select name="authorIds" multiple size="6">
                <c:forEach var="a" items="${allAuthors}">
                    <option value="${a.authorId}"
                        <c:forEach var="sel" items="${book.authors}">
                            <c:if test="${sel.authorId == a.authorId}">selected</c:if>
                        </c:forEach>>
                        ${a.authorName}
                    </option>
                </c:forEach>
            </select>
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="button">💾 Lưu</button>
        <a class="button secondary" href="${pageContext.request.contextPath}/admin/books">Hủy</a>
    </div>
</form>