package dea_s_hw13;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("service")
public class Endpoints {
	
	
	// Returns HikeOutput obj with correct pricing and/or error messages
	public HikeOutput getServiceOutput(HikeJson hj) {
		Hike outputHike = new Hike();
		HikeOutput serviceOutput = new HikeOutput();
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
			monthInt = Integer.parseInt(hj.getMonth());
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid month input.");
			goodInput = false;
		}
		try{
			dayInt = Integer.parseInt(hj.getDay());
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid day input.");
			goodInput = false;
		}
		try{
			yearInt = Integer.parseInt(hj.getYear());
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid year input.");
			goodInput = false;
		}
		// May not need this error checking for hike ID and length
		try{
			hikeInt = Integer.parseInt(hj.getHikeID());
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid hikeID input.");
			goodInput = false;
		}
		try{
			lengthInt = Integer.parseInt(hj.getLength());
		}
		catch (NumberFormatException e) {
			outputHike.addErrorMessage("Invalid length input.");
			goodInput = false;
		}
		
		try{
			hikersInt = Integer.parseInt(hj.getHikers());
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
				serviceOutput.setPrice(outputHike.calcPrice());
			}
			else {
				serviceOutput.setErrorMessage(outputHike.getErrorMessage());
				serviceOutput.setPrice(-1);
			}
		}
		else {
			serviceOutput.setErrorMessage(outputHike.getErrorMessage());
			serviceOutput.setPrice(-1);
		}
		return serviceOutput;
	}
	
	@GET
	@Path("load")
	@Produces(MediaType.APPLICATION_JSON)
	public HikeOutput output(HikeJson hj) {
//		System.out.println("GET: " + hj.toString());
		HikeOutput serviceOutput = getServiceOutput(hj);
		return serviceOutput;
	}
	
	@POST
	@Path("load")
	@Consumes(MediaType.APPLICATION_JSON)
	public HikeOutput input(HikeJson hj) {
//		System.out.println("POST: " + hj.toString());
		HikeOutput serviceOutput = output(hj);
		return serviceOutput;
	}
	
}
