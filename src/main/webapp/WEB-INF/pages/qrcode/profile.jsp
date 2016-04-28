<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Profile QrCode Generate</title>
<style type="text/css" >
	p{
		line-height: 28px;
	}
	 label{
		min-width: 170px!important;
		text-align:right;
		background-color: darkgray;
		display:inline-block; 
	}
	input {
		height: 26px;
		margin-left: 25px;
	}
</style>
</head>
<body>
	<h1>二维码名片生成</h1>
	<form action="/rqCode/profileRqCodeGenerate"
		method="post">
		<p>
			<label>姓名</label><input name="name">
		</p>
		<p>
			<label>个人电话</label><input name="cellphone">
		</p>
		<p>
			<label>公司电话</label><input name="tellphone">
		</p>
		<p>
			<label>传真号码</label><input name="fax">
		</p>
		<p>
			<label>电子邮件</label><input name="email">
		</p>
		<p>
			<label>个人主页</label><input name="url">
		</p>
		<p>
			<label>公司名称</label><input name="company">
		</p>
		<p>
			<label>职位</label><input name="role">
		</p>
		<p>
			<label>职称</label><input name="title">
		</p>
		<p>
			<label>工作地址</label><input name="address">
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