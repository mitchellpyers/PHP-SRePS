package guiPackage;

import java.awt.Component;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import databasePackage.DatabaseConnector;

/**
 * A class to construct the user input fields and panel
 * JSpinners have been used over JTextFields,
 * to allow for data validation.
 * @author Aidan Crellin 9734996
 *
 */
public class UserInputPanel extends JPanel{

	private JComboBox transactionIDComboBox;
	private JComboBox productNameComboBox;
	private JSpinner productQuantityField;
	//private JSpinner salePriceFIeld;
	//private JSpinner totalPriceField; 
	private JSpinner dateField;
	private JTextField customerNameField;
	private ArrayList<String> productNames;
	private ArrayList<String> transactionIDs;
	//private int transactionIDCount;
	private DatabaseConnector dbConn;
		
	public UserInputPanel(DatabaseConnector dbc) throws SQLException{
		
		dbConn = dbc;		
		
		transactionIDs = new ArrayList<String>();
		transactionIDs.add("Select ID");
		transactionIDs.add("New ID");
		transactionIDs.addAll(populateTransactionIDsToComboBox());
		
		productNames = new ArrayList<String>();
		productNames.add("Select Product");
		productNames.addAll(populateProductNamesToComboBox());
				
		JPanel transactionIDPanel = new JPanel();
		
		this.add(transactionIDPanel);
		transactionIDPanel.setLayout(new BoxLayout(transactionIDPanel, BoxLayout.Y_AXIS));
		
		JLabel transactionIDLabel = new JLabel("Transaction ID");
		transactionIDLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		transactionIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
		transactionIDPanel.add(transactionIDLabel);
		
		transactionIDComboBox = new JComboBox();
		transactionIDComboBox.setModel(new DefaultComboBoxModel(transactionIDs.toArray()));
		transactionIDComboBox.setSelectedIndex(0);
		transactionIDPanel.add(transactionIDComboBox);
		
		JPanel productNamePanel = new JPanel();
		this.add(productNamePanel);
		productNamePanel.setLayout(new BoxLayout(productNamePanel, BoxLayout.Y_AXIS));
		
		JLabel productNameLabel = new JLabel("Product Name");
		productNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		productNamePanel.add(productNameLabel);
		
		productNameComboBox = new JComboBox();
		productNameComboBox.setModel(new DefaultComboBoxModel(productNames.toArray()));
		productNameComboBox.setSelectedIndex(0);
		productNamePanel.add(productNameComboBox);
		
		JPanel quantityPanel = new JPanel();
		this.add(quantityPanel);
		quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));
		
		JLabel productQuantityLabel = new JLabel("Quantity");
		productQuantityLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		quantityPanel.add(productQuantityLabel);
		
		productQuantityField = new JSpinner();
		quantityPanel.add(productQuantityField);
		productQuantityField.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
/*		JPanel salePricePanel = new JPanel();
		this.add(salePricePanel);
		salePricePanel.setLayout(new BoxLayout(salePricePanel, BoxLayout.Y_AXIS));
		
		JLabel salePriceLabel = new JLabel("Sale Price");
		salePriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		salePricePanel.add(salePriceLabel);
		
		salePriceFIeld = new JSpinner();
		salePriceFIeld.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		salePricePanel.add(salePriceFIeld);*/
		
		/*
		JPanel totalPricePanel = new JPanel();
		this.add(totalPricePanel);
		totalPricePanel.setLayout(new BoxLayout(totalPricePanel, BoxLayout.Y_AXIS));
		
		JLabel totalPriceLabel = new JLabel("Total Price");
		totalPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		totalPricePanel.add(totalPriceLabel);
		
		totalPriceField = new JSpinner();
		totalPriceField.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		totalPricePanel.add(totalPriceField);
		*/
		
		JPanel datePanel = new JPanel();
		this.add(datePanel);
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
		
		JLabel dateLabel = new JLabel("Date");
		dateLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		datePanel.add(dateLabel);
		
		dateField = new JSpinner();
		dateField.setModel(new SpinnerDateModel(new Date(1472652000000L), null, null, Calendar.DAY_OF_YEAR));
		datePanel.add(dateField);
		
		JPanel customerNamePanel = new JPanel();
		this.add(customerNamePanel);
		customerNamePanel.setLayout(new BoxLayout(customerNamePanel, BoxLayout.Y_AXIS));
		
		JLabel customerNameLabel = new JLabel("Customer Name");
		customerNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		customerNamePanel.add(customerNameLabel);
		
		customerNameField = new JTextField();
		customerNamePanel.add(customerNameField);
		customerNameField.setColumns(10);
	}

	/**
	 * Get the combo box which stores all of the transaction IDs
	 * @return JComboBox containing the transaction IDs
	 */
	public JComboBox getTransactionIDComboBox() {
		return transactionIDComboBox;
	}
	
	/**
	 * Create a new transaction ID, and add it to the list of current transaction IDs
	 * @return the newly created transaction ID
	 * @throws SQLException 
	 */
	public void addNewIDToTransactionIDComboBox() throws SQLException {
		ArrayList<String> transactionIDList = new ArrayList<String>();
		transactionIDList.add("Select ID");
		transactionIDList.add("New ID");
		dbConn.InsertToDatabase("INSERT INTO Transaction () VALUES ()");
		transactionIDList.addAll(populateTransactionIDsToComboBox());
		transactionIDComboBox.setModel(new DefaultComboBoxModel(transactionIDList.toArray()));
	}
	
	public ArrayList<String> populateProductNamesToComboBox() throws SQLException {
		
		return dbConn.FindProductNames("SELECT DISTINCT productName FROM Inventory");
	}
	
	public ArrayList<String> populateTransactionIDsToComboBox() throws SQLException {
		return dbConn.FindTransactionIDs("SELECT TransID FROM Transaction");
	}
	/**
	 * Get the combo box which stores all of the product names
	 * @return JComboBox containing the product names
	 */
	public JComboBox getProductNameComboBox() {
		return productNameComboBox;
	}

	/**
	 * Get the field that contains number of products sold
	 * @return JSpinner containing the number of products sold
	 */
	public JSpinner getProductQuantityField() {
		return productQuantityField;
	}

	/**
	 * Get the field which contains the price per product paid
	 * @return JSpinner containing the price per product entered
	 */
/*	public JSpinner getSalePriceField() {
		return salePriceFIeld;
	}*/

	/*public JSpinner getTotalPriceField() {
		return totalPriceField;
	}*/

	/**
	 * Get the field containing the transaction date
	 * @return JSpinner containing the transaction's date
	 */
	public JSpinner getDateField() {
		return dateField;
	}

	/**
	 * Get the text field containing the customers name
	 * @return JTextField containing the customers Name
	 */
	public JTextField getCustomerNameField() {
		return customerNameField;
	}
}
