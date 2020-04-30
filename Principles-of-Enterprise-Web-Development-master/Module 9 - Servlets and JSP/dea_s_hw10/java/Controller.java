package dea_hw10;

import java.io.IOException;

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
	public static final long serialVersionUID = 1L;
	public static final String HIKE = "hike";
	public static final String MONTH = "Month";
	public static final String DAY = "Day";
	public static final String YEAR = "Year";
	public static final String HIKEID = "hikes";
	public static final String LENGTH = "length";
	public static final String HIKERS = "hikers";
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
		// might have an issue with these 3 values not resetting
    	String errorMessage = "";
    	boolean goodInput = true;
    	boolean noErrors = true;
		
		response.setContentType("text/html;charset=UTF-8");
			
		//Get an HttpSession obj to store the Hike bean
		HttpSession session = request.getSession();
		ServletContext servletContext = getServletContext();
		
		// check if there is a hike object already created
		Hike hike = (Hike) session.getAttribute(HIKE);
		
		// Create a new hike obj if it hasn't been made yet
		if(hike == null) {
			hike = new Hike();
			session.setAttribute(HIKEID, hike);
			
			// forward object in HttpRequest to the index.jsp
			RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
			dispatcher.forward(request, response);
		}
		// If there already is a hike bean made, then it has come from the index.jsp page and can be processed
		else {
			int month = 0;
			int day = 0;
			int year = 0;
			int hikeID = 0;
			int length = 0;
			int hikers = 0;
			
			// Reset error messages
			hike.setErrorMessage("");
			
			// attempt to parse the strings for int inputs
			try{
				month = Integer.parseInt(request.getParameter(MONTH));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid month input.");
				goodInput = false;
			}
			try{
				day = Integer.parseInt(request.getParameter(DAY));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid day input.");
				goodInput = false;
			}
			try{
				year = Integer.parseInt(request.getParameter(YEAR));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid year input.");
				goodInput = false;
			}
			
			// May not need this error checking for hike ID and length
			try{
				hikeID = Integer.parseInt(request.getParameter(HIKEID));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid hikeID input.");
				goodInput = false;
			}
			try{
				length = Integer.parseInt(request.getParameter(LENGTH));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid length input.");
				goodInput = false;
			}
			
			
			try{
				hikers = Integer.parseInt(request.getParameter(HIKERS));
			}
			catch (NumberFormatException e) {
				hike.addErrorMessage("Invalid hikers input.");
				goodInput = false;
			}
			
			// Set the values of the hike Bean if goodInput = true
			if(goodInput) {				
				hike.setMonth(month);
				hike.setDay(day);
				hike.setYear(year);
				hike.setHikeID(hikeID);
				hike.setLength(length);
				hike.setHikers(hikers);	
				
				// Check all of the values are of good ranges
				boolean goodDate = hike.checkDate();
				boolean goodDateCombo = hike.checkDayMonthCombo();
				boolean goodID = hike.checkHikeID();
				boolean goodLength = hike.checkLength();
				boolean goodHikers = hike.checkHikers();
				boolean goodHikeCombo = hike.checkHikeCombo();
				boolean goodSeason = hike.checkSeason();
				
				// Add to error message what's wrong
				if(goodDate == false) {
					hike.addErrorMessage("Day/Month/Year have incorrect values.");
					noErrors = false;
				}
				if(goodDateCombo == false) {
					hike.addErrorMessage("Day and Month combo is invalid.");
					noErrors = false;
				}
				if(goodID == false) {
					hike.addErrorMessage("HikeID has incorrect value.");
					noErrors = false;
				}
				if(goodLength == false) {
					hike.addErrorMessage("Length has incorrect value.");
					noErrors = false;
				}
				if(goodHikers == false) {
					hike.addErrorMessage("Hikers has incorrect value.");
					noErrors = false;
				}
				if(goodHikeCombo == false) {
					hike.addErrorMessage("Hike and length are incorrect.");
					noErrors = false;
				}
				if(goodSeason == false) {
					hike.addErrorMessage("Date is out of season.");
					noErrors = false;
				}
				
				// Forward to success.jsp with the calculated price
				if(noErrors) {
					hike.setPrice(hike.calcPrice());
					RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/success.jsp");
					dispatcher.forward(request, response);
				}
				
				// Otherwise forward to index.jsp with error messages about what input is wrong
				else {
					RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
			}
			// Forward to index.jps with error messages about invalid input data
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
