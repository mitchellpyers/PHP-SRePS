package guiPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import databasePackage.DatabaseConnector;

/**
 * A class to construct the control buttons for the GUI.
 * @author Aidan Crellin 9734996
 *
 */
public class UserButtonControlPanel extends JPanel{

	private UserInputPanel userInputPanel;
	private SalesRecordsTable salesRecordsTable;
	private DatabaseConnector dbConn;
	private ReportGenerationPanel reportGenerationPanel;
	private boolean visibilityFlag;
	
	public UserButtonControlPanel(SalesRecordsTable sRT,UserInputPanel uIP, DatabaseConnector dbc, ReportGenerationPanel rGP) throws SQLException{
		
		salesRecordsTable = sRT;
		userInputPanel = uIP;
		dbConn = dbc;
		reportGenerationPanel = rGP;
		
		visibilityFlag = false;
		
		updateSalesRecordsTable();
		
		JButton addSalesRecordButton = new JButton("Add");
		addSalesRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				String data1 = (String) userInputPanel.getTransactionIDComboBox().getSelectedItem();
				if(data1 == "Select ID"){
					incorrectInformationEntered("Transaction ID");
					return;
				}
				else if(data1 == "New ID"){
						userInputPanel.addNewIDToTransactionIDComboBox();
						data1 = Integer.toString(dbConn.SelectLatestTransactionID("SELECT TransID FROM Transaction WHERE TransID = (SELECT max(TransID) FROM Transaction)"));
				}
//			    String data2 = Integer.toString(dbConn.FindCurrentSaleID("SELECT SaleID FROM Sales WHERE SaleID = (SELECT max(SaleID) FROM Sales)")+1);			    
			    String data3 = (String) userInputPanel.getProductNameComboBox().getSelectedItem();
			    if(data3 == "Select Product"){
					incorrectInformationEntered("Product");
					return;
				}
			    String data4 = Integer.toString((Integer)userInputPanel.getProductQuantityField().getValue());
//			    String data5 = Double.toString((Double)userInputPanel.getSalePriceField().getValue());
//			    String data6 = Double.toString((Double)userInputPanel.getSalePriceField().getValue()*
//			    				(Integer)userInputPanel.getProductQuantityField().getValue());
			    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			    String data7 = df.format(userInputPanel.getDateField().getValue());
			    String data8 = userInputPanel.getCustomerNameField().getText();

			    //ADD TO DB
			    //Select InventoryID/ProductID/SellPrice FROM Inventory Where ProductName = ProductName
			    ArrayList<String> inventoryQuery = dbConn.FindCurrentItemPrice("SELECT InvID, sellPrice FROM Inventory WHERE InvID = (SELECT max(InvID) FROM Inventory WHERE productName = '" + data3 + "')");
			    //Insert INTO Sales
			    int salePrice = (Integer.parseInt(inventoryQuery.get(1)) * Integer.parseInt(data4));
			    dbConn.InsertToDatabase("INSERT INTO Sales (InvID, TransID, Qty, salePrice, Date) VALUES (" + inventoryQuery.get(0) + ", " + data1 + ", " + data4 + ", " + salePrice + ", '" + data7 + "')");
			    //CalcTotalPrice (Data Chingus)
			    int totalSalePrice = dbConn.FindTotalSaleCost("SELECT salePrice FROM Sales WHERE TransID = " + data1);
			    //Update Total Price
			    dbConn.UpdateToDatabase("UPDATE Transaction SET totalPrice = " + totalSalePrice + " WHERE TransID = " + data1);
			    //Update Date
			    dbConn.UpdateToDatabase("UPDATE Transaction SET Date = '" + data7 + "' WHERE TransID = "+ data1);
			    //Update Customer Name
			    dbConn.UpdateToDatabase("UPDATE Transaction SET customerName = '" + data8 + "' WHERE TransID = " + data1);
			    //REFRESH GUI
			    //LATER TONIGHT
			    
			    updateSalesRecordsTable();
			    
//			    Object[] rowToAdd = { data1, data3, data4, data7, data8 };
//
//			    DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getModel();
//
//			    model.addRow(rowToAdd);

			    userInputPanel.getTransactionIDComboBox().setSelectedIndex(0);
			    userInputPanel.getProductNameComboBox().setSelectedIndex(0);
			    userInputPanel.getProductQuantityField().setValue(new Integer(1));
//			    userInputPanel.getSalePriceField().setValue(new Double(0.0));
			    //userInputPanel.getTotalPriceField().setValue(new Double(0.0));
			    userInputPanel.getCustomerNameField().setText("");
			    
			    
			}
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
		});
		this.add(addSalesRecordButton);
		
//		JButton editSalesRecord = new JButton("Edit");
//		editSalesRecord.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				
//				DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getModel();
//
//			    int rowNumber = salesRecordsTable.getSalesRecordsTable().getSelectedRow();
//			    if(rowNumber > -1)
//			    {
//			    	String data1 = "This value should not change";
//				    String data2 = "this value should not change";		    
//				    String data3 = (String) userInputPanel.getProductNameComboBox().getSelectedItem();
//				    if(data3 == "Select Product"){
//						incorrectInformationEntered("Product");
//						return;
//					}
//				    String data4 = Integer.toString((Integer)userInputPanel.getProductQuantityField().getValue());
////				    String data5 = Double.toString((Double)userInputPanel.getSalePriceField().getValue());
////				    String data6 = Double.toString((Double)userInputPanel.getSalePriceField().getValue()*
////		    				(Integer)userInputPanel.getProductQuantityField().getValue());
//				    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//				    String data7 = df.format(userInputPanel.getDateField().getValue());
//				    String data8 = userInputPanel.getCustomerNameField().getText();
//
////				    Object[] rowToAdd = { data1, data2, data3, data4, data5, data6, data7, data8 };
////
////			    	for(int i = 2; i < model.getColumnCount(); i++)
////				    {
////				    	model.setValueAt(rowToAdd[i], rowNumber, i);
////				    }
//			    }	
//							    
//			    userInputPanel.getTransactionIDComboBox().setSelectedIndex(0);
//			    userInputPanel.getProductNameComboBox().setSelectedIndex(0);
//			    userInputPanel.getProductQuantityField().setValue(new Integer(1));
////			    userInputPanel.getSalePriceField().setValue(new Double(0.0));
//			    //userInputPanel.getTotalPriceField().setValue(new Double(0.0));
//			    userInputPanel.getCustomerNameField().setText("");
//			}
//		});
//		this.add(editSalesRecord);
		
		JButton removeSalesRecord = new JButton("Remove");
		removeSalesRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getSalesRecordsTable().getModel();

			    int rowNumber = salesRecordsTable.getSalesRecordsTable().getSelectedRow();
			    if(rowNumber > -1)
			    {
			    	String data1 = model.getValueAt(rowNumber, 0).toString();
			    	String data2 = model.getValueAt(rowNumber, 1).toString();
			    	try {
						dbConn.UpdateToDatabase("DELETE FROM Sales WHERE SaleID = " + data2);
					    int totalSalePrice = dbConn.FindTotalSaleCost("SELECT salePrice FROM Sales WHERE TransID = " + data1);
					    //Update Total Price
					    dbConn.UpdateToDatabase("UPDATE Transaction SET totalPrice = " + totalSalePrice + " WHERE TransID = " + data1);
					    updateSalesRecordsTable();
			    	} catch (SQLException e1) {
						e1.printStackTrace();
					}    	
			    		
			    }	    
			}
		});
		this.add(removeSalesRecord);
		
		JButton button_3 = new JButton("Report Generation");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				togglereportGenerationPanelVisibility();
			}
		});
		this.add(button_3);		
	}
	
	/**
	 * If incorrect information has been entered into a field,
	 * a option pane will pop up explaining which field has the incorrect information
	 * @param fieldInformation the field that has inccorect information
	 */
	private void incorrectInformationEntered(String fieldInformation)
	{
		JOptionPane.showMessageDialog(this,"Please select a valid " + fieldInformation);
	}
	
	private void updateSalesRecordsTable() throws SQLException
	{		
		ArrayList<ArrayList<String>> database = new ArrayList<ArrayList<String>>();
		database = dbConn.PopulateJTable("SELECT T.TransID, S.SaleID, I.productName, S.Qty, I.sellPrice, S.salePrice, T.totalPrice, T.Date, T.customerName FROM Sales S INNER JOIN Transaction T ON S.TransID = T.TransID INNER JOIN Inventory I ON S.InvID = I.InvID ORDER BY T.TransID, S.SaleID ASC");
		//pull all the data from database (inefficient)
		//data order is: Trans ID, Sale ID, Product Name, Qty, Sale Price, Total Price, Date, Name
		//2D ArrayList
		DefaultTableModel updatedModel = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
						"Transaction ID", "Sale ID", "Product Name", "Purchase Quantity", "Product Price",
						"Sale Price","Total Transaction Cost","Date","Customer Name"
				}
			){
			@Override
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		
		for(int i = 0; i < database.size(); i++)
		{
			updatedModel.addRow(database.get(i).toArray());
		}
		salesRecordsTable.setModel(updatedModel);
	}
	
	private void togglereportGenerationPanelVisibility(){
		visibilityFlag = !visibilityFlag;
		reportGenerationPanel.setVisible(visibilityFlag);
	}
}
