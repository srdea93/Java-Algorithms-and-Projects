package dea_s_hw9;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BhcThread extends Thread {
	
	// Initialize all input values to -1 so set up error handling if a value is not changed
	private boolean continueRunning = true;
	private int hikeID = -1;
	private int beginYear = -1;
	private int beginMonth = -1;
	private int beginDay = -1;
	private int duration = -1;
	private String errorMessage = "";
	
	// errorCount is used to keep track of whether or not an error occurred in any of the parsing
	private int errorCount = 0;
	private final Socket socket;
	
	// Make sure this thread sleeps every once in awhile to allow others to run
	private final long delay = 1000;
	
	public BhcThread(Socket clientSocket) {
		super();
		this.socket = clientSocket;
	}
	
	public int getHikeID() {
		return this.hikeID;
	}
	
	public void setHikeID(int hikeID) {
		this.hikeID = hikeID;
	}
	
	public int getBeginYear() {
		return this.beginYear;
	}
	
	public void setBeginYear(int beginYear) {
		this.beginYear = beginYear;
	}
	
	public int getBeginMonth() {
		return this.beginMonth;
	}
	
	public void setBeginMonth(int beginMonth) {
		this.beginMonth = beginMonth;
	}
	
	public int getBeginDay() {
		return this.beginDay;
	}
	
	public void setBeginDay(int beginDay) {
		this.beginDay = beginDay;
	}
	
	public int getDuration() {
		return this.duration;
	}
	
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void addToErrorMessage(String errorMessage) {
		this.errorMessage = this.errorMessage + " " + errorMessage;
		this.errorCount += 1;
	}
	
	public void parseInput(String input) {
		
		// Tokenize the input string coming from the clientSocket
		String[] inputs = input.split("\\:");
		
		// Check to see if the length of the tokenized array has the correct # of arguments
		if (inputs.length != 5) {
			addToErrorMessage("Invalid number of arguments passed.");
		}
		
		// If it has the correct #, parse through the inputs
		else {
			
			//hikeID handling
			try {
				int hikeID = Integer.parseInt(inputs[0]);
				
				// Validate hikeID before setting it to member variable
				if(hikeID < 0 || hikeID > 2) {
					addToErrorMessage("Hike ID must be between 0 and 2.");
				}
				else {
					setHikeID(hikeID);
				}
			}
			// Catch exception if hikeID is not an integer value that can be parsed
			catch (NumberFormatException e) {
				addToErrorMessage("Invalid input for hike ID.");
			}
			
			//beginYear handling
			try {
				int beginYear = Integer.parseInt(inputs[1]);
				
				// Validate year to be between 2020-2025
				if(beginYear < 2020 || beginYear > 2025) {
					addToErrorMessage("Year must be between 2020 and 2025.");
				}
				else {
					setBeginYear(beginYear);
				}
			}
			catch (NumberFormatException e) {
				addToErrorMessage("Invalid input for year.");
			}
			
			//beginMonth handling
			try {
				int beginMonth = Integer.parseInt(inputs[2]);
				
				// Validate month to be between 6 and 9
				if(beginMonth < 6 || beginMonth > 9) {
					addToErrorMessage("Month must be between 6 and 9.");
				}
				
				else {
					setBeginMonth(beginMonth);
				}
			}
			catch (NumberFormatException e) {
				addToErrorMessage("Invalid input for Month.");
			}
			
			//beginDay handling
			try {
				int beginDay = Integer.parseInt(inputs[3]);
				
				// Create BookingDay object to check for good month/day combos, the -1 is because bookingDay takes months from 0-11
				BookingDay bookingDay = new BookingDay(this.beginYear, this.beginMonth - 1, beginDay);
				
				// Validate day to be between 1 and 31
				if(beginDay < 1 || beginDay > 31) {
					addToErrorMessage("Day must be between 1 and 31.");
				}
				else {
					// Validate the day and month combo
					boolean validateDay = bookingDay.badDay();
					if(validateDay) {
						addToErrorMessage("Invalid day and month combination.");
					}
					
					else {
						setBeginDay(beginDay);
					}
				}				
			}
			catch (NumberFormatException e) {
				addToErrorMessage("Invalid input for Day.");
			}
			
			//duration handling, no need to check if combination is okay
			try {
				int duration = Integer.parseInt(inputs[4]);
				
				// Validate duration to be between 2 and 7
				if(duration < 2 || duration > 7) {
					addToErrorMessage("Duration must be between 2 and 7 days.");
				}
				else {
					setDuration(duration);
				}
			}
			catch (NumberFormatException e) {
				addToErrorMessage("Invalid input for duration.");
			}
			
		}
		
	}
	
	public double calculateCost() {
		// All error handling dealt with during the parsing method
		double cost = 0.0;
		int hikePrice = 0;
		if (this.hikeID == 0) {
			hikePrice = 40;
		}
		if (this.hikeID == 1) {
			hikePrice = 35;
		}
		if (this.hikeID == 2) {
			hikePrice = 45;
		}
		
		cost = hikePrice * this.duration;
		return cost;
	}
	
	// Reset all thread values when it returns a correct pricing or an incorrect pricing
	public void resetValues() {
		this.hikeID = -1;
		this.beginYear = -1;
		this.beginMonth = -1;
		this.beginDay = -1;
		this.duration = -1;
		this.errorMessage = "";
		this.errorCount = 0;
	}
	
	public void run() {
		BufferedReader in = null;
        PrintWriter out = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String outputLine = null;
            while (!socket.isClosed()) {
            	
                outputLine = in.readLine();
                if (outputLine == null) {
                    break;
                }
                
                // Exit the thread
                if (outputLine.equalsIgnoreCase("exit")) {
                    break;
                } 
                
                else {
                    // call parse method
                	parseInput(outputLine);
                	
                	// if there are any errors in parsing the info, return negative cost + error messages that occurred
                	if(this.errorCount != 0) {
                		out.println("-0.01" + ":" + this.errorMessage);
                		
                		// reset values
                		resetValues();
                	}
                	else {
                		// call calculate cost method
                		double cost = calculateCost();
                		out.println(cost + ":Quoted Rate");
                		
                		// reset values
                		resetValues();
                	}
                }
                
                // sleeps for a second after every call to allow other threads to run
                try {
                	Thread.sleep(delay);
                }
                catch (InterruptedException ie) {
                	out.println("sleep interrupted.");
                	break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BhcThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(BhcThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}
