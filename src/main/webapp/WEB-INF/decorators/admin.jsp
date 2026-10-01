<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title><sitemesh:write property='title'>Admin - BookStore</sitemesh:write></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/app.css">
</head>
<body class="admin-layout">

<!-- ===== SIDEBAR ADMIN ===== -->
<aside class="sidebar">
    <h2>⚙️ Quản Trị</h2>
    <ul>
        <li><a href="${pageContext.request.contextPath}/admin/books">📖 Quản lý Sách</a></li>
        <li><a href="${pageContext.request.contextPath}/admin/authors">✍️ Quản lý Tác giả</a></li>
        <li><a href="${pageContext.request.contextPath}/home">🏠 Về trang chủ</a></li>
        <li><a href="${pageContext.request.contextPath}/logout">🚪 Đăng xuất</a></li>
    </ul>
</aside>

<!-- ===== NỘI DUNG CHÍNH ===== -->
<div class="admin-content">
    <header class="admin-header">
        <span>Xin chào, <strong>${sessionScope.user.fullname}</strong></span>
    </header>

    <main class="container">
        <sitemesh:write property='body'/>
    </main>

    <footer class="footer">
        <p>Lê Đình Tùng - 24162140 - Đề 01</p>
    </footer>
</div>

</body>
</html>