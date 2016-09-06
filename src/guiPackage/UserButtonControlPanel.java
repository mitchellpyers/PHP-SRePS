package guiPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

/**
 * A class to construct the control buttons for the GUI.
 * @author Aidan Crellin 9734996
 *
 */
public class UserButtonControlPanel extends JPanel{

	private UserInputPanel userInputPanel;
	private SalesRecordsTable salesRecordsTable;
	
	public UserButtonControlPanel(SalesRecordsTable sRT,UserInputPanel uIP){
		
		this.salesRecordsTable = sRT;
		this.userInputPanel = uIP;
		
		JButton addSalesRecordButton = new JButton("Add");
		addSalesRecordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String data1 = (String) userInputPanel.getTransactionIDComboBox().getSelectedItem();
				if(data1 == "Select ID"){
					incorrectInformationEntered("Transaction ID");
					return;
				}
				else if(data1 == "New ID"){
					data1 = userInputPanel.addNewIDToTransactionIDComboBox();
				}
			    String data2 = Integer.toString(salesRecordsTable.getNewSaleID());			    
			    String data3 = (String) userInputPanel.getProductNameComboBox().getSelectedItem();
			    if(data3 == "Select Product"){
					incorrectInformationEntered("Product");
					return;
				}
			    String data4 = Integer.toString((Integer)userInputPanel.getProductQuantityField().getValue());
			    String data5 = Double.toString((Double)userInputPanel.getSalePriceField().getValue());
			    String data6 = Double.toString((Double)userInputPanel.getSalePriceField().getValue()*
			    				(Integer)userInputPanel.getProductQuantityField().getValue());
			    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			    String data7 = df.format(userInputPanel.getDateField().getValue());
			    String data8 = userInputPanel.getCustomerNameField().getText();

			    Object[] rowToAdd = { data1, data2, data3, data4, data5, data6, data7, data8 };

			    DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getModel();

			    model.addRow(rowToAdd);

			    userInputPanel.getTransactionIDComboBox().setSelectedIndex(0);
			    userInputPanel.getProductNameComboBox().setSelectedIndex(0);
			    userInputPanel.getProductQuantityField().setValue(new Integer(1));
			    userInputPanel.getSalePriceField().setValue(new Double(0.0));
			    //userInputPanel.getTotalPriceField().setValue(new Double(0.0));
			    userInputPanel.getCustomerNameField().setText("");
			    
			}
		});
		this.add(addSalesRecordButton);
		
		JButton editSalesRecord = new JButton("Edit");
		editSalesRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getModel();

			    int rowNumber = salesRecordsTable.getSalesRecordsTable().getSelectedRow();
			    if(rowNumber > -1)
			    {
			    	String data1 = "This value should not change";
				    String data2 = "this value should not change";		    
				    String data3 = (String) userInputPanel.getProductNameComboBox().getSelectedItem();
				    if(data3 == "Select Product"){
						incorrectInformationEntered("Product");
						return;
					}
				    String data4 = Integer.toString((Integer)userInputPanel.getProductQuantityField().getValue());
				    String data5 = Double.toString((Double)userInputPanel.getSalePriceField().getValue());
				    String data6 = Double.toString((Double)userInputPanel.getSalePriceField().getValue()*
		    				(Integer)userInputPanel.getProductQuantityField().getValue());
				    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				    String data7 = df.format(userInputPanel.getDateField().getValue());
				    String data8 = userInputPanel.getCustomerNameField().getText();

				    Object[] rowToAdd = { data1, data2, data3, data4, data5, data6, data7, data8 };

			    	for(int i = 2; i < model.getColumnCount(); i++)
				    {
				    	model.setValueAt(rowToAdd[i], rowNumber, i);
				    }
			    }	
							    
			    userInputPanel.getTransactionIDComboBox().setSelectedIndex(0);
			    userInputPanel.getProductNameComboBox().setSelectedIndex(0);
			    userInputPanel.getProductQuantityField().setValue(new Integer(1));
			    userInputPanel.getSalePriceField().setValue(new Double(0.0));
			    //userInputPanel.getTotalPriceField().setValue(new Double(0.0));
			    userInputPanel.getCustomerNameField().setText("");
			}
		});
		this.add(editSalesRecord);
		
		JButton removeSalesRecord = new JButton("Remove");
		removeSalesRecord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    DefaultTableModel model = (DefaultTableModel) salesRecordsTable.getSalesRecordsTable().getModel();

			    int rowNumber = salesRecordsTable.getSalesRecordsTable().getSelectedRow();
			    if(rowNumber > -1)
			    {
			    	model.removeRow(rowNumber);
			    }	    
			}
		});
		this.add(removeSalesRecord);
		
		JButton button_3 = new JButton("<Unused>");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Unused
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
}
