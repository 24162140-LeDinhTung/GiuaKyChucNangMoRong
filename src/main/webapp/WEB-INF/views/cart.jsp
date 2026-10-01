<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>🛒 Giỏ hàng của bạn</h1>

<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>

<c:choose>
    <c:when test="${empty cartItems}">
        <div class="empty-cart">
            <p style="font-size:48px;">🛒</p>
            <h3>Giỏ hàng đang trống</h3>
            <p>Hãy thêm sách vào giỏ để tiếp tục mua sắm.</p>
            <a class="button" href="${pageContext.request.contextPath}/home">← Tiếp tục mua sắm</a>
        </div>
    </c:when>

    <c:otherwise>
        <table class="cart-table">
            <thead>
                <tr>
                    <th>Ảnh</th>
                    <th>Sách</th>
                    <th>Giá</th>
                    <th>Số lượng</th>
                    <th>Thành tiền</th>
                    <th></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="ci" items="${cartItems}">
                    <tr>
                        <td>
                            <img class="cart-thumb"
                                 src="${pageContext.request.contextPath}/static/images/${ci.book.coverImage}"
                                 onerror="this.src='${pageContext.request.contextPath}/static/images/no-image.png'"
                                 alt="">
                        </td>
                        <td>
                            <a href="${pageContext.request.contextPath}/book-detail?id=${ci.book.bookid}">
                                ${ci.book.title}
                            </a>
                            <br>
                            <small>Tác giả: ${ci.book.authorsAsString}</small>
                        </td>
                        <td>${ci.book.price} đ</td>
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/cart/update"
                                  class="qty-form">
                                <input type="hidden" name="cartItemId" value="${ci.cartItemId}">
                                <button type="submit" name="quantity" value="${ci.quantity - 1}"
                                        class="qty-btn">−</button>
                                <input type="number" name="quantity" value="${ci.quantity}"
                                       min="1" max="10" class="qty-input">
                                <button type="submit" name="quantity" value="${ci.quantity + 1}"
                                        class="qty-btn">+</button>
                            </form>
                        </td>
                        <td><strong>${ci.subtotal} đ</strong></td>
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/cart/remove"
                                  onsubmit="return confirm('Xóa sách này khỏi giỏ?');">
                                <input type="hidden" name="cartItemId" value="${ci.cartItemId}">
                                <button type="submit" class="btn-delete">🗑️</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="4" class="text-right"><strong>Tổng cộng:</strong></td>
                    <td colspan="2" class="total-price">${cartTotal} đ</td>
                </tr>
            </tfoot>
        </table>

        <div class="cart-actions">
            <form method="post" action="${pageContext.request.contextPath}/cart/clear"
                  onsubmit="return confirm('Xóa toàn bộ giỏ hàng?');">
                <button type="submit" class="button secondary">🗑️ Xóa toàn bộ</button>
            </form>

            <a class="button" href="${pageContext.request.contextPath}/home">
                ← Tiếp tục mua sắm
            </a>

            <a class="button primary" href="${pageContext.request.contextPath}/checkout">
                💳 Thanh toán COD →
            </a>
        </div>
    </c:otherwise>
</c:choose>