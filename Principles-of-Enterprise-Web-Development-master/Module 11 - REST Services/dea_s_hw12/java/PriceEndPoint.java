package dea_s_hw12;

import javax.servlet.RequestDispatcher;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;


@Path("prices")
public class PriceEndPoint {
	
	// Returns true if all of the String inputs are valid inputs (i.e. can be turned to ints if necessary)
	public String getPricePoint(String month, String day, String year, String hike, String length, String hikers) {
		Hike outputHike = new Hike();
		int monthInt = 0;
		int dayInt = 0;
		int yearInt = 0;
		int hikeInt = 0;
		int lengthInt = 0;
		int hikersInt = 0;
		boolean goodInput = true;
		boolean noErrors = true;
		String output = "";
		
		// attempt to parse the strings for int inputs
		try{
			monthInt = Integer.parseInt(month);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid month input.");
			goodInput = false;
		}
		try{
			dayInt = Integer.parseInt(day);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid day input.");
			goodInput = false;
		}
		try{
			yearInt = Integer.parseInt(year);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid year input.");
			goodInput = false;
		}
		// May not need this error checking for hike ID and length
		try{
			hikeInt = Integer.parseInt(hike);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid hikeID input.");
			goodInput = false;
		}
		try{
			lengthInt = Integer.parseInt(length);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid length input.");
			goodInput = false;
		}
		
		try{
			hikersInt = Integer.parseInt(hikers);
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid hikers input.");
			goodInput = false;
		}
		
		// Set the values of the hike Bean if goodInput = true
		if(goodInput) {				
			outputHike.setMonth(monthInt);
			outputHike.setDay(dayInt);
			outputHike.setYear(yearInt);
			outputHike.setHikeID(hikeInt);
			outputHike.setLength(lengthInt);
			outputHike.setHikers(hikersInt);	
			
			// Check all of the values are of good ranges
			boolean goodDate = outputHike.checkDate();
			boolean goodDateCombo = outputHike.checkDayMonthCombo();
			boolean goodID = outputHike.checkHikeID();
			boolean goodLength = outputHike.checkLength();
			boolean goodHikers = outputHike.checkHikers();
			boolean goodHikeCombo = outputHike.checkHikeCombo();
			boolean goodSeason = outputHike.checkSeason();
			
			// Add to error message what's wrong
			if(goodDate == false) {
				outputHike.addErrorMessage("Day/Month/Year have incorrect values.");
				noErrors = false;
			}
			if(goodDateCombo == false) {
				outputHike.addErrorMessage("Day and Month combo is invalid.");
				noErrors = false;
			}
			if(goodID == false) {
				outputHike.addErrorMessage("HikeID has incorrect value.");
				noErrors = false;
			}
			if(goodLength == false) {
				outputHike.addErrorMessage("Length has incorrect value.");
				noErrors = false;
			}
			if(goodHikers == false) {
				outputHike.addErrorMessage("Hikers has incorrect value.");
				noErrors = false;
			}
			if(goodHikeCombo == false) {
				outputHike.addErrorMessage("Hike and length are incorrect.");
				noErrors = false;
			}
			if(goodSeason == false) {
				outputHike.addErrorMessage("Date is out of season.");
				noErrors = false;
			}
			
			// Forward to success.jsp with the calculated price
			if(noErrors) {
				outputHike.setPrice(outputHike.calcPrice());
				output = "Price: " + outputHike.getPrice();
			}
			else {
				output = "Errors: " + outputHike.getErrorMessage();
			}
		}
		else {
			output = "Errors: " + outputHike.getErrorMessage();
		}
		return output;
	}
	
	
	@POST
	@Path("/pricepoint")
	@Produces(MediaType.TEXT_HTML)
	public String getParamText(@FormParam("Month") String month, @FormParam("Day") String day,
			@FormParam("Year") String year, @FormParam("hikes") String hike, 
			@FormParam("length") String length, @FormParam("hikers") String hikers) {
		String output = getPricePoint(month, day, year, hike, length, hikers);
		
		return "<h1>" + output + "</h1>";
	}
}
