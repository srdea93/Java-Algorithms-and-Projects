<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Returned Database Information</title>
	<link rel="stylesheet" href="styles.css">
	<jsp:useBean id="output" class="dea_s_hw11.dbOutput" scope="session"></jsp:useBean>
</head>
<body>
	<h2>Returned Database Information</h2>
	<p>
		<jsp:getProperty name = "output" property = "output"/>
	</p>

</body>
</html>