/*
 * This servlet is to be used with form.html to provide a reactive web form for the users of the BHC
 * to use to check for bookings for hikes and find out the prices of those hikes. The user starts
 * on the form.html page and enters prompted information that is then sent back to this servlet to
 * handle and return a page with the same form and a price if the information is valid, or return 
 * the same form and listed errors.
 * @author Steven Dea
 */
package dea_s_hw7;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class hikeServlet
 */
@WebServlet("/hikeServlet")
public class hikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public hikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		// Initialized variables
		String month = "1";
		int monthInt = 1;
		String day = "1";
		int dayInt = 1;
		String year = "2020";
		int yearInt = 2020;
		String hike = "Gardiner Lake";
		int hikeInt = 0;
		String length = "3";
		int lengthInt = 1;
		// Error checking
		boolean isError = false;
		String errorMessage = "";
		
		
		// Take values from input form and do input checking on month/day/year
		// xxxInt values of -1 denote an error with that particular input
		
		try {
			month = request.getParameter("Month");
			if (month == null) {
				month = "<none entered>";
				isError = true;
				errorMessage = errorMessage + "Please enter a month 1-12.\n";
			}
			else {
				// checking due to BookingDay
				monthInt = Integer.parseInt(month);
				if ((monthInt > 11) || (monthInt < 0)){
					isError = true;
					errorMessage = errorMessage + "Please enter a month 1-12.\n";
				}
			}

			day = request.getParameter("Day");
			if (day == null) {
				day = "<none entered>";
				isError = true;
				errorMessage = errorMessage + "Please enter a day 1-31.\n";
			}
			else {
				dayInt = Integer.parseInt(day);
				if ((dayInt > 31) || (dayInt < 1)) {
				isError = true;
				errorMessage = errorMessage + "Please enter a day 1-31.\n";
				}
			}
			
			year = request.getParameter("Year");
			if (year == null) {
				year = "<none entered>";
				isError = true;
				errorMessage = errorMessage + "Please enter a year 2020-2025.\n";
			}
			else {
				yearInt = Integer.parseInt(year);
				if ((yearInt > 2025) || (yearInt < 2020)) {
					isError = true;
					errorMessage = errorMessage + "Please enter a year 2020-2025.\n";
				}
			}
			
			// not converting between hike and hikeInt correctly
			hike = request.getParameter("hikes");
			if (hike == null) {
				hike = "<none selected>";
				isError = true;
				errorMessage = errorMessage + "Please select a hike.\n";
			}
			else {
				if(hike.equals("Gardiner Lake")) {
					hikeInt = 0;
				}
				if(hike.equals("The Hellroaring Plateau")) {
					hikeInt = 1;
				}
				if(hike.equals("The Beaten Path")) {
					hikeInt = 2;
				}
			}
			
			length = request.getParameter("length");
			if (length == null) {
				length = "<none selected>";
				isError = true;
				errorMessage = errorMessage + "Please select a length.\n";
			}
			else {
				lengthInt = Integer.parseInt(length);
				// Check for invalid combinations of hikes and lengths
				if ((hikeInt == 0) & ((lengthInt != 3) & (lengthInt != 5))) {
					isError = true;
					errorMessage = errorMessage + "Invalid hike and length combo.\n";
				}
				if ((hikeInt == 1) & ((lengthInt != 2) & (lengthInt != 3) & (lengthInt != 4))) {
					isError = true;
					errorMessage = errorMessage + "Invalid hike and length combo.\n";
				}
				if ((hikeInt == 2) & ((lengthInt != 5) & (lengthInt != 7))) {
					isError = true;
					errorMessage = errorMessage + "Invalid hike and length combo.\n";
				}
			}
		
			// Create a BookingDay object to check if in season
			BookingDay bookedDay = new BookingDay(yearInt, monthInt, dayInt);
			// if True: good
			boolean validDate = bookedDay.isValidDate();
			if(validDate == false) {
				isError = true;
				errorMessage = errorMessage + "Booking is invalid.\n";
			}
			// if True: bad
			boolean badDay = bookedDay.badDay();
			if (badDay) {
				isError = true;
				errorMessage = errorMessage + "Day invalid for month.\n";
			}
			
			boolean withinSeason = true;
			if (monthInt < 6 || monthInt >= 10) {
				withinSeason = false;
			}
			
			if (withinSeason == false) {
				isError = true;
				errorMessage = errorMessage + "Booking is not within season.\n";
			}
			
			// Calculate price of the hike
			double price = -1;
			double dayPrice = -1;
			if (hikeInt == 0) {
				dayPrice = 40.00;
			}
			if (hikeInt == 1) {
				dayPrice = 35.00;
			}
			if(hikeInt == 2) {
				dayPrice = 45.00;
			}
			price = dayPrice * lengthInt;
			
			// If there are no errors, return a page with the pricing
			if(isError == false) {
				out.println("<!DOCTYPE html>\n" + 
						"\n" + 
						"<html>\n" + 
						"<head>\n" + 
						"\n" + 
						"<title>Hike Form</title>\n" + 
						"<link rel=\"stylesheet\" href=\"styles.css\">\n" + 
						"\n" + 
						"</head>\n" + 
						"\n" + 
						"<body>\n" + 
						"	<form action=\"http://localhost:8080/dea_s_hw7/hikeServlet\" method=GET>\n" + 
						"	\n" + 
						"	<div class=content-wrapper>\n" + 
						"		\n" + 
						"		<div class=left-column>\n" + 
						"		<fieldset id=\"fieldset1\">\n" + 
						"			<legend id=\"legend1\">Date Selection</legend>\n" + 
						"			Month: <input type=\"TEXT\" name=\"Month\" value=" + month +">\n" + 
						"			<br />\n" + 
						"			Day: <input type=\"TEXT\" name=\"Day\" value=" + day + ">\n" + 
						"			<br />\n" + 
						"			Year: <input type=\"TEXT\" name=\"Year\" value=" + year + ">\n" + 
						"			<br />\n" + 
						"		</fieldset>	\n" + 
						"		</div>\n" + 
						"		\n" + 
						"\n" + 
						"		<div class=middle-column>\n");
						if(hike.equals("Gardiner Lake")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if(hike.equals("The Hellroaring Plateau")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if(hike.equals("The Beaten Path")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if (length.equals("2")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" checked value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("3")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("4")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("5")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("7")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						out.println(
						"		\n" + 
						"	</div>\n" + 
						"	<div class=submit-wrapper>\n" + 
						"		<div class=submit>\n" + 
						"			<input type=\"SUBMIT\">\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"	\n" + 
						"	</form>\n" + 
						"	<div class=\"pricing-wrapper\">\n" + 
						"		<div class=\"pricing\">\n" + 
						"		Price: $" + price + "\n" +
						"		</div>\n" + 
						"	</div>\n" + 
						"</body>\n" + 
						"</html>");
			}
			// If there are errors, return a page with the original-filled-out form and the errors listed
			// need to have individual out.println() for each "changable" value
			else {
				out.println("<!DOCTYPE html>\n" + 
						"\n" + 
						"<html>\n" + 
						"<head>\n" + 
						"\n" + 
						"<title>Hike Form</title>\n" + 
						"<link rel=\"stylesheet\" href=\"styles.css\">\n" + 
						"\n" + 
						"</head>\n" + 
						"\n" + 
						"<body>\n" + 
						"	<form action=\"http://localhost:8080/dea_s_hw7/hikeServlet\" method=GET>\n" + 
						"	\n" + 
						"	<div class=content-wrapper>\n" + 
						"		\n" + 
						"		<div class=left-column>\n" + 
						"		<fieldset id=\"fieldset1\">\n" + 
						"			<legend id=\"legend1\">Date Selection</legend>\n" + 
						"			Month: <input type=\"TEXT\" name=\"Month\" value=" + month +">\n" + 
						"			<br />\n" + 
						"			Day: <input type=\"TEXT\" name=\"Day\" value=" + day + ">\n" + 
						"			<br />\n" + 
						"			Year: <input type=\"TEXT\" name=\"Year\" value=" + year + ">\n" + 
						"			<br />\n" + 
						"		</fieldset>	\n" + 
						"		</div>\n" + 
						"		\n" + 
						"\n" + 
						"		<div class=middle-column>\n");
						if(hike.equals("Gardiner Lake")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if(hike.equals("The Hellroaring Plateau")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if(hike.equals("The Beaten Path")) {
							out.println("<fieldset id=\"fieldset2\">\n" + 
									"				<legend id=\"legend2\">Hike Selection</legend>\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value = \"Gardiner Lake\">Gardiner Lake\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" value=\"The Hellroaring Plateau\">The Hellroaring Plateau\n" + 
									"			<br />\n" + 
									"			<br />\n" + 
									"			<input type=\"RADIO\" name=\"hikes\" checked value=\"The Beaten Path\">The Beaten Path\n" + 
									"			</fieldset>");
						}
						if (length.equals("2")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" checked value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("3")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("4")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("5")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						if (length.equals("7")) {
							out.println(
									"		</div>\n" + 
									"		\n" + 
									"		<div class=right-column>\n" + 
									"			<fieldset id=\"fieldset3\">\n" + 
									"				<legend id=\"legend3\">Length Selection</legend>\n" + 
									"				<input type=\"RADIO\" name=\"length\" value = \"2\">2 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"3\">3 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"4\">4 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" value = \"5\">5 Days\n" + 
									"				<br />" +
									"				<input type=\"RADIO\" name=\"length\" checked value = \"7\">7 Days\n" + 
									"			</fieldset>\n" + 
									"		</div>");
						}
						out.println(
						"		\n" + 
						"	</div>\n" + 
						"	<div class=submit-wrapper>\n" + 
						"		<div class=submit>\n" + 
						"			<input type=\"SUBMIT\">\n" + 
						"		</div>\n" + 
						"	</div>\n" + 
						"	\n" + 
						"	</form>\n" + 
						"	<div class=\"pricing-wrapper\">\n" + 
						"		<div class=\"pricing\">\n" + 
						"		Error: " + errorMessage + "\n" +
						"		</div>\n" + 
						"	</div>\n" + 
						"</body>\n" + 
						"</html>");
			}
				
		}

		finally {
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
