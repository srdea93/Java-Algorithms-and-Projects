package dea_s_hw6;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.io.*;
import java.net.*;

/*
 * This program creates a GUI using Swing. The contents of this GUI are split into 3 portions,
 * the main panel which contains the hike JList, the length JLists, the select hike and
 * select length buttons and two further subdivided panels of DatePanel and PricePanel.
 * The DatePanel handles all formatting and input regarding the date selection while the PricePanel
 * handles calculating the price. Both separate panels have Event and Listener classes implemented
 * to link any actions that occur within these panels (the Check Dates button and the Calculate button)
 * to the main panel.
 * The GUI allows a user to check if certain dates are valid given a format of day/month/year and is prompted
 * to fix various inputs if they are incorrect. It also allows a user to select a hike and a length of their hike
 * to calculate the final cost.
 * @author Steven Dea
*/
public class HikeBookingGUI extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	// Create JLists of the hikes to be selected and the durations to be selected
	String[] hikeList = {
		"Gardiner Lake",
		"The Hellroaring Plateau",
		"The Beaten Path"
	};
	private JList hikes = new JList(hikeList);	
	
	String [] emptyList = {""};
	String [] GLDurationList = {"3", "5"};
	String [] HPDurationList = {"2", "3", "4"};
	String [] BPDurationList = {"5", "7"};
	
	private JList lengthList = new JList(emptyList);
	
	private int hikeIndex;
	private int lengthIndex;
	
	private PricePanel pricePanel = new PricePanel();
	private DatePanel datePanel = new DatePanel();
	
	//HW 6 additions for sending data to server
	int beginYear;
	int beginMonth;
	int beginDay;
	int dayLength;
	String errorMessage;
	
	String toServer;

	
	
	

	/**
	 * Launch the application.
	 * Calls the constructor for the GUI
	 */
	public static void main(String[] args) throws IOException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HikeBookingGUI frame = new HikeBookingGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}		
			}
		});
		
	}

	/**
	 * Create the frame.
	 * Place labels, lists, etc. in here to be shown
	 */
	public HikeBookingGUI() {
		super("BHC Booking GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Initialized grid bag constraints
		gbc.gridx = 0;
		gbc.gridy = 0;                                                
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		
		// Y = 0
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridwidth = 2;
		gbc.weighty = 0.1;
		add(datePanel, gbc);
		
		// Link action button from DatePanel to the HikeBookingGUI to interact with the main contentPane
		datePanel.setDateListener(new DateListener() {
			public void dateEventOccurred(DateEvent event) {
				// Reset valid date label on button click until date has been validated
				datePanel.clearValidDate();
				int day = event.getDay();
				int month = event.getMonth();
				int year = event.getYear();
				
				// Create a booking day object
				BookingDay bookDay = new BookingDay(year, month, day);
				
				// Check for validity of integer input
				boolean dayValid = datePanel.checkDay(day);
				boolean monthValid = datePanel.checkMonth(month);
				boolean yearValid = datePanel.checkYear(year);
				boolean monthDayValid = bookDay.badDay();
				
				if (dayValid == false) {
					JOptionPane.showMessageDialog(datePanel, "Day value incorrect.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				if (monthValid == false) {
					JOptionPane.showMessageDialog(datePanel, "Month value incorrect.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				if (yearValid == false) {
					JOptionPane.showMessageDialog(datePanel, "Year value incorrect.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				if (monthDayValid == true) {
					JOptionPane.showMessageDialog(datePanel, "Day value incorrect for specific month.", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
				if ((dayValid == true) && (monthValid == true) && (yearValid == true) && (monthDayValid == false)){
					datePanel.setValidDate();
					beginYear = year;
					beginMonth = month;
					beginDay = day;
				}
			}
		});		
		
		// Y = 1
		hikes.setBorder(BorderFactory.createTitledBorder("Hikes"));
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.weighty = 1;
		add(hikes, gbc);
		
		// Depending on hike selection set the next column to be whichever trip length list
		lengthList.setBorder(BorderFactory.createTitledBorder("Length of Trip (in days)"));
		
		gbc.gridx = 1;
		
		add (lengthList, gbc);
		
		// Add listener for JList selection on the hikes JList
		hikes.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					JList list = (JList)e.getSource();
					hikeIndex = list.getSelectedIndex();
					
					// Depending on the selected hike, replace the lengthList data with the correct hike length options
					if(hikeIndex == 0) {
						lengthList.setListData(GLDurationList);
					}
					else if (hikeIndex == 1) {;
						lengthList.setListData(HPDurationList);
					}
					else if (hikeIndex == 2) {
						lengthList.setListData(BPDurationList);	
					}
				}
			}
		});
		
		// Add listener for JList selection on the lengthList JList
		lengthList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					JList list = (JList)e.getSource();
					lengthIndex = list.getSelectedIndex();
					System.out.println(list.getSelectedValue());
				}
			}
		});
		
		
		gbc.gridx = 1;
		gbc.gridy = 1;		
		
		
		// Y = 2	
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.weighty = 0.1;
		gbc.gridwidth = 2;
		add(pricePanel, gbc);
		
		// Add listener for calculate price button
		pricePanel.setPriceListener(new PriceListener(){
			public void priceEventOccurred(PriceEvent event) {
				int hikeCost = 0;
				int days = 0;
				if (hikeIndex == 0) {
					hikeCost = 40;
					if (lengthIndex == 0) {
						days = 3;
					}
					else if (lengthIndex == 1){
						days = 5;
					}
				}
				else if (hikeIndex == 1) {
					hikeCost = 35;
					if (lengthIndex == 0) {
						days = 2;
					}
					else if (lengthIndex == 1) {
						days = 3;
					}
					else if (lengthIndex == 2) {
						days = 4;
					}
				}
				else if (hikeIndex == 2) {
					hikeCost = 45;
					if (lengthIndex == 0) {
						days = 5;
					}
					else if (lengthIndex == 1) {
						days = 7;
					}
				}
				else {
					hikeCost = 0;
					days = 0;
				}
				
				dayLength = days;
				// Generate the String that will be sent to the server to be queried
				toServer = hikeIndex + ":" + beginYear + ":" + beginMonth + ":" + beginDay + ":" + dayLength;
				
				String totalPriceStr = "";
				try {
					totalPriceStr = sendToServer(toServer);
				}
				catch (IOException e) {
					System.err.println("I/O Error");
				}
				
				// Inform user why price is -0.01
				if(totalPriceStr.equals("-0.01")) {
					pricePanel.append("$" + totalPriceStr + " " + errorMessage);
				}
				else {
					pricePanel.append("$" + totalPriceStr);
				}
				
			}
		});
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String name = e.getActionCommand();

	}
	
	public String sendToServer(String toServer) throws IOException {
		String output = "";
		
		// Networking HW:6
		String host = "web6.jhuep.com";
		int port = 20025;
		Socket socket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		try {
			System.out.println("Connecting to Socket: " + host + " " + port);
			socket = new Socket(host, port);
			System.out.println("Making PrintWriter");
			out = new PrintWriter(socket.getOutputStream(), true);
			System.out.println("Making Buffered Reader");
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch (UnknownHostException e){
			System.err.println("Host: " + host + " unknown.");
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("I/O Error");
			System.exit(1);
		}
		
		// Do I/O stuff with streams
		// Send toServer string to server using out
		System.out.println(toServer);
		out.println(toServer);
		
		// Read output back from server using in
		String priceQuote = in.readLine();
		System.out.println(priceQuote);
		String[] outputArr = priceQuote.split(":");
		
		errorMessage = outputArr[1];
		
		output = outputArr[0];
			
						
		return output;
	}
}
