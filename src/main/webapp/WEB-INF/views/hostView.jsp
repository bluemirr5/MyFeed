<%@ include file = "./include/top.jsp" %>
<body>
	<div class="container">
	total : ${totalSize} <br>
	<form action="/insertHost" method="post">
		<input type="text" name="hostUrl" id="hostUrl" />
		<input type="submit" value="Submit"/>
	</form>
	<form action="/insertHostbyFile" method="post" enctype="multipart/form-data" id="conditionalForm">
		<span class="select_file">	
			<em>File</em>
			<input type="file" name="excelFile" id="excelFile"/>
			<input type="submit" value="Submit"/>
		</span>  
	</form>
	<table>
		<c:forEach var="host" items="${hostList}">
			<tr>
			<td>${host.title}</td>
			<td><img src="${host.imgUrl}" width="200" heigh="200"/></td>
			<td>${host.description}</td>
			<td><a href="${host.hostUrl}">[Link]</a></td>
			</tr>
		</c:forEach>
	</table>
	</div>
</body>
</html>