package guiPackage;

import java.awt.EventQueue;

import javax.swing.JFrame;

import databasePackage.DatabaseConnector;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.Insets;
import java.sql.SQLException;

/**
 * The Main class of the SRS-SRePs application.
 * Initialises the GUI and starts the application.
 * @author Aidan Crellin 9734996
 *
 */
public class GUIMain {

	private JFrame frame;
	private SalesRecordsTable salesRecordsTable;
	private UserInputPanel userInputPanel;
	private UserButtonControlPanel userButtonControlPanel;
	private ReportGenerationPanel reportGenerationPanel;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIMain window = new GUIMain();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public GUIMain() throws SQLException {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws SQLException 
	 */
	private void initialize() throws SQLException {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 1000, 500);
		frame.setMinimumSize(new Dimension(800, 300));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("PHP-SRePs");
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		DatabaseConnector dbConn = new DatabaseConnector();
		
		salesRecordsTable = new SalesRecordsTable();		
		GridBagConstraints gbc_SalesRecordsTable = new GridBagConstraints();
		gbc_SalesRecordsTable.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc_SalesRecordsTable.fill = GridBagConstraints.HORIZONTAL;
		gbc_SalesRecordsTable.insets = new Insets(0, 0, 5, 0);
		gbc_SalesRecordsTable.gridwidth = 3;
		gbc_SalesRecordsTable.gridx = 0;
		gbc_SalesRecordsTable.gridy = 0;
		frame.getContentPane().add(salesRecordsTable.getSalesRecordsScrollPane(), gbc_SalesRecordsTable);
		
		//userInputPanel = new UserInputPanel(null);
		userInputPanel = new UserInputPanel(dbConn);
		GridBagConstraints gbc_UserInputPanel = new GridBagConstraints();
		gbc_UserInputPanel.gridwidth = 3;
		gbc_UserInputPanel.fill = GridBagConstraints.BOTH;
		gbc_UserInputPanel.gridx = 0;
		gbc_UserInputPanel.gridy = 2;
		frame.getContentPane().add(userInputPanel, gbc_UserInputPanel);
		
		reportGenerationPanel = new ReportGenerationPanel(userInputPanel,dbConn);
		//userButtonControlPanel = new UserButtonControlPanel(salesRecordsTable,userInputPanel,null,reportGenerationPanel);
		userButtonControlPanel = new UserButtonControlPanel(salesRecordsTable,userInputPanel,dbConn,reportGenerationPanel);
		GridBagConstraints gbc_UserButtonControlPanel = new GridBagConstraints();
		gbc_UserButtonControlPanel.gridwidth = 3;
		gbc_UserButtonControlPanel.insets = new Insets(0, 0, 5, 5);
		gbc_UserButtonControlPanel.fill = GridBagConstraints.BOTH;
		gbc_UserButtonControlPanel.gridx = 0;
		gbc_UserButtonControlPanel.gridy = 3;
		frame.getContentPane().add(userButtonControlPanel, gbc_UserButtonControlPanel);	
		
		GridBagConstraints gbc_ReportGenerationPanel = new GridBagConstraints();
		gbc_ReportGenerationPanel.gridwidth = 3;
		gbc_ReportGenerationPanel.insets = new Insets(0, 0, 5, 5);
		gbc_ReportGenerationPanel.fill = GridBagConstraints.BOTH;
		gbc_ReportGenerationPanel.gridx = 0;
		gbc_ReportGenerationPanel.gridy = 4;
		frame.getContentPane().add(reportGenerationPanel, gbc_ReportGenerationPanel);	
		System.out.println(reportGenerationPanel.getSize());
	}
}
