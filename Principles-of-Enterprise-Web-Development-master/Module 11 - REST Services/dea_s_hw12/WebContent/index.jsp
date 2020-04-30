<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>BHC Booking Portal</title>
	<link rel="stylesheet" href="styles.css">
	<jsp:useBean id="hike" class="dea_s_hw12.Hike" scope="session"></jsp:useBean>
</head>
<body>
	<h2>Welcome to the Beartooth Hiking Company Booking Portal!</h2>
	<form name=hikeform action="/dea_s_hw12/webresources/prices/pricepoint" method=POST>
	<div class=content-wrapper>
		
		<div class=left-column>
			<fieldset id="fieldset1">
				<legend id="legend1">Date Selection</legend>
				Month: <input type="TEXT" name="Month" value="1">
				<br />
				Day: <input type="TEXT" name="Day" value="1">
				<br />
				Year: <input type="TEXT" name="Year" value="2020">
				<br />
			</fieldset>	
		</div>
		

		<div class=middle-column>
			<fieldset id="fieldset2">
				<legend id="legend2">Hike Selection</legend>
			<input type="RADIO" name="hikes" checked value = "0">Gardiner Lake
			<br />
			<br />
			<input type="RADIO" name="hikes" value="1">The Hellroaring Plateau
			<br />
			<br />
			<input type="RADIO" name="hikes" value="2">The Beaten Path
			</fieldset>
		</div>

		
		<div class=right-column>
			<fieldset id="fieldset3">
				<legend id="legend3">Length Selection</legend>
				<input type="RADIO" name="length" checked value = "2">2 Days
				<br />
				<input type="RADIO" name="length" value = "3">3 Days
				<br />
				<input type="RADIO" name="length" value = "4">4 Days
				<br />
				<input type="RADIO" name="length" value = "5">5 Days
				<br />
				<input type="RADIO" name="length" value = "7">7 Days
			</fieldset>
		</div>
		
		<div class=rightest-column>
			<fieldset id="fieldset4">
				<legend id="legend4">Number of Hikers</legend>
				Hikers: <input type="TEXT" name="hikers" value="1">
			</fieldset>
		</div>
	</div>
	
	<div class=submit-wrapper>
		<div class=submit>
			<input type="SUBMIT">
		</div>
	</div>
	
	<div class=error-messages>
		<% if (hike.getErrorMessage() != ""){ %>
		<h2>Errors: </h2><jsp:getProperty name="hike" property="errorMessage" />
		<% } %>
	</div>
	
	</div>

</body>
</html>