package deleting.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import deleting.controller.DeleteDBcontrol;

public class DeleteGuiPanel extends JPanel
{
	private DeleteDBcontrol baseController;
	private JTextArea displayArea;
	private JTextArea textArea;
	private JButton dadaButton;
	private JButton tableButton;
	private JButton insertButton;
	private JButton clearButton;
	private JScrollPane displayPane;
	private JScrollPane textPane;
	private SpringLayout baseLayout;
	private JTable tableData;
	private JPasswordField password;
	private TableCellWrapRenderer cellRenderer;
	
	/**
	 * Defines objects
	 * 
	 * @param baseController
	 */
	public DeleteGuiPanel(DeleteDBcontrol baseController)
	{
		this.baseController = baseController;
		dadaButton = new JButton("Test the query");
		tableButton = new JButton("See Table");
		insertButton = new JButton("Insert into table");
		clearButton = new JButton("Clear!");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		textArea = new JTextArea(5, 20);
		textPane = new JScrollPane(textArea);
		baseLayout = new SpringLayout();
		password = new JPasswordField(null, 20);
		cellRenderer = new TableCellWrapRenderer();
		
		setupTable();
		setupPane();
		setupPanel();
		setupLayout();
		heyListen();
	}
	
	/**
	 * Grabs and creates table
	 */
	private void setupTable()
	{
		tableData = new JTable(new DefaultTableModel(baseController.getDatabase().tableInfo(), baseController.getDatabase().getMetaData()));
		
		displayPane = new JScrollPane(tableData);
		
		for(int spot = 0; spot < tableData.getColumnCount(); spot++)
		{
			tableData.getColumnModel().getColumn(spot).setCellRenderer(cellRenderer);
		}
	}
	
	/**
	 * Places restrictions on displayArea
	 */
	private void setupPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
	}
	
	/**
	 * Adds objects to the window
	 */
	private void setupPanel()
	{
		this.setBackground(Color.GRAY);
		this.setLayout(baseLayout);
		this.add(dadaButton);
		this.add(tableButton);
		this.add(insertButton);
		this.add(clearButton);
		this.add(displayPane);
		this.add(textPane);
		this.add(password);
		password.setEchoChar('Ω');
		password.setForeground(Color.GREEN);
		password.setFont(new Font("Serif", Font.BOLD, 30));
	}
	
	/**
	 * Creates the layout of the window
	 */
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.SOUTH, dadaButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, dadaButton, 1, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, tableButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, tableButton, 0, SpringLayout.WEST, dadaButton);
		baseLayout.putConstraint(SpringLayout.SOUTH, insertButton, -6, SpringLayout.NORTH, dadaButton);
		baseLayout.putConstraint(SpringLayout.EAST, insertButton, 1, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, clearButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, clearButton, 0, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, displayPane, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, displayPane, 500, SpringLayout.WEST, this);
		baseLayout.putConstraint(SpringLayout.NORTH, textPane, 50, SpringLayout.NORTH, this);
		baseLayout.putConstraint(SpringLayout.WEST, textPane, 25, SpringLayout.WEST, this);
	}
	
	/**
	 * Defines how the buttons are suppose to operate USELESS FOR NOW!
	 */
	private void heyListen()
	{
		dadaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(
					ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().displayTables();
				textArea.setText(databaseAnswer);
			}
		});
		
		tableButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(
					ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().describeTable();
				textArea.setText(databaseAnswer);
			}
		});
		
		insertButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(
					ActionEvent click)
			{
				int insert = baseController.getDatabase().insertData();
				textArea.setText(displayArea.getText() + "\nRows Affected: " + insert);
			}
		});
		
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(
					ActionEvent click)
			{
				textArea.setText("");
			}
		});
	}
}
