<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="auth-wrap">
    <div class="auth-card">
        <h1>🔐 Xác nhận OTP</h1>

        <p class="subtitle">
            Nhập mã OTP 6 số đã được gửi đến email
            <strong>${not empty email ? email : sessionScope.pendingEmail}</strong>
        </p>

        <c:if test="${not empty error}">
            <div class="alert error">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/verify-otp">
            <input type="hidden" name="email"
                   value="${not empty email ? email : sessionScope.pendingEmail}">

            <label>Mã OTP</label>
            <input type="text" name="otp" maxlength="6" inputmode="numeric"
                   pattern="[0-9]{6}" placeholder="______"
                   required autofocus style="text-align:center;font-size:24px;letter-spacing:8px;">

            <button type="submit" class="button full">Xác nhận</button>
        </form>

        <form method="post" action="${pageContext.request.contextPath}/verify-otp"
              style="margin-top:8px;">
            <input type="hidden" name="email"
                   value="${not empty email ? email : sessionScope.pendingEmail}">
            <!-- Nút resend gửi lại OTP: dùng cùng servlet với action=resend -->
            <button type="submit" name="action" value="resend"
                    class="button secondary full">Gửi lại OTP</button>
        </form>

        <p class="auth-link">
            <a href="${pageContext.request.contextPath}/login">← Về trang đăng nhập</a>
        </p>
    </div>
</div>