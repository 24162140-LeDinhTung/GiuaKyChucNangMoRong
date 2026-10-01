<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>📦 Đơn hàng của tôi</h1>

<%-- ===== TABS LỌC THEO TRẠNG THÁI ===== --%>
<div class="status-tabs">

    <%-- Tab "Tất cả" --%>
    <a href="${pageContext.request.contextPath}/my-orders"
       class="status-tab ${empty filterStatus ? 'active' : ''}">
        Tất cả <span class="tab-count">${totalOrders}</span>
    </a>

    <%-- Các tab theo trạng thái --%>
    <c:forEach var="st" items="${allStatuses}">
        <c:set var="cnt" value="${statusCounts[st]}" />
        <a href="${pageContext.request.contextPath}/my-orders?status=${st}"
           class="status-tab status-${st} ${filterStatus == st ? 'active' : ''}">
            <c:choose>
                <c:when test="${st == 'PENDING'}">⏳ Đơn hàng mới</c:when>
                <c:when test="${st == 'CONFIRMED'}">✅ Đã xác nhận</c:when>
                <c:when test="${st == 'PREPARING'}">📦 Chuẩn bị hàng</c:when>
                <c:when test="${st == 'SHIPPING'}">🚚 Vận chuyển</c:when>
                <c:when test="${st == 'DELIVERING'}">🛵 Đang giao hàng</c:when>
                <c:when test="${st == 'DELIVERED'}">🎉 Đã giao</c:when>
                <c:when test="${st == 'CANCELLED'}">❌ Đơn hàng hủy</c:when>
                <c:when test="${st == 'RETURNED'}">↩️ Đơn hàng hoàn</c:when>
            </c:choose>
            <span class="tab-count">${cnt != null ? cnt : 0}</span>
        </a>
    </c:forEach>
</div>

<%-- ===== DANH SÁCH ĐƠN HÀNG ===== --%>
<c:choose>
    <c:when test="${empty orders}">
        <div class="empty-cart" style="margin-top:20px;">
            <p style="font-size:48px;">📦</p>
            <h3>
                <c:choose>
                    <c:when test="${filterStatus != null}">
                        Không có đơn hàng ở trạng thái này
                    </c:when>
                    <c:otherwise>
                        Bạn chưa có đơn hàng nào
                    </c:otherwise>
                </c:choose>
            </h3>
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
                                <c:when test="${o.status == 'PENDING'}">⏳ Đơn hàng mới</c:when>
                                <c:when test="${o.status == 'CONFIRMED'}">✅ Đã xác nhận</c:when>
                                <c:when test="${o.status == 'PREPARING'}">📦 Chuẩn bị hàng</c:when>
                                <c:when test="${o.status == 'SHIPPING'}">🚚 Vận chuyển</c:when>
                                <c:when test="${o.status == 'DELIVERING'}">🛵 Đang giao hàng</c:when>
                                <c:when test="${o.status == 'DELIVERED'}">🎉 Đã giao</c:when>
                                <c:when test="${o.status == 'CANCELLED'}">❌ Đơn hàng hủy</c:when>
                                <c:when test="${o.status == 'RETURNED'}">↩️ Đơn hàng hoàn</c:when>
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