package databasePackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DatabaseConnector 
{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://dp2-mysql.caoqebumdage.us-west-2.rds.amazonaws.com/dp2_mysql";
	//  Database credentials
	static final String USER = "dp2project";
	static final String PASS = "dp2swinburne";
	
	Connection conn = null;
	Statement stmt = null;
	
	public DatabaseConnector()
	{
		try
		{
	      //STEP 2: Register JDBC driver
	      Class.forName("com.mysql.jdbc.Driver");
	      //STEP 3: Open a connection
	      System.out.println("Connecting to Database: " + DB_URL);
	      System.out.println("Using Credentials: " + USER + " / " + PASS);
	      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void InsertToDatabase(String sqlQuery) throws SQLException
	{
		System.out.println("INSERTING to the Database: " + sqlQuery);
		stmt = conn.createStatement();
		stmt.executeUpdate(sqlQuery);
		stmt.close();
	}

	public void UpdateToDatabase(String sqlQuery) throws SQLException
	{
		System.out.println("UPDATING to the Database: " + sqlQuery);
		stmt = conn.createStatement();
		stmt.executeUpdate(sqlQuery);
		stmt.close();
	}
	
	public int SelectLatestTransactionID(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Transaction: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		int transID = 0;
	    while(rs.next())
	    {
	    	 transID = rs.getInt("TransID");
			 System.out.println("Transaction ID: " + transID);
	     }
	     rs.close();
	     stmt.close();
	     return transID;
	}
	
	public void SelectFromSales(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Sales: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
	    while(rs.next())
	    {
			 int saleID  = rs.getInt("SaleID");
			 int invID  = rs.getInt("InvID");
			 int transID = rs.getInt("TransID");
			 int Qty = rs.getInt("Qty");
			 int salePrice = rs.getInt("salePrice");
			 java.sql.Date date = rs.getDate("Date");
			 
			 //Display values
			 System.out.println("Inventory ID: " + invID);
			 System.out.println("Sale ID: " + saleID);
			 System.out.println("Transaction ID: " + transID);
			 System.out.println("Sale Price: " + salePrice);
			 System.out.println("Quantity: " + Qty);
			 System.out.println("Date: " + date);
	     }
	     rs.close();
	     stmt.close();
	}	
	public void SelectFromTransaction(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Transaction: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
	    while(rs.next())
	    {
	    	 int transID = rs.getInt("TransID");
			 int totalPrice = rs.getInt("totalPrice");
			 String customerName = rs.getString("customerName");
			 java.sql.Date date = rs.getDate("Date");
			
			 //Display values
			 System.out.println("Transaction ID: " + transID);
			 System.out.println("Total Price: " + totalPrice);
			 System.out.println("Customer Name: " + customerName);
			 System.out.println("Date: " + date);
	     }
	     rs.close();
	     stmt.close();
	}	
	public void SelectFromInventory(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Inventory: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
	    while(rs.next())
	    {
			 int invID  = rs.getInt("InvID");
			 String productName = rs.getString("productName");
			 int buyPrice = rs.getInt("buyPrice");
			 int sellPrice = rs.getInt("sellPrice");
			 int Qty = rs.getInt("Qty");
			 java.sql.Date date = rs.getDate("Date");
			 
			 //Display values
			 System.out.println("Inventory ID: " + invID);
			 System.out.println("Product Name: " + productName);
			 System.out.println("Buy Price: " + buyPrice);
			 System.out.println("Sell Price: " + sellPrice);
			 System.out.println("Quantity: " + Qty);
			 System.out.println("Date: " + date);
	     }
	     //STEP 6: Clean-up environment
	     rs.close();
	     stmt.close();
	}
	public ArrayList<String> FindCurrentItemPrice(String sqlQuery) throws SQLException
	{
		ArrayList<String> itemPrice = new ArrayList<String>();
		System.out.println("SELECTING From Inventory: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		int sellPrice = 0;
		int invID = 0;
	    while(rs.next())
	    {
			 invID = rs.getInt("InvID");
			 sellPrice = rs.getInt("sellPrice");
			 
			 //Display values
			 System.out.println("Inventory ID: " + invID);
			 System.out.println("Sell Price: " + sellPrice);
	     }
	     //STEP 6: Clean-up environment
	     itemPrice.add(Integer.toString(invID));
	     itemPrice.add(Integer.toString(sellPrice));
	     rs.close();
	     stmt.close();
	     return itemPrice;
	}
	
	public int FindCurrentSaleID(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Sales: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		int saleID = 0;
	    while(rs.next())
	    {
			 saleID = rs.getInt("SaleID");
			 //Display values
			 System.out.println("SaleID: " + saleID);
	     }
	     //STEP 6: Clean-up environment
	     rs.close();
	     stmt.close();
	     return saleID;
	}
	
	public ArrayList<String> FindProductNames(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Inventory: " + sqlQuery);
		ArrayList<String> productNames = new ArrayList<String>();	
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		String productName;
	    while(rs.next())
	    {
    		productName = rs.getString("productName");
    		productNames.add(productName);
			//Display values
			System.out.println("Product Name: " + productName);
	     }
	     //STEP 6: Clean-up environment
	     
	     rs.close();
	     stmt.close();
	     return productNames;
	}
	
	public ArrayList<String> FindTransactionIDs(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Transaction: " + sqlQuery);
		ArrayList<String> transactionIDs = new ArrayList<String>();
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		int tempTransactionID = 0;
		String transactionID;
		
	    while(rs.next())
	    {
	    	 tempTransactionID = rs.getInt("TransID");
	    	 transactionID = Integer.toString(tempTransactionID);
	    	 transactionIDs.add(transactionID);
			 //Display values
			 System.out.println("Transaction ID: " + transactionID);
	     }
	     rs.close();
	     stmt.close();
	     return transactionIDs;
	}	
	
	public int FindTotalSaleCost(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From Sales: " + sqlQuery);
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		int totalSalePrice = 0;
	    while(rs.next())
	    {
			 int salePrice = rs.getInt("salePrice");
			 totalSalePrice = totalSalePrice + salePrice;
			 //Display values
	     }

	     System.out.println("Total Sale Price: " + totalSalePrice);
	     rs.close();
	     stmt.close();
	     return totalSalePrice;
	}
	
	public ArrayList<ArrayList<String>> PopulateJTable(String sqlQuery) throws SQLException
	{
		System.out.println("SELECTING From ALL OF THE THINGS: " + sqlQuery);
		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();	
		stmt = conn.createStatement();
		java.sql.ResultSet rs = stmt.executeQuery(sqlQuery);
		
		String transID;
		String saleID;
		String productName;
		String qty;
		String salePrice;
		String sellPrice;
		String totalPrice;
		java.sql.Date date;
		String customerName;
		String stringDate;
		
	    while(rs.next())
	    {
	    	ArrayList<String> databaseRecord = new ArrayList<String>();
	    	saleID = Integer.toString(rs.getInt("SaleID"));
	    	transID = Integer.toString(rs.getInt("TransID"));
	    	qty = Integer.toString(rs.getInt("Qty"));
	    	salePrice = Integer.toString(rs.getInt("salePrice"));
	    	sellPrice = Integer.toString(rs.getInt("sellPrice"));
	    	totalPrice = Integer.toString(rs.getInt("totalPrice"));
    		productName = rs.getString("productName");
    		customerName = rs.getString("customerName");
    		date = rs.getDate("Date");
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //to convert Date to String, use format method of SimpleDateFormat class.
            stringDate = dateFormat.format(date);
			//Display values
    		//Too many to print
            
            databaseRecord.add(transID);
            databaseRecord.add(saleID);
            databaseRecord.add(productName);
            databaseRecord.add(qty);
            databaseRecord.add(sellPrice);
            databaseRecord.add(salePrice);
            databaseRecord.add(totalPrice);
            databaseRecord.add(stringDate);
            databaseRecord.add(customerName);
            database.add(databaseRecord);
	     }
	     //STEP 6: Clean-up environment
	     rs.close();
	     stmt.close();
	     return database;
	}
	
	public void CloseConnection() throws SQLException
	{
		System.out.println("Closing the Connection to the Database: " + DB_URL);
		conn.close();
	}
}
