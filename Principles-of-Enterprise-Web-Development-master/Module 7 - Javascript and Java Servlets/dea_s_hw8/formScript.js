/**
 * 
 */
function validateForm(){
	var hikers = document.hikeform.hikers.value;
	var day = document.hikeform.Day.value;
	var month = document.hikeform.Month.value;
	var year = document.hikeform.Year.value;
	
	if ((isNaN(day)) || (day > 31) || (day < 1)){
		alert("Day must be within 1 and 31");
		return false;
	}
	if ((isNaN(month)) || (month > 12) || (month < 1)){
		alert("Month must be within 1 and 12")
		return false;
	}
	if ((isNaN(year)) || (year > 2025) || (year < 2020)){
		alert("Year must be within 2020 and 2025");
		return false;
	}
	
	if ((isNaN(hikers)) || (hikers > 10) ||(hikers < 1)){
		alert("Hiker count must be between 1 and 10");
		return false;
	}
	else {
		return true;
	}
}