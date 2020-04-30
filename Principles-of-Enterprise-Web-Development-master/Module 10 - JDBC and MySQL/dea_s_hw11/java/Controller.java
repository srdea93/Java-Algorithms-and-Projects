package dea_s_hw11;

import java.io.IOException;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String OUTPUT = "output";
	public static final String YEAR = "Year";
	public static final String MONTH = "Month";
	public static final String DAY = "Day";
	
	// Change this to web6.jhuep.com before submission
	private final static String url = "jdbc:mysql://web6.jhuep.com:3306/";
	// The qualified class name for the mysql driver
	private final static String driver = "com.mysql.jdbc.Driver";
	// The user we wish to connect as. for internal testing we can make a new account on our database
	private final static String user = "johncolter";
	// The password associated with the particular account we are connecting with
	private final static String password = "LetMeIn";
	// Name of the schema we are connecting to
	private final static String db = "class";
	
	// Do not need to use this with web6 connections
//	private final static String options = "?useSSL=false";
	
	// Appears to be some strange issue with this version of JDBC driver where have to specify timezone
//	private final static String options = "?serverTimezone=UTC";
	
	private final static String options = "";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int year = 0;
		int month = 0;
		int day = 0;
		boolean validInput = true;
		boolean goodInput = true;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		response.setContentType("text/html;charset=UTF-8");
		
		//Get an HttpSession obj to store the Hike bean
		HttpSession session = request.getSession();
		ServletContext servletContext = getServletContext();
		
		// check if there is a hike object already created
		dbOutput output = (dbOutput) session.getAttribute(OUTPUT);
		
		// Create a new output obj if it hasn't been made yet
		if(output == null) {
			output = new dbOutput();
			session.setAttribute(OUTPUT, output);
			
//			output.addLineToOutput("Reaches: " + "create new output obj");
			
			// forward object in HttpRequest to the index.jsp
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}

		// controller reaches this point
		else {
//			output.addLineToOutput("Reaches: " + "else after creating a new hike obj");
			// Clear errors
			output.setErrorMessage("");
			
			// Parse jsp form for input data
			try{
				year = Integer.parseInt(request.getParameter(YEAR));
			}
			catch (NumberFormatException e) {
				output.addErrorMessage("Year input invalid. ");
				validInput = false;
			}
			try{
				month = Integer.parseInt(request.getParameter(MONTH));
			}
			catch (NumberFormatException e) {
				output.addErrorMessage("Month input invalid. ");
				validInput = false;
			}
			try{
				day = Integer.parseInt(request.getParameter(DAY));
			}
			catch (NumberFormatException e) {
				output.addErrorMessage("Day input invalid. ");
				validInput = false;
			}
			
			if(validInput) {
//				output.addLineToOutput("Reaches: " + "valid input test");
				BookingDay bookDay = new BookingDay(year, month, day);
				
				// Check to make sure that the data is of valid ranges
				if(year < 2000|| year > 2025) {
					output.addErrorMessage("Year is not within range. ");
					goodInput = false;
				}
				if(month < 1 || month > 12) {
					output.addErrorMessage("Month is not within range. ");
					goodInput = false;
				}
				if(day < 1 || day > 31) {
					output.addErrorMessage("Day is not within range. ");
					goodInput = false;
				}
				if(bookDay.badDay()) {
					output.addErrorMessage("Month/Day combo is invalid. ");
					goodInput = false;
				}
				
				// If all of our input is good, we will query the database
				if(goodInput) {
					//Clear output
					output.setOutput("");
					
					// does not reach inside this connection
					try (Connection conn = DriverManager.getConnection(url + db + options, user, password);
							Statement statement = conn.createStatement()) {
						
//						output.addLineToOutput("Reaches: " + "inside connection establishment");
						
						String query = "SELECT reservation.First, reservation.Last, StartDay, NumberOfDays, locations.location, guides.First, guides.Last \n" + 
								"FROM reservation\n" + 
								"	LEFT JOIN locations on reservation.location = idlocations\n" + 
								"   LEFT JOIN guides on guide = idguides\n" + 
								"WHERE StartDay >= " + "'" + year + "-" + month + "-" + day + "'";
						ResultSet rs = statement.executeQuery(query);
//						output.setOutput("");
						output.addLineToOutput("<table>");
						output.addLineToOutput("<tr>");
						output.addLineToOutput("<th>Reservation Name</th>");
						output.addLineToOutput("<th>Start Day</th>");
						output.addLineToOutput("<th>End Day</th>");
						output.addLineToOutput("<th>Location</th>");
						output.addLineToOutput("<th>Guide Name</th>");
						output.addLineToOutput("</tr>");
						
						// For every line in the database we grab the pertinent information and add to our output
						while(rs.next()) {
//							output.addLineToOutput("Reaches: " + "inside rs.next while loop");
							output.addLineToOutput("<tr>");
							output.addLineToOutput("<th>" + rs.getString("reservation.First") + " " + rs.getString("reservation.Last") + "</th>");
							output.addLineToOutput("<th>" + rs.getString("StartDay") +  "</th>");
							
							// Parse start date string 
							String startDate = rs.getString("StartDay");
							String dateArr[] = startDate.split("\\-");
							int startMonth = Integer.parseInt(dateArr[1]);
							int endMonth = startMonth;
							int startDay = Integer.parseInt(dateArr[2]);
							int length = Integer.parseInt(rs.getString("NumberOfDays"));
							int endDay = startDay + length;
			
							// Max days in month = 31
							if((startMonth == 5 || startMonth == 7 || startMonth == 8) & endDay > 31) {
								endMonth = startMonth + 1;
								endDay = endDay % 31;
							}
							// Max days in month = 30
							if(startMonth == 6 & endDay > 30) {
								endMonth = startMonth + 1;
								endDay = endDay % 30;
							}
							String endMonthStr = "";
							String endDayStr = "";
							
							// Format the string output if less than 2 digits
							if(endMonth < 10) {
								endMonthStr = "0" + endMonth;
							}
							else {
								endMonthStr = endMonthStr + endMonth;
							}
							if(endDay < 10) {
								endDayStr = "0" + endDay;
							}
							else {
								endDayStr = endDayStr + endDay;
							}
							String endDate = dateArr[0] + "-" + endMonthStr + "-" + endDayStr;
							
							// Add the day length to it. Need to accommodate days going into next month from May-August.
							output.addLineToOutput("<th>" + endDate + "</th>");
							output.addLineToOutput("<th>" + rs.getString("locations.location") + "</th>");
							output.addLineToOutput("<th>" + rs.getString("guides.First") + " " + rs.getString("guides.Last") + "</th>");
							output.addLineToOutput("</tr>");
						}
						output.addLineToOutput("</table>");
						rs.close();
						
					}
					catch (SQLException e) {
						e.printStackTrace();
						output.setErrorMessage(e.getMessage());
						// Forward to index with errors
						RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
						dispatcher.forward(request, response);
					}
					// Forward to the return database information page
//					output.addLineToOutput("Reaches: " + "forwarding to return page");
					RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/return.jsp");
					dispatcher.forward(request, response);
				}
				// Send to index with error messages
				else {
					RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
			}
			// Otherwise forward to index.jsp with error messages about what input is wrong
			else {
				RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
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
