<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>📚 Danh sách sách</h1>

<p class="welcome">
    Xin chào <strong>${sessionScope.user.fullname}</strong>
    <c:if test="${sessionScope.user.admin}">
        <span class="badge">ADMIN</span>
    </c:if>
</p>

<!-- ===== GRID SÁCH ===== -->
<div class="book-grid">
    <c:forEach var="b" items="${books}">
        <div class="book-card">

            <!-- COVER IMAGE -->
            <div class="book-cover">
                <c:choose>
                    <c:when test="${not empty b.coverImage}">
                        <img src="${pageContext.request.contextPath}/static/images/${b.coverImage}"
                             alt="${b.title}"
                             onerror="this.src='${pageContext.request.contextPath}/static/images/no-image.png'">
                    </c:when>
                    <c:otherwise>
                        <div class="no-image">📖</div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- INFO -->
            <div class="book-info">
                <%-- ⭐ Tiêu đề là link tới trang chi tiết (Câu 4) --%>
                <h3 class="book-title">
                    <a href="${pageContext.request.contextPath}/book-detail?id=${b.bookid}">
                        ${b.title}
                    </a>
                </h3>

                <div class="book-meta">
                    <p><span class="label">Mã ISBN:</span> ${b.isbn}</p>
                    <p><span class="label">Tác giả:</span> ${b.authorsAsString}</p>
                    <p><span class="label">Publisher:</span> ${b.publisher}</p>
                    <p><span class="label">Publisher date:</span> ${b.publishDateFormatted}</p>
                    <p><span class="label">Quantity:</span> ${b.quantity}</p>
                    <p class="review">
                        <span class="label">Review:</span>
                        <span class="stars">
                            <c:forEach begin="1" end="5" var="i">
                                <c:choose>
                                    <c:when test="${i <= b.avgRating}">★</c:when>
                                    <c:otherwise>☆</c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </span>
                        (${b.reviewCount})
                    </p>
                </div>

                <a class="button detail-btn"
                   href="${pageContext.request.contextPath}/book-detail?id=${b.bookid}">
                    Xem chi tiết →
                </a>
            </div>
        </div>
    </c:forEach>

    <c:if test="${empty books}">
        <div class="empty-state">
            <h3>Không có sách nào</h3>
            <p>Vui lòng thêm sách vào database để test.</p>
        </div>
    </c:if>
</div>

<!-- ===== PHÂN TRANG ===== -->
<c:if test="${totalPages > 1}">
    <div class="pagination">
        <!-- Nút Previous -->
        <c:choose>
            <c:when test="${currentPage > 1}">
                <a href="${pageContext.request.contextPath}/home?page=${currentPage - 1}">« Trước</a>
            </c:when>
            <c:otherwise>
                <span class="disabled">« Trước</span>
            </c:otherwise>
        </c:choose>

        <!-- Số trang -->
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="active">${i}</span>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/home?page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!-- Nút Next -->
        <c:choose>
            <c:when test="${currentPage < totalPages}">
                <a href="${pageContext.request.contextPath}/home?page=${currentPage + 1}">Sau »</a>
            </c:when>
            <c:otherwise>
                <span class="disabled">Sau »</span>
            </c:otherwise>
        </c:choose>
    </div>

    <p class="page-info">
        Trang ${currentPage} / ${totalPages}
    </p>
</c:if>