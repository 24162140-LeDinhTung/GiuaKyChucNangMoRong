<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>✍️ Quản lý Tác giả</h1>

<div class="admin-toolbar">
    <form method="get" action="${pageContext.request.contextPath}/admin/authors" class="search-form">
        <input type="text" name="keyword" value="${keyword}"
               placeholder="Tìm theo tên tác giả...">
        <button type="submit" class="button">🔍 Tìm</button>
    </form>
    <a class="button" href="${pageContext.request.contextPath}/admin/author-form">
        ➕ Thêm tác giả
    </a>
</div>

<table class="admin-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Tên tác giả</th>
            <th>Ngày sinh</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="a" items="${authors}">
            <tr>
                <td>${a.authorId}</td>
                <td>${a.authorName}</td>
                <td>${a.dateOfBirth}</td>
                <td class="actions-cell">
                    <a class="btn-edit"
                       href="${pageContext.request.contextPath}/admin/author-form?id=${a.authorId}">
                        ✏️ Sửa
                    </a>
                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/author-delete"
                          onsubmit="return confirm('Xóa tác giả này? Các liên kết với sách cũng bị xóa.');"
                          style="display:inline">
                        <input type="hidden" name="id" value="${a.authorId}">
                        <button type="submit" class="btn-delete">🗑️ Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty authors}">
            <tr><td colspan="4" class="empty-row">Không có tác giả nào.</td></tr>
        </c:if>
    </tbody>
</table>

<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&keyword=${keyword}">« Trước</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}"><span class="active">${i}</span></c:when>
                <c:otherwise><a href="?page=${i}&keyword=${keyword}">${i}</a></c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}&keyword=${keyword}">Sau »</a>
        </c:if>
    </div>
    <p class="page-info">Trang ${currentPage} / ${totalPages}</p>
</c:if>