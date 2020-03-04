package dea_s_hw6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Create a special JPanel for the input Dates

public class DatePanel extends JPanel {
	private JLabel dayLabel = new JLabel("Day:");
	private JLabel monthLabel = new JLabel("Month:");
	private JLabel yearLabel = new JLabel("Year:");
	
	// Set text fields with width of 10
	private JTextField dayField = new JTextField("DD (1-31)", 10);
	private JTextField monthField = new JTextField("MM (1-12)", 10);
	private JTextField yearField = new JTextField("YYYY (2020-2025)", 10);
	
	JButton checkDates = new JButton("Check Dates");
	
	private JLabel validDate = new JLabel("");
	
	private DateListener dateListener;
	
	
	// Panel constructor
	public DatePanel() {
		
		setBorder(BorderFactory.createTitledBorder("Desired Date of Trip"));
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Initialized grid bag constraints
		gbc.gridx = 0;
		gbc.gridy = 0;                                                
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE; // will NOT have what is inside the panel fill completely
			
		Dimension dim = getPreferredSize();
		dim.width = 100;
		dim.height = 70;
		
		add(monthLabel, gbc);
		
		// Move over by 1 in the x-dimension
		gbc.gridx = 1;
		add(monthField, gbc);

		gbc.gridx = 2;
		add(dayLabel, gbc);
		
		gbc.gridx = 3;
		add(dayField, gbc);
		
		gbc.gridx = 4;
		add(yearLabel, gbc);
		
		gbc.gridx = 5;
		add(yearField,gbc);
		
		// Move down to the next y level
		gbc.gridx = 6;
		add(checkDates, gbc);
		
		gbc.gridx = 3;
		gbc.gridy = 1;
		add(validDate, gbc);
		
		// Anonymous class
		checkDates.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String dayFieldIn = dayField.getText();
				String monthFieldIn = monthField.getText();
				String yearFieldIn = yearField.getText();
				
				int day;
				int month;
				int year;
				
				// Check to see if day field can be parsed into an int, if it cannot, return with an invalid day value to prompt JOptionPanel in main GUI
				try{
					day = Integer.parseInt(dayFieldIn);
				}
				catch (Exception ex){
					day = -1;
				}
				try {
					month = Integer.parseInt(monthFieldIn);
				}
				catch (Exception ex) {
					month = -1;
				}
				try {
					year = Integer.parseInt(yearFieldIn);
				}
				catch (Exception ex) {
					year = -1;
				}
				
				// Connect this button event to the main HikeBookingGUI frame
				DateEvent event = new DateEvent(this, day, month, year);
				if(dateListener != null) {
					dateListener.dateEventOccurred(event);
				}
			}
		});
	}
	
	public void setDateListener (DateListener listener) {
		this.dateListener = listener;
	}
	
	// Method to check if the day value is valid & return boolean to main GUI program
	public boolean checkDay(int day) {
		if (day > 31 || day < 0) {
			return false;
		}
		else {
			return true;
		}
		
	}
	public boolean checkMonth(int month) {
		if (month > 12 || month < 1) {
			return false;
		}
		else {
			return true;
		}
		
	}
	public boolean checkYear(int year) {
		if (year > 2030 || year < 2020) {
			return false;
		}
		else {
			return true;
		}
		
	}
	public void setValidDate() {
		validDate.setText("Valid Date.");
	}
	
	public void clearValidDate()
	{
		validDate.setText("");
	}
}
