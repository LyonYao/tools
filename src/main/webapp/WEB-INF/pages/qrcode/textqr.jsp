<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QrCode Generate</title>
</head>
<body>
	<form action="/rqCode/textRqCodeGenerate"
		method="post">
		<p>
			<label>CronStr</label><input name="content">
		</p>
		<p>
			<button type="submit">生成</button>
		</p>
	</form>
	<c:if test="${ img!=null }">
		<img src="${img}"/>
	</c:if>
</body>
</html>