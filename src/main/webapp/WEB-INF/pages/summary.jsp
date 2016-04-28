<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Summary -Index</title>
</head>
<body>
	<dl>
		<c:forEach items="${categories}" var="category">
			<dt>${category.name }</dt>
			<c:forEach items="${category.features}" var="feature">
				<dd>
					<a target="_blank"
						href="/${feature.url }"><span>${feature.name}</span>-${feature.desc}</a><a target="_blank" 
						href="/code/list-relative-code?url=${feature.url }">相关代码</a>
				</dd>
			</c:forEach>
		</c:forEach>
	</dl>

</body>
</html>