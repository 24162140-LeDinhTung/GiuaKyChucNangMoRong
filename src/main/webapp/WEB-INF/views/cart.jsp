<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<h1>🛒 Giỏ hàng của bạn</h1>

<c:if test="${not empty error}">
    <div class="alert error">${error}</div>
</c:if>

<c:choose>
    <%-- ============ GIỎ TRỐNG ============ --%>
    <c:when test="${empty cartItems}">
        <div class="empty-cart">
            <p style="font-size:48px;">🛒</p>
            <h3>Giỏ hàng đang trống</h3>
            <p>Hãy thêm sách vào giỏ để tiếp tục mua sắm.</p>
            <a class="button" href="${pageContext.request.contextPath}/home">
                ← Tiếp tục mua sắm
            </a>
        </div>
    </c:when>

    <%-- ============ CÓ SẢN PHẨM ============ --%>
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
                        <%-- ẢNH --%>
                        <td>
                            <img class="cart-thumb"
                                 src="${pageContext.request.contextPath}/static/images/${ci.book.coverImage}"
                                 onerror="this.src='${pageContext.request.contextPath}/static/images/no-image.png'"
                                 alt="">
                        </td>

                        <%-- TÊN SÁCH --%>
                        <td>
                            <a href="${pageContext.request.contextPath}/book-detail?id=${ci.book.bookid}">
                                ${ci.book.title}
                            </a>
                            <br>
                            <small>Tác giả: ${ci.book.authorsAsString}</small>
                        </td>

                        <%-- GIÁ --%>
                        <td>${ci.book.price} đ</td>

                        <%-- ===== SỐ LƯỢNG: 3 FORM RIÊNG ===== --%>
                        <td>
                            <div class="qty-form">

                                <%-- ===== NÚT GIẢM ===== --%>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/cart/update"
                                      style="display:inline">
                                    <input type="hidden" name="cartItemId" value="${ci.cartItemId}">
                                    <input type="hidden" name="quantity" value="${ci.quantity - 1}">

                                    <c:choose>
                                        <c:when test="${ci.quantity <= 1}">
                                            <button type="submit" class="qty-btn" disabled
                                                    title="Đã đạt số lượng tối thiểu">−</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="qty-btn"
                                                    title="Giảm số lượng">−</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>

                                <%-- ===== Ô NHẬP SỐ LƯỢNG ===== --%>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/cart/update"
                                      style="display:inline">
                                    <input type="hidden" name="cartItemId" value="${ci.cartItemId}">
                                    <input type="number" name="quantity"
                                           value="${ci.quantity}" min="1" max="10"
                                           class="qty-input"
                                           onchange="this.form.submit()">
                                </form>

                                <%-- ===== NÚT TĂNG ===== --%>
                                <form method="post"
                                      action="${pageContext.request.contextPath}/cart/update"
                                      style="display:inline">
                                    <input type="hidden" name="cartItemId" value="${ci.cartItemId}">
                                    <input type="hidden" name="quantity" value="${ci.quantity + 1}">

                                    <c:choose>
                                        <c:when test="${ci.quantity >= 10}">
                                            <button type="submit" class="qty-btn" disabled
                                                    title="Đã đạt số lượng tối đa">+</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" class="qty-btn"
                                                    title="Tăng số lượng">+</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>

                            </div>
                        </td>

                        <%-- THÀNH TIỀN --%>
                        <td><strong>${ci.subtotal} đ</strong></td>

                        <%-- NÚT XÓA --%>
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

        <%-- ===== NÚT HÀNH ĐỘNG ===== --%>
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