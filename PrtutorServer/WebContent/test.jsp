<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>上传upload文件</title>
</head>
<body>
<form action="testServlet" method="post" enctype="multipart/form-data">
上传文件：<input type="text" name="file" />

<p /><input type="submit" value="test" style="width: 50px" />
</form>

</body>
</html>
