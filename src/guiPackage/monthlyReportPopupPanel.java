package guiPackage;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import databasePackage.DatabaseConnector;
import databasePackage.MonthlyReportData;

public class monthlyReportPopupPanel extends JPanel {

	private DatabaseConnector dbConn;
	
	public monthlyReportPopupPanel(String month, String year, DatabaseConnector dbC) {
		this.dbConn = dbC;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.add(new JLabel("Monthly sales report for " + month));
		this.add(new JLabel("----------------------------------------------------------------"));
						
		ArrayList<MonthlyReportData> monthlyReportDataSet;
		try {
			monthlyReportDataSet = getMonthlyDataFromDataBase(month,year);
			int monthlyTotal = 0;
			for(MonthlyReportData monthlyReportDataIterator : monthlyReportDataSet)
			{
				monthlyReportDataIterator.calculateItemProfit();	
				monthlyTotal += monthlyReportDataIterator.getItemProfit();
				this.add(new JLabel("Product Name: " + monthlyReportDataIterator.getProductName()));
				this.add(new JLabel("Buy Price: $" + monthlyReportDataIterator.getBuyPrice()));
				this.add(new JLabel("Sell Price: $" + monthlyReportDataIterator.getSellPrice()));
				this.add(new JLabel("Quantity: " + monthlyReportDataIterator.getQuantity()));
				this.add(new JLabel("Item Profit: $" + monthlyReportDataIterator.getItemProfit()));	
				this.add(new JLabel("----------------------------------------------------------------"));
			}
			this.add(new JLabel("Monthly profit: $" + monthlyTotal));	
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}

	public ArrayList<MonthlyReportData> getMonthlyDataFromDataBase(String month, String year) throws SQLException{
		
		
		//needed format - 2016-09-01
		String startDate = year + "-01-01";
		String endDate = year + "-01-01";
		switch (month) {
	        case "January":  
	        		startDate = year + "-01-01";
	    			endDate = year + "-01-31";
	                break;
	        case "February":  
		        	startDate = year + "-02-01";
					endDate = year + "-02-28";
			        break;
	        case "March":  
		        	startDate = year + "-03-01";
					endDate = year + "-03-31";
			        break;
	        case "April":  
		        	startDate = year + "-04-01";
					endDate = year + "-04-30";
			        break;
	        case "May":  
	        		startDate = year + "-05-01";
					endDate = year + "-05-31";
			        break;
	        case "June":  
	        		startDate = year + "-06-01";
        			endDate = year + "-06-30";
        			break;
	        case "July":  
	        		startDate = year + "-07-01";
					endDate = year + "-07-31";
			        break;
	        case "August": 
	        		startDate = year + "-08-01";
					endDate = year + "-08-31";
			        break;
	        case "September":  
	        		startDate = year + "-09-01";
					endDate = year + "-09-30";
			        break;
	        case "October": 
	        		startDate = year + "-10-01";
					endDate = year + "-10-31";
			        break;
	        case "November": 
	        		startDate = year + "-11-01";
					endDate = year + "-11-30";
			        break;
	        case "December": 
	        		startDate = year + "-12-01";
					endDate = year + "-12-31";
			        break;
		}		
		
		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
		database = dbConn.GenerateMonthlySalesReport("SELECT I.productName, I.buyPrice, I.sellPrice, S.Qty FROM dp2_mysql.Sales S INNER JOIN dp2_mysql.Inventory I ON S.InvID = I.InvID WHERE DATE(S.Date) BETWEEN '"+startDate+"' AND '"+endDate+"'");
		
		boolean noMatchFound = true;		
		ArrayList<MonthlyReportData> monthlyReportDataSet = new ArrayList<MonthlyReportData>();
		for(ArrayList<String> databaseRecord : database)
		{
			if(monthlyReportDataSet.size() == 0)
			{
				MonthlyReportData monthlyReportData = new MonthlyReportData();
				monthlyReportData.setProductName(databaseRecord.get(0));
				monthlyReportData.setBuyPrice(Integer.parseInt(databaseRecord.get(1)));
				monthlyReportData.setSellPrice(Integer.parseInt(databaseRecord.get(2)));
				monthlyReportData.setQuantity(Integer.parseInt(databaseRecord.get(3)));
				monthlyReportDataSet.add(monthlyReportData);
			}
			else
			{
				for(MonthlyReportData monthlyReportDataIterator : monthlyReportDataSet)
				{
					if(monthlyReportDataIterator.getProductName().equals(databaseRecord.get(0)))
					{
						//There is a matching Product Name
						//We need to add the Quantity Total
						monthlyReportDataIterator.setAdditionalQuantity(Integer.parseInt(databaseRecord.get(3)));
						noMatchFound = false;
					}
				}
				if(noMatchFound)
				{
					MonthlyReportData monthlyReportData = new MonthlyReportData();
					monthlyReportData.setProductName(databaseRecord.get(0));
					monthlyReportData.setBuyPrice(Integer.parseInt(databaseRecord.get(1)));
					monthlyReportData.setSellPrice(Integer.parseInt(databaseRecord.get(2)));
					monthlyReportData.setQuantity(Integer.parseInt(databaseRecord.get(3)));
					monthlyReportDataSet.add(monthlyReportData);
				}
			}
		}
		return monthlyReportDataSet;
	}	
}
