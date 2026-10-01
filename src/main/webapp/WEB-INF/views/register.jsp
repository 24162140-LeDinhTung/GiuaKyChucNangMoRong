<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="auth-wrap">
    <div class="auth-card">
        <h1>📝 Đăng ký</h1>

        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/register">
            <label>Email <span class="req">*</span></label>
            <input type="email" name="email" value="${email}" required autofocus>

            <label>Họ và tên <span class="req">*</span></label>
            <input type="text" name="fullname" value="${fullname}" required>

            <label>Số điện thoại</label>
            <input type="text" name="phone" value="${phone}">

            <label>Mật khẩu <span class="req">*</span></label>
            <input type="password" name="password" required>

            <label>Xác nhận mật khẩu <span class="req">*</span></label>
            <input type="password" name="confirm" required>

            <button type="submit" class="button full">Đăng ký &amp; nhận OTP</button>
        </form>

        <p class="auth-link">
            Đã có tài khoản?
            <a href="${pageContext.request.contextPath}/login">Đăng nhập</a>
        </p>
    </div>
</div>