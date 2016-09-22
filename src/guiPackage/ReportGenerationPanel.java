package guiPackage;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JTabbedPane;
import javax.swing.SpinnerDateModel;
import javax.swing.JButton;
import javax.swing.Box;

public class ReportGenerationPanel extends JPanel {

	private JComboBox reportSelectionComboBox;
	private JSpinner startDateField;
	private JSpinner endDateField;
	private JPanel reportTypeSelectionPanel;
	private JPanel dateRangeReportPanel;
	
	public ReportGenerationPanel() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		reportTypeSelectionPanel = new JPanel();
		dateRangeReportPanel = new JPanel();
		dateRangeReportPanel.setVisible(false);
		
		reportTypeSelectionPanelInitialisation();		
		dateRangeReportPanelInitialisation();
		
		this.add(reportTypeSelectionPanel);
		this.add(dateRangeReportPanel);
		
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
		reportTypeOptions.add("Select Report Type");
		reportTypeOptions.add("Date Range");
		reportSelectionComboBox.setModel(new DefaultComboBoxModel(reportTypeOptions.toArray()));
		reportSelectionComboBox.setSelectedIndex(0);
		reportSelectionPanel.add(reportSelectionComboBox);
		
		JPanel selectReportButtonPanel = new JPanel();
		reportTypeSelectionPanel.add(selectReportButtonPanel);
		selectReportButtonPanel.setLayout(new BoxLayout(selectReportButtonPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(10);
		selectReportButtonPanel.add(verticalStrut);
		
		JButton selectReportButton = new JButton("Select Report Type");
		selectReportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data1 = (String)reportSelectionComboBox.getSelectedItem();
				if(data1 == "Select Report Type"){
					dateRangeReportPanel.setVisible(false);
				}else if(data1 == "Date Range"){
					dateRangeReportPanel.setVisible(true);
				}
			}
		});
		selectReportButtonPanel.add(selectReportButton);
	}
	
	private void dateRangeReportPanelInitialisation(){
		JPanel startDatePanel = new JPanel();
		dateRangeReportPanel.add(startDatePanel);
		startDatePanel.setLayout(new BoxLayout(startDatePanel, BoxLayout.Y_AXIS));
		
		JLabel startDateLabel = new JLabel("Start Date");
		startDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		startDatePanel.add(startDateLabel);
		
		startDateField = new JSpinner();
		startDateField.setModel(new SpinnerDateModel(new Date(1472652000000L), null, null, Calendar.DAY_OF_YEAR));
		startDatePanel.add(startDateField);
		
		JPanel endDatePanel = new JPanel();
		dateRangeReportPanel.add(endDatePanel);
		endDatePanel.setLayout(new BoxLayout(endDatePanel, BoxLayout.Y_AXIS));
		
		JLabel endDateLabel = new JLabel("End Date");
		endDateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		endDatePanel.add(endDateLabel);
		
		endDateField = new JSpinner();
		endDateField.setModel(new SpinnerDateModel(new Date(1472652000000L), null, null, Calendar.DAY_OF_YEAR));
		endDatePanel.add(endDateField);
		
		JPanel generateReportButtonPanel = new JPanel();
		dateRangeReportPanel.add(generateReportButtonPanel);
		generateReportButtonPanel.setLayout(new BoxLayout(generateReportButtonPanel, BoxLayout.Y_AXIS));
		
		JButton generateReportButton = new JButton("Generate Report");
		generateReportButtonPanel.add(generateReportButton);
	}
}
