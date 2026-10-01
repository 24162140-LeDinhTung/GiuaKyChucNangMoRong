<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>${mode == 'edit' ? '✏️ Sửa tác giả' : '➕ Thêm tác giả mới'}</h1>

<a class="back-link" href="${pageContext.request.contextPath}/admin/authors">
    ← Quay lại danh sách
</a>

<form class="admin-form" method="post"
      action="${pageContext.request.contextPath}/admin/author-form">

    <c:if test="${mode == 'edit'}">
        <input type="hidden" name="authorId" value="${author.authorId}">
    </c:if>

    <div class="form-grid">
        <div class="form-group full-width">
            <label>Tên tác giả <span class="req">*</span></label>
            <input type="text" name="authorName" value="${author.authorName}" required>
        </div>

        <div class="form-group full-width">
            <label>Ngày sinh</label>
            <input type="date" name="dateOfBirth" value="${author.dateOfBirth}">
        </div>
    </div>

    <div class="form-actions">
        <button type="submit" class="button">💾 Lưu</button>
        <a class="button secondary" href="${pageContext.request.contextPath}/admin/authors">Hủy</a>
    </div>
</form>