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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

// Module for the calculating of price text panel part of the BHC GUI

public class PricePanel extends JPanel {
	private JLabel priceLabel = new JLabel("Price: ");
	private PriceListener priceListener;
	
	public PricePanel() {
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Initialized grid bag constraints
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.NONE;
		
		JButton calculate = new JButton("Calculate");
		add(calculate, gbc);
		
		calculate.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Connect this button event to the main HikeBookingGUI frame
				PriceEvent event = new PriceEvent(this);
				if(priceListener != null) {
					priceListener.priceEventOccurred(event);
				}
			}
		});
		
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		add(priceLabel, gbc);
		
		Dimension dim = getPreferredSize();
		dim.width = 100;
		dim.height = 35;
		
		setBorder(BorderFactory.createTitledBorder("Pricing"));
	
		
		
	}
	
	public void setPriceListener (PriceListener listener) {
		this.priceListener = listener;
	}
	
	public void append(String price) {
		priceLabel.setText("Price: " + price);
	}
}
