<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>📚 Quản lý Sách</h1>

<c:if test="${param.success != null}">
    <div class="alert success">${param.success}</div>
</c:if>

<!-- Toolbar: Search + Add -->
<div class="admin-toolbar">
    <form method="get" action="${pageContext.request.contextPath}/admin/books" class="search-form">
        <input type="text" name="keyword" value="${keyword}"
               placeholder="Tìm theo tiêu đề, nhà xuất bản...">
        <button type="submit" class="button">🔍 Tìm</button>
    </form>
    <a class="button" href="${pageContext.request.contextPath}/admin/book-form">
        ➕ Thêm sách
    </a>
</div>

<!-- Table -->
<table class="admin-table">
    <thead>
        <tr>
            <th>ID</th>
            <th>Cover</th>
            <th>Tiêu đề</th>
            <th>ISBN</th>
            <th>Tác giả</th>
            <th>Publisher</th>
            <th>Giá</th>
            <th>SL</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="b" items="${books}">
            <tr>
                <td>${b.bookid}</td>
                <td>
                    <c:choose>
                        <c:when test="${not empty b.coverImage}">
                            <img class="thumb"
                                 src="${pageContext.request.contextPath}/static/images/${b.coverImage}"
                                 onerror="this.src='${pageContext.request.contextPath}/static/images/no-image.png'"
                                 alt="">
                        </c:when>
                        <c:otherwise><span class="no-img">📖</span></c:otherwise>
                    </c:choose>
                </td>
                <td>${b.title}</td>
                <td>${b.isbn}</td>
                <td>${b.authorsAsString}</td>
                <td>${b.publisher}</td>
                <td>${b.price}</td>
                <td>${b.quantity}</td>
                <td class="actions-cell">
                    <a class="btn-edit"
                       href="${pageContext.request.contextPath}/admin/book-form?id=${b.bookid}">
                        ✏️ Sửa
                    </a>
                    <form method="post"
                          action="${pageContext.request.contextPath}/admin/book-delete"
                          onsubmit="return confirm('Xóa sách này?');"
                          style="display:inline">
                        <input type="hidden" name="id" value="${b.bookid}">
                        <button type="submit" class="btn-delete">🗑️ Xóa</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${empty books}">
            <tr><td colspan="9" class="empty-row">Không có sách nào.</td></tr>
        </c:if>
    </tbody>
</table>

<!-- Pagination -->
<c:if test="${totalPages > 1}">
    <div class="pagination">
        <c:if test="${currentPage > 1}">
            <a href="?page=${currentPage - 1}&keyword=${keyword}">« Trước</a>
        </c:if>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="active">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="?page=${i}&keyword=${keyword}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:if test="${currentPage < totalPages}">
            <a href="?page=${currentPage + 1}&keyword=${keyword}">Sau »</a>
        </c:if>
    </div>
    <p class="page-info">Trang ${currentPage} / ${totalPages}</p>
</c:if>