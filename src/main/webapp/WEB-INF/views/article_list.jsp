<%@ include file = "./include/top.jsp" %>
<body>
	<div class="container">
	total : ${totalSize}<br>
	<form action="/searchArticle" method="post">
		<input type="text" name="keyword" id="keyword" />
		<input type="submit" value="Submit"/>
	</form>
	<table>
		<c:forEach var="article" items="${articleList}">
			<tr>
			<td>${article.title}</td>
			<td><a href="${article.link}">[Link]</a></td>
			<td>${article.pubDate}</td>
			</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>