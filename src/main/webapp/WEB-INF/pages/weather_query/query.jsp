<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Weather query</title>
</head>
<body>
	<form action="/weather-query/query"
		method="post">
		<p>
			<label>城市</label><input name="city">
		</p>
		<p>
			<button type="submit">查询</button>
		</p>
	</form>
	<c:if test="${ result!=null  }">
		<p>${result}</p>
	</c:if>
</body>
</html>