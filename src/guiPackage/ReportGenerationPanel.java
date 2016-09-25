package guiPackage;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Console;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;

import databasePackage.DatabaseConnector;

import javax.swing.JButton;
import javax.swing.Box;

public class ReportGenerationPanel extends JPanel {

	private DatabaseConnector dbConn;
	private UserInputPanel userInputPanel;
	private JComboBox reportSelectionComboBox;
	private JComboBox productDemandSelectionComboBox;
	private JComboBox timePeriodComboBox;
	private JSpinner monthDateField;
	private JPanel reportTypeSelectionPanel;
	private ArrayList<JComponent> monthlyReportComponenets = new ArrayList<JComponent>();
	private ArrayList<JComponent> productDemandComponenets = new ArrayList<JComponent>();
	
	public ReportGenerationPanel(UserInputPanel uIP, DatabaseConnector dbC) {
		this.userInputPanel = uIP;
		this.dbConn = dbC;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		reportTypeSelectionPanel = new JPanel();
		
		reportTypeSelectionPanelInitialisation();		
		monthlyReportPanelInitialisation();
		productDemandPanelInitialisation();
		
		this.add(reportTypeSelectionPanel);
		
		this.setVisible(false);
	}
	
	private void reportTypeSelectionPanelInitialisation(){
		JPanel reportSelectionPanel = new JPanel();
		reportTypeSelectionPanel.add(reportSelectionPanel);
		reportSelectionPanel.setLayout(new BoxLayout(reportSelectionPanel, BoxLayout.Y_AXIS));
		
		JLabel reportSelectionLabel = new JLabel("Select Report Type");
		reportSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		reportSelectionPanel.add(reportSelectionLabel);
		
		reportSelectionComboBox = new JComboBox();
		ArrayList<String> reportTypeOptions = new ArrayList<String>();
		reportTypeOptions.add("Monthly Sales Report");
		reportTypeOptions.add("Product Demand");
		reportSelectionComboBox.setModel(new DefaultComboBoxModel(reportTypeOptions.toArray()));
		reportSelectionComboBox.setSelectedIndex(0);
		reportSelectionPanel.add(reportSelectionComboBox);
		
		JPanel selectReportButtonPanel = new JPanel();
		reportTypeSelectionPanel.add(selectReportButtonPanel);
		selectReportButtonPanel.setLayout(new BoxLayout(selectReportButtonPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(15);
		selectReportButtonPanel.add(verticalStrut);
		
		JButton selectReportButton = new JButton("Select Report Type");
		selectReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data1 = (String)reportSelectionComboBox.getSelectedItem();
				if(data1 == "Monthly Sales Report"){
					for(int i = 0; i < monthlyReportComponenets.size(); i++){
						monthlyReportComponenets.get(i).setVisible(true);
					}			
					for(int i = 0; i < productDemandComponenets.size(); i++){
						productDemandComponenets.get(i).setVisible(false);
					}	
				}else if(data1 == "Product Demand"){
					for(int i = 0; i < monthlyReportComponenets.size(); i++)
					{
						monthlyReportComponenets.get(i).setVisible(false);
					}
					for(int i = 0; i < productDemandComponenets.size(); i++){
						productDemandComponenets.get(i).setVisible(true);
					}	
				}
			}
		});
		selectReportButtonPanel.add(selectReportButton);
	}
	
	private void monthlyReportPanelInitialisation(){
			
		JPanel datePanel = new JPanel();
		reportTypeSelectionPanel.add(datePanel);
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		
		JLabel monthDateLabel = new JLabel("Date");
		monthDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		datePanel.add(monthDateLabel);
		
		monthDateField = new JSpinner();
		monthDateField.setModel(new SpinnerDateModel(new Date(1472652000000L), null, null, Calendar.DAY_OF_YEAR));
		datePanel.add(monthDateField);
		
		monthlyReportComponenets.add(datePanel);
		
		JPanel generateMonthlyReportButtonPanel = new JPanel();
		reportTypeSelectionPanel.add(generateMonthlyReportButtonPanel);
		generateMonthlyReportButtonPanel.setLayout(new BoxLayout(generateMonthlyReportButtonPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(15);
		generateMonthlyReportButtonPanel.add(verticalStrut);
		
		JButton generateMonthlyReportButton = new JButton("Generate Report");
		generateMonthlyReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String popUpTitle = "Monthly Sales Report";
				Date monthData = (Date)monthDateField.getValue();
				String[] months = new String[] {"January","February","March","April","May","June",
						"July","August","September","October","November","December"};
				String month = months[monthData.getMonth()];
				JOptionPane.showMessageDialog(null,new monthlyReportPopupPanel(month,dbConn),popUpTitle,JOptionPane.PLAIN_MESSAGE);
			}
		});
		generateMonthlyReportButtonPanel.add(generateMonthlyReportButton);
		
		monthlyReportComponenets.add(generateMonthlyReportButtonPanel);
	}
	
	private void productDemandPanelInitialisation(){
				
		JPanel productNameSelectionPanel = new JPanel();
		reportTypeSelectionPanel.add(productNameSelectionPanel);
		productNameSelectionPanel.setLayout(new BoxLayout(productNameSelectionPanel, BoxLayout.Y_AXIS));
		
		JLabel reportSelectionLabel = new JLabel("Product Name");
		reportSelectionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		productNameSelectionPanel.add(reportSelectionLabel);
		
		productDemandSelectionComboBox = new JComboBox();
		productDemandSelectionComboBox.setModel(new DefaultComboBoxModel(userInputPanel.getProductNames().toArray()));
		productDemandSelectionComboBox.setSelectedIndex(0);
		productNameSelectionPanel.add(productDemandSelectionComboBox);
		
		JPanel timePeriodSelectionPanel = new JPanel();
		reportTypeSelectionPanel.add(timePeriodSelectionPanel);
		timePeriodSelectionPanel.setLayout(new BoxLayout(timePeriodSelectionPanel, BoxLayout.Y_AXIS));
		
		JLabel timePeriodionLabel = new JLabel("Time Period");
		timePeriodionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		timePeriodSelectionPanel.add(timePeriodionLabel);
		
		timePeriodComboBox = new JComboBox();
		ArrayList<String> timePeriod = new ArrayList<String>();
		timePeriod.add("Quarterly");
		timePeriod.add("Yearly");
		timePeriodComboBox.setModel(new DefaultComboBoxModel(timePeriod.toArray()));
		timePeriodComboBox.setSelectedIndex(0);
		timePeriodSelectionPanel.add(timePeriodComboBox);
		
		JPanel generateProductDemandButtonPanel = new JPanel();
		reportTypeSelectionPanel.add(generateProductDemandButtonPanel);
		generateProductDemandButtonPanel.setLayout(new BoxLayout(generateProductDemandButtonPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(15);
		generateProductDemandButtonPanel.add(verticalStrut);
		
		JButton generateProductDemandButton = new JButton("Generate Report");
		generateProductDemandButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
			    if((String)productDemandSelectionComboBox.getSelectedItem() == "Select Product"){
					incorrectInformationEntered("Please select a valid Product");
					return;
				}
			    ArrayList<String> data = new ArrayList<String>();
			    data.add((String)productDemandSelectionComboBox.getSelectedItem());
			    data.add((String)timePeriodComboBox.getSelectedItem());
				JOptionPane.showMessageDialog(null,new productDemandPopupPanel(data,dbConn),"Product Demand Report",JOptionPane.PLAIN_MESSAGE);
			}
		});
		generateProductDemandButtonPanel.add(generateProductDemandButton);
		
		productNameSelectionPanel.setVisible(false);
		timePeriodSelectionPanel.setVisible(false);
		generateProductDemandButtonPanel.setVisible(false);
		productDemandComponenets.add(productNameSelectionPanel);
		productDemandComponenets.add(timePeriodSelectionPanel);
		productDemandComponenets.add(generateProductDemandButtonPanel);
	}
	
	private void incorrectInformationEntered(String fieldInformation)
	{
		JOptionPane.showMessageDialog(this, fieldInformation);
	}
}
