<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="navbar">
	<div class="navbar-inner">
		<div class="container">
			<ul class="nav">
				<li<%= (navNum == 1)? " class=\"active\"" : "" %>><a href="${pageContext.request.contextPath}/admin/facebookInfoView">Facebook Info</a></li>
				<li<%= (navNum == 2)? " class=\"active\"" : "" %>><a href="${pageContext.request.contextPath}/admin/promotionDownloadStatisticsView">Promotion Download</a></li>
				<li<%= (navNum == 3)? " class=\"active\"" : "" %>><a href="${pageContext.request.contextPath}/pushMsgTest.do">Push Message</a></li>
			</ul>
		</div>
	</div>
</div>