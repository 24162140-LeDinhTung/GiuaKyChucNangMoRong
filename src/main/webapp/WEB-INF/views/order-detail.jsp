<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:if test="${param.placed == 'true'}">
    <div class="alert success">
        ✅ Đặt hàng thành công! Chúng tôi sẽ liên hệ xác nhận đơn hàng của bạn.
    </div>
</c:if>

<h1>📄 Chi tiết đơn hàng #${order.orderId}</h1>

<a class="back-link" href="${pageContext.request.contextPath}/my-orders">
    ← Quay lại danh sách đơn hàng
</a>

<div class="order-detail-box">

    <!-- Thông tin đơn -->
    <div class="order-info">
        <h3>Thông tin đơn hàng</h3>
        <table class="detail-table">
            <tr><td class="lbl">Mã đơn:</td><td>#${order.orderId}</td></tr>
            <tr><td class="lbl">Ngày đặt:</td><td>${order.orderDateFormatted}</td></tr>
            <tr><td class="lbl">Trạng thái:</td>
                <td>
                    <span class="order-status status-${order.status}">
                        <c:choose>
                            <c:when test="${order.status == 'PENDING'}">⏳ Chờ xác nhận</c:when>
                            <c:when test="${order.status == 'CONFIRMED'}">✅ Đã xác nhận</c:when>
                            <c:when test="${order.status == 'SHIPPING'}">🚚 Đang giao</c:when>
                            <c:when test="${order.status == 'DELIVERED'}">📦 Đã giao</c:when>
                            <c:when test="${order.status == 'CANCELLED'}">❌ Đã hủy</c:when>
                        </c:choose>
                    </span>
                </td>
            </tr>
            <tr><td class="lbl">Thanh toán:</td><td>💰 COD (Thanh toán khi nhận hàng)</td></tr>
            <tr><td class="lbl">Địa chỉ:</td><td>${order.shippingAddress}</td></tr>
            <tr><td class="lbl">SĐT:</td><td>${order.phone}</td></tr>
            <c:if test="${not empty order.note}">
                <tr><td class="lbl">Ghi chú:</td><td>${order.note}</td></tr>
            </c:if>
        </table>
    </div>

    <!-- Danh sách sản phẩm -->
    <div class="order-items">
        <h3>Sản phẩm</h3>
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Sách</th>
                    <th>Giá</th>
                    <th>SL</th>
                    <th>Thành tiền</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="item" items="${order.items}">
                    <tr>
                        <td>
                            <c:if test="${item.book != null}">
                                <a href="${pageContext.request.contextPath}/book-detail?id=${item.book.bookid}">
                                    ${item.book.title}
                                </a>
                            </c:if>
                        </td>
                        <td>${item.price} đ</td>
                        <td>${item.quantity}</td>
                        <td><strong>${item.subtotal} đ</strong></td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="3" class="text-right"><strong>Tổng cộng:</strong></td>
                    <td class="total-price">${order.totalAmount} đ</td>
                </tr>
            </tfoot>
        </table>
    </div>

</div>