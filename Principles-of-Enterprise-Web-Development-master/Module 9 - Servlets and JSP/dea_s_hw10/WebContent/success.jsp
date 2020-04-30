<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Booking Quote</title>
	<jsp:useBean id="hike" class="dea_hw10.Hike" scope="session"></jsp:useBean>
</head>
<body>
	<h2>Booking Quote Successful</h2>
	<h3>Booking for:  
	<% if(hike.getHikeID() == 0){ %>	
	Gardiner Lake 
	<% } %>
	<% if(hike.getHikeID() == 1){ %>	
	The Hellroaring Plateau 
	<% } %>
	<% if(hike.getHikeID() == 2){ %>	
	The Beaten Path 
	<% } %>
	for <jsp:getProperty name = "hike" property = "hikers"/> hiker(s) 
	for <jsp:getProperty name = "hike" property = "length"/> days.
	
	
	</h3>
	<h3>Price: $<jsp:getProperty name="hike" property="price"></jsp:getProperty></h3>

</body>
</html>