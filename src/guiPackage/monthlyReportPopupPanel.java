package guiPackage;

import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import databasePackage.DatabaseConnector;

public class monthlyReportPopupPanel extends JPanel {

	private DatabaseConnector dbConn;
	
	public monthlyReportPopupPanel(String month, DatabaseConnector dbC) {
		this.dbConn = dbC;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new JLabel("Monthly sales report for " + month));
		this.add(new JLabel("----------------------------------------------------------------"));
		
		this.add(new JLabel("        " + "Product 1 " + "X units " + "Y Price " + "Z Total"));
		this.add(new JLabel("        " + "Product 2 " + "X units " + "Y Price " + "Z Total"));
		this.add(new JLabel("        " + "Product 3 " + "X units " + "Y Price " + "Z Total"));
		this.add(new JLabel("        " + "Product 4 " + "X units " + "Y Price " + "Z Total"));
		
		this.add(new JLabel("        " + "Total Sales " + "Z Total"));
	}

}
