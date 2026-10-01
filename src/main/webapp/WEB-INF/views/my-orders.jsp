<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>📦 Đơn hàng của tôi</h1>

<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-cart">
            <p style="font-size:48px;">📦</p>
            <h3>Bạn chưa có đơn hàng nào</h3>
            <a class="button" href="${pageContext.request.contextPath}/home">
                ← Mua sắm ngay
            </a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="orders-list">
            <c:forEach var="o" items="${orders}">
                <div class="order-card">
                    <div class="order-head">
                        <div>
                            <strong>Đơn #${o.orderId}</strong>
                            <span class="order-date">${o.orderDateFormatted}</span>
                        </div>
                        <span class="order-status status-${o.status}">
                            <c:choose>
                                <c:when test="${o.status == 'PENDING'}">⏳ Chờ xác nhận</c:when>
                                <c:when test="${o.status == 'CONFIRMED'}">✅ Đã xác nhận</c:when>
                                <c:when test="${o.status == 'SHIPPING'}">🚚 Đang giao</c:when>
                                <c:when test="${o.status == 'DELIVERED'}">📦 Đã giao</c:when>
                                <c:when test="${o.status == 'CANCELLED'}">❌ Đã hủy</c:when>
                                <c:otherwise>${o.status}</c:otherwise>
                            </c:choose>
                        </span>
                    </div>

                    <div class="order-body">
                        <p>Số sản phẩm: <strong>${o.totalItems}</strong></p>
                        <p>Thanh toán: <strong>COD</strong></p>
                        <p>Địa chỉ: ${o.shippingAddress}</p>
                        <p>SĐT: ${o.phone}</p>
                    </div>

                    <div class="order-foot">
                        <span class="order-total">Tổng: <strong>${o.totalAmount} đ</strong></span>
                        <a class="button"
                           href="${pageContext.request.contextPath}/order-detail?id=${o.orderId}">
                            Xem chi tiết →
                        </a>
                    </div>
                </div>
            </c:forEach>
        </div>
    </c:otherwise>
</c:choose>