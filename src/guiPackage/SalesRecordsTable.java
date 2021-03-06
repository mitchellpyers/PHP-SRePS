package guiPackage;

import java.awt.Dimension;	

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * A class to initialise view of the Sales Records via a JTable
 * @author Aidan Crellin 9734996
 *
 */
public class SalesRecordsTable extends JTable{

	private static final long serialVersionUID = -5770141088152980774L;
	
	private JScrollPane salesRecordsScrollPane;
	
	/**
	 * Initalise the SalesRecordTable
	 */
	public SalesRecordsTable()
	{
		
		DefaultTableModel tableModel = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"Transaction ID", "Sale ID", "Product Name", "Purchase Quantity", "Product Price",
					"Sale Price","Total Transaction Cost","Date","Customer Name"
				}
			){
				private static final long serialVersionUID = -3876494968803592007L;

				@Override
				public boolean isCellEditable(int row, int column) {
					//all cells false
					return false;
				}
			};
		
		this.setModel(tableModel);
		this.setPreferredScrollableViewportSize(new Dimension(400, 280));
		this.setFillsViewportHeight(true);
		salesRecordsScrollPane = new JScrollPane(this);

	}

	/**
	 * Get the Sales records table
	 * @return JTable of the sales records
	 */
	JTable getSalesRecordsTable() {
		return this;
	}
	
	/**
	 * Get the Scroll Pane in which the sales records table is stored in.
	 * @return JScrollPane of the sales records table
	 */
	JScrollPane getSalesRecordsScrollPane() {
		return salesRecordsScrollPane;
	}	
}
