<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="detail-wrap">

    <a class="back-link" href="${pageContext.request.contextPath}/home">
        ← Quay lại danh sách
    </a>

    <!-- ============================================= -->
    <!-- PHẦN 1: THÔNG TIN SÁCH (2 CỘT)               -->
    <!-- ============================================= -->
    <div class="detail-card">

        <!-- CỘT TRÁI: ẢNH -->
        <div class="detail-cover">
            <c:choose>
                <c:when test="${not empty book.coverImage}">
                    <img src="${pageContext.request.contextPath}/static/images/${book.coverImage}"
                         alt="${book.title}"
                         onerror="this.src='${pageContext.request.contextPath}/static/images/no-image.png'">
                </c:when>
                <c:otherwise>
                    <div class="no-image-large">📖</div>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- CỘT PHẢI: THÔNG TIN CƠ BẢN -->
        <div class="detail-info">
            <table class="detail-table top-table">
                <tr>
                    <td class="lbl">Tiêu đề:</td>
                    <td><strong>${book.title}</strong></td>
                </tr>
                <tr>
                    <td class="lbl">Mã ISBN:</td>
                    <td>${book.isbn}</td>
                </tr>
                <tr>
                    <td class="lbl">Tác giả:</td>
                    <td>${book.authorsAsString}</td>
                </tr>
                <tr>
                    <td class="lbl">Publisher:</td>
                    <td>${book.publisher}</td>
                </tr>
                <tr>
                    <td class="lbl">Publisher date:</td>
                    <td>${book.publishDateFormatted}</td>
                </tr>
            </table>
        </div>
    </div>

	<!-- Thêm vào giỏ -->
	<div class="add-to-cart-box">
	    <form method="post" action="${pageContext.request.contextPath}/cart/add">
	        <input type="hidden" name="bookId" value="${book.bookid}">
	        <label>Số lượng:</label>
	        <input type="number" name="quantity" value="1" min="1" max="10" class="qty-input-lg">
	        <button type="submit" class="button primary">🛒 Thêm vào giỏ hàng</button>
	    </form>
	</div>
    <!-- ============================================= -->
    <!-- PHẦN 2: QUANTITY + REVIEWS                   -->
    <!-- ============================================= -->
    <div class="detail-section">
        <table class="detail-table">
            <tr>
                <td class="lbl">Quantity:</td>
                <td>${book.quantity}</td>
            </tr>
            <tr>
                <td class="lbl">Reviews</td>
                <td>
                    <span class="stars">
                        <c:forEach begin="1" end="5" var="i">
                            <c:choose>
                                <c:when test="${i <= book.avgRating}">★</c:when>
                                <c:otherwise>☆</c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </span>
                    (${book.reviewCount} đánh giá)
                </td>
            </tr>
        </table>
    </div>

    <!-- ============================================= -->
    <!-- PHẦN 3: DANH SÁCH REVIEWS                    -->
    <!-- ============================================= -->
    <div class="detail-section">
        <h3 class="section-title">📝 Đánh giá từ người dùng</h3>

        <c:choose>
            <c:when test="${empty reviews}">
                <p class="empty-reviews">Chưa có đánh giá nào. Hãy là người đầu tiên!</p>
            </c:when>
            <c:otherwise>
                <div class="review-list">
                    <c:forEach var="r" items="${reviews}">
                        <%-- r[0]=email, r[1]=fullname, r[2]=rating, r[3]=review_text --%>
                        <div class="review-item">
                            <div class="review-header">
                                <strong class="review-user">${r[1]}</strong>
                                <span class="review-email">(${r[0]})</span>
                                <span class="stars small">
                                    <c:forEach begin="1" end="5" var="i">
                                        <c:choose>
                                            <c:when test="${i <= r[2]}">★</c:when>
                                            <c:otherwise>☆</c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </span>
                            </div>
                            <p class="review-text">
                                <c:out value="${r[3]}" default="(Không có nội dung)"/>
                            </p>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- ============================================= -->
    <!-- PHẦN 4: FORM THÊM REVIEW                     -->
    <!-- ============================================= -->
    <div class="detail-section">
        <h3 class="section-title">✍️ Thêm đánh giá của bạn</h3>

        <form class="review-form"
              action="${pageContext.request.contextPath}/add-review"
              method="post">

            <input type="hidden" name="bookid" value="${book.bookid}">

            <div class="form-row">
                <label>Đánh giá:</label>
                <select name="rating" required>
                    <option value="5">★★★★★ (5 sao)</option>
                    <option value="4">★★★★☆ (4 sao)</option>
                    <option value="3">★★★☆☆ (3 sao)</option>
                    <option value="2">★★☆☆☆ (2 sao)</option>
                    <option value="1">★☆☆☆☆ (1 sao)</option>
                </select>
            </div>

            <div class="form-row">
                <label>Nội dung:</label>
                <textarea name="reviewText" rows="4"
                          placeholder="Chia sẻ cảm nhận của bạn về cuốn sách này..."></textarea>
            </div>

            <button type="submit" class="button">Submit</button>
        </form>
    </div>

</div>