<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- board_paging -->
<div class="pagination pagination-centered">
	<ul>
		<c:choose>
			<c:when test="${pageModel.pageNo!=0 && pageModel.pageSize!=0 && pageModel.totalSize!=0}">
				<c:if test="${pageModel.pageNo > 1}">
					<c:url var="goFirstHref" value="javascript:goPage('1');" />
				</c:if>
				<c:choose>
					<c:when test="${goFirstHref != null}">
						<li><a href="${goFirstHref}">&laquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="disabled"><span>&laquo;</span></li>
					</c:otherwise>
				</c:choose>
				<c:if test="${pageModel.pageNo > pageModel.pageRange}">
					<c:url var="goPrevHref" value="javascript:goPage(${pageModel.prevPageNo});" />
				</c:if>
				<c:choose>
					<c:when test="${goPrevHref != null}">
						<li><a href="${goPrevHref}">&lsaquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="disabled"><span>&lsaquo;</span></li>
					</c:otherwise>
				</c:choose>
				<c:forEach var="pageNumber" items="${pageModel.pageNoList}" varStatus="stat">
					<c:choose>
						<c:when test="${pageNumber == pageModel.pageNo}">
							<li class="active"><span>${pageNumber}</span></li>
						</c:when>
						<c:otherwise>
							<li><a href="javascript:goPage(${pageNumber});">${pageNumber}</a></li>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<c:if test="${pageModel.nextPageNo <= pageModel.totalPage}">
					<c:url var="goNextHref" value="javascript:goPage(${pageModel.nextPageNo});" />
				</c:if>
				<c:choose>
					<c:when test="${goNextHref != null}">
						<li><a href="${goNextHref}">&rsaquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="disabled"><span>&rsaquo;</span></li>
					</c:otherwise>
				</c:choose>
				<c:if test="${pageModel.totalPage > pageModel.pageNo}">
					<c:url var="goLastHref" value="javascript:goPage(${pageModel.totalPage});" />
				</c:if>
				<c:choose>
					<c:when test="${goLastHref != null}">
						<li><a href="${goLastHref}">&raquo;</a></li>
					</c:when>
					<c:otherwise>
						<li class="disabled"><span>&raquo;</span></li>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<li class="disabled"><span>&laquo;</span></li>
				<li class="disabled"><span>&lsaquo;</span></li>
				<li class="active"><span>1</span></li>
				<li class="disabled"><span>&rsaquo;</span></li>
				<li class="disabled"><span>&raquo;</span></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
<!-- //board_paging -->