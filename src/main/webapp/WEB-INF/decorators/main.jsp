<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title'>BookStore</sitemesh:write></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
</head>
<body>

<!-- ===== HEADER ===== -->
<header class="header">
    <a href="${pageContext.request.contextPath}/home" class="brand">📚 BookStore</a>

    <nav>
        <a href="${pageContext.request.contextPath}/home">Trang Chủ</a>
        <a href="${pageContext.request.contextPath}/books">Sản phẩm</a>

        <c:if test="${sessionScope.user != null && sessionScope.user.admin}">
            <a href="${pageContext.request.contextPath}/admin/books">Trang quản trị</a>
        </c:if>
        <a href="${pageContext.request.contextPath}/cart" class="cart-link">
    	🛒 Giỏ hàng
	    <c:if test="${sessionScope.cartCount > 0}">
	        <span class="cart-badge">${sessionScope.cartCount}</span>
	    </c:if>
		</a>
<a href="${pageContext.request.contextPath}/my-orders">📦 Đơn hàng</a>

        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <span class="user-info">
                    Xin chào, <strong>${sessionScope.user.fullname}</strong>
                </span>
                <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>

<!-- ===== NỘI DUNG CHÍNH ===== -->
<main class="container">
    <sitemesh:write property='body'/>
</main>

<!-- ===== FOOTER ===== -->
<footer class="footer">
    <p>Lê Đình Tùng - 24162140 - Đề 01</p>
</footer>

</body>
</html>