<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="auth-wrap">
    <div class="auth-card">
        <h1>🔑 Đăng nhập</h1>

        <c:if test="${param.logout == 'true'}">
            <div class="alert success">Bạn đã đăng xuất thành công.</div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert success">${successMessage}</div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <label>Email</label>
            <input type="email" name="email" value="${email}" required autofocus>

            <label>Mật khẩu</label>
            <input type="password" name="password" required>

            <button type="submit" class="button full">Đăng nhập</button>
        </form>

        <p class="auth-link">
            Chưa có tài khoản?
            <a href="${pageContext.request.contextPath}/register">Đăng ký ngay</a>
        </p>
    </div>
</div>