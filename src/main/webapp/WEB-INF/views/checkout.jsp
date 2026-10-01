<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>💳 Thanh toán đơn hàng</h1>

<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>

<div class="checkout-grid">

    <!-- Cột trái: Form giao hàng -->
    <div class="checkout-form-box">
        <h3>Thông tin giao hàng</h3>

        <form method="post" action="${pageContext.request.contextPath}/checkout">
            <div class="form-group">
                <label>Địa chỉ giao hàng <span class="req">*</span></label>
                <textarea name="shippingAddress" rows="3" required
                          placeholder="Số nhà, đường, phường, quận, thành phố...">${sessionScope.user.fullname != null ? '' : ''}</textarea>
            </div>

            <div class="form-group">
                <label>Số điện thoại <span class="req">*</span></label>
                <input type="text" name="phone" required
                       value="${sessionScope.user.phone}"
                       placeholder="VD: 0901234567">
            </div>

            <div class="form-group">
                <label>Ghi chú (tùy chọn)</label>
                <textarea name="note" rows="2"
                          placeholder="VD: Giao giờ hành chính"></textarea>
            </div>

            <div class="form-group">
                <label>Phương thức thanh toán</label>
                <div class="payment-method">
                    <label class="radio-label">
                        <input type="radio" name="paymentMethod" value="COD" checked>
                        <span>💰 Thanh toán khi nhận hàng (COD)</span>
                    </label>
                </div>
            </div>

            <button type="submit" class="button primary full">
                ✅ Xác nhận đặt hàng
            </button>

            <a class="button secondary full" style="margin-top:8px;"
               href="${pageContext.request.contextPath}/cart">
                ← Quay lại giỏ hàng
            </a>
        </form>
    </div>

    <!-- Cột phải: Tóm tắt đơn hàng -->
    <div class="checkout-summary">
        <h3>Đơn hàng của bạn</h3>

        <c:forEach var="ci" items="${cartItems}">
            <div class="summary-item">
                <span class="summary-title">${ci.book.title}</span>
                <span class="summary-qty">×${ci.quantity}</span>
                <span class="summary-sub">${ci.subtotal} đ</span>
            </div>
        </c:forEach>

        <hr>
        <div class="summary-total">
            <span>Tổng cộng:</span>
            <strong>${cartTotal} đ</strong>
        </div>
    </div>

</div>