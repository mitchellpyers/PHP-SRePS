package guiPackage;

import javax.swing.JPanel;

import databasePackage.DatabaseConnector;
import databasePackage.MonthlyReportData;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

public class productDemandPopupPanel extends JPanel {

	private DatabaseConnector dbConn;
	
	public productDemandPopupPanel(ArrayList<String> data, DatabaseConnector dbC) {
		this.dbConn = dbC;
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		Calendar now = Calendar.getInstance();
		int currentMonthIndex = now.get((Calendar.MONTH)) ;
		int year = now.get(Calendar.YEAR);
		int previousYear = year - 1;
		String[] months = new String[] {"January","February","March","April","May","June",
				"July","August","September","October","November","December"};
		
		this.add(new JLabel("Predicted demand for " + data.get(0) + " for month " + months[(currentMonthIndex + 1) & 11] + " " + Integer.toString(year)));	
		this.add(new JLabel("------------------------------------------------------------------------------"));
		
		int n = 0;
		if(data.get(1) == "Quarterly"){
			n = 3;
		}else{
			n = 12;
		}
		
		//ArrayList<Integer> monthlySales = new ArrayList<Integer>();		
		int[] monthlySales = new int[n];
		try {
			for(int i = (n-1); i > -1; i--){
				int monthIndex = 0;
				if((currentMonthIndex - i) % 11 < 0 ){
					monthIndex = ((currentMonthIndex - i) % 11) + 12;
					int temp = getMonthlyProductSalesFromDataBase(monthIndex+1,Integer.toString(previousYear),data.get(0));
					monthlySales[i] = temp;
					JLabel monthlyLabel = new JLabel("        - " 
							+ months[monthIndex] + " " 
							+ Integer.toString(previousYear) + " : " + temp + " unit(s)");
						this.add(monthlyLabel);
				}else{
					monthIndex = (currentMonthIndex - i) % 11;
					int temp = getMonthlyProductSalesFromDataBase(monthIndex+1,Integer.toString(year),data.get(0));
					monthlySales[i] = temp;
					JLabel monthlyLabel = new JLabel("        - " 
							+ months[monthIndex] + " " 
							+ Integer.toString(year) + " : "  + temp + " unit(s)");
						this.add(monthlyLabel);
				}
				
			}
			for(int i = 0; i < monthlySales.length / 2; i++)
			{
			    int temp = monthlySales[i];
			    monthlySales[i] = monthlySales[monthlySales.length - i - 1];
			    monthlySales[monthlySales.length - i - 1] = temp;
			}
			this.add(new JLabel("Prediction for next month: "  + predictNextValue(monthlySales) + " unit(s)"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int getMonthlyProductSalesFromDataBase(int month, String year, String productName) throws SQLException{
		
		System.out.println(year);
		//needed format - 2016-09-01
		String startDate = year;
		String endDate = year;
		switch (month) {
	        case 1:  
	        		startDate = year + "-01-01";
	    			endDate = year + "-01-31";
	                break;
	        case 2:  
		        	startDate = year + "-02-01";
					endDate = year + "-02-28";
			        break;
	        case 3:  
		        	startDate = year + "-03-01";
					endDate = year + "-03-31";
			        break;
	        case 4:  
		        	startDate = year + "-04-01";
					endDate = year + "-04-30";
			        break;
	        case 5:  
	        		startDate = year + "-05-01";
					endDate = year + "-05-31";
			        break;
	        case 6:  
	        		startDate = year + "-06-01";
        			endDate = year + "-06-30";
        			break;
	        case 7:  
	        		startDate = year + "-07-01";
					endDate = year + "-07-31";
			        break;
	        case 8: 
	        		startDate = year + "-08-01";
					endDate = year + "-08-31";
			        break;
	        case 9:  
	        		startDate = year + "-09-01";
					endDate = year + "-09-30";
			        break;
	        case 10: 
	        		startDate = year + "-10-01";
					endDate = year + "-10-31";
			        break;
	        case 11: 
	        		startDate = year + "-11-01";
					endDate = year + "-11-30";
			        break;
	        case 12: 
	        		startDate = year + "-12-01";
					endDate = year + "-12-31";
			        break;
		}		
		
		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
		database = dbConn.GenerateMonthlySalesReport("SELECT I.productName, I.buyPrice, I.sellPrice, S.Qty FROM dp2_mysql.Sales S INNER JOIN dp2_mysql.Inventory I ON S.InvID = I.InvID WHERE DATE(S.Date) BETWEEN '"+startDate+"' AND '"+endDate+"'");
		
		boolean noMatchFound = true;		
		ArrayList<MonthlyReportData> monthlyReportDataSet = new ArrayList<MonthlyReportData>();
		int productSales = 0;
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
		
		for(MonthlyReportData monthlyReportDataIterator : monthlyReportDataSet)
		{
			if(monthlyReportDataIterator.getProductName().equals(productName)){
				productSales += monthlyReportDataIterator.getQuantity();
			}
		}
		return productSales;
	}	
	
	private int predictNextValue(int[] data){
		int sumX = 0;
		int n = data.length;
		for(int i = 1; i < n + 1; i++){
			sumX += i;
		}
		int sumY = 0;
		for(int i = 0; i < n; i++){
			sumY += data[i];
		}
		int sumXY = 0;
		for(int i = 0; i < n; i++){
			sumXY += data[i] * (i+1);
		}
		int sumXX = 0;
		for(int i = 1; i < n + 1; i++){
			sumXX += i*i;
		}
		int sumX_2 = sumX*sumX;
		
		System.out.println(sumX);
		System.out.println(sumY);
		System.out.println(sumXY);
		System.out.println(sumXX);
		System.out.println(sumX_2);
		
		double a = ((n*sumXY) - (sumX*sumY));
		a = a / ((n*sumXX)-(sumX_2));
		
		double b = ((sumY) - (a*sumX)) / n;
		return (int) Math.ceil((a*(n+1)) + b);			
	}

}
