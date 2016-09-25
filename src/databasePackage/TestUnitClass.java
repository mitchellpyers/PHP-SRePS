package databasePackage;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;


public class TestUnitClass {

	@Test
	public void test() throws SQLException 
	{
		DatabaseConnector dbConn = new DatabaseConnector();
		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
		database = dbConn.GenerateMonthlySalesReport("SELECT I.productName, I.buyPrice, I.sellPrice, S.Qty FROM dp2_mysql.Sales S INNER JOIN dp2_mysql.Inventory I ON S.InvID = I.InvID WHERE DATE(S.Date) BETWEEN '2016-09-01' AND '2016-09-30'");
		dbConn.CloseConnection();
		
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
		for(MonthlyReportData monthlyReportDataIterator : monthlyReportDataSet)
		{
			monthlyReportDataIterator.calculateItemProfit();
			System.out.println("-----------------------------------");
			System.out.println("Product Name: " + monthlyReportDataIterator.getProductName());
			System.out.println("Buy Price: " + monthlyReportDataIterator.getBuyPrice());
			System.out.println("Sell Price: " + monthlyReportDataIterator.getSellPrice());
			System.out.println("Quantity: " + monthlyReportDataIterator.getQuantity());
			System.out.println("-----------------------------------");
			System.out.println("Item Profit: " + monthlyReportDataIterator.getItemProfit());
		}
		System.out.println("-----------------------------------");
	}

}
