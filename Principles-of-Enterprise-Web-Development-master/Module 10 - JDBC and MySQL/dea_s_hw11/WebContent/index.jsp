<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>BHC Database</title>
	<link rel="stylesheet" href="styles.css">
	<jsp:useBean id="output" class="dea_s_hw11.dbOutput" scope="session"></jsp:useBean>
</head>

<body>
	<h2>Welcome to the BHC Internal Database Portal</h2>
	<form name="dbform" action="http://web6.jhuep.com/dea_hw11/Controller" method=GET>
		<fieldset id="dateform">
			<legend id="dateselection">Date Selection</legend>
			Year: <input type="TEXT" name="Year" value="2000">
			Month: <input type="TEXT" name="Month" value="01">
			Day: <input type="TEXT" name="Day" value="01">
		</fieldset>
		
		<input type="SUBMIT">
	</form>
	<div class=error-messages>
		<% if (output.getErrorMessage() != ""){ %>
		<h2>Errors: </h2><jsp:getProperty name="output" property="errorMessage" />
		<% } %>
	</div>
</body>
</html>