package deleting.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;
import javax.swing.table.DefaultTableModel;

import deleting.controller.DeleteDBcontrol;

public class DeleteGuiPanel extends JPanel
{
	
	private DeleteDBcontrol baseController;
	private SpringLayout appLayout;
	private JTextArea displayArea;
	private JButton dadaButton;
	private JButton tableButton;
	private JButton insertButton;
	private JButton clearButton;
	private JScrollPane displayPane;
	private SpringLayout baseLayout;
	private JTable tableData;
	
	public DeleteGuiPanel(DeleteDBcontrol baseController)
	{
		this.baseController = baseController;
		dadaButton = new JButton("Test the query");
		tableButton = new JButton("See Table");
		insertButton = new JButton("Insert into table");
		clearButton = new JButton("Clear!");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		
		setupTable();
		setupPane();
		setupPanel();
		setupLayout();
		heyListen();
	}
	
	private void setupTable()
	{
		tableData = new JTable(new DefaultTableModel(baseController.getDatabase().realInfo(), baseController.getDatabase().getMetaData()));
		
		displayPane = new JScrollPane(tableData);
	}
	
	private void setupPane()
	{
		displayArea.setWrapStyleWord(true);
		displayArea.setLineWrap(true);
		displayArea.setEditable(false);
	}
	
	private void setupPanel()
	{
		this.setBackground(Color.GRAY);
		this.setLayout(baseLayout);
		this.add(dadaButton);
		this.add(tableButton);
		this.add(insertButton);
		this.add(clearButton);
		this.add(displayPane);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.SOUTH, dadaButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, dadaButton, 1, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, tableButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, tableButton, 0, SpringLayout.WEST, dadaButton);
		baseLayout.putConstraint(SpringLayout.SOUTH, insertButton, -6, SpringLayout.NORTH, dadaButton);
		baseLayout.putConstraint(SpringLayout.EAST, insertButton, 1, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.WEST, clearButton, 0, SpringLayout.WEST, displayPane);
		baseLayout.putConstraint(SpringLayout.SOUTH, clearButton, 1, SpringLayout.SOUTH, this);
	}
	
	private void heyListen()
	{
		dadaButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().displayTables();
				displayArea.setText(databaseAnswer);
			}
		});
		
		tableButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				String databaseAnswer = baseController.getDatabase().describeTable();
				displayArea.setText(databaseAnswer);
			}
		});
		
		insertButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				int insert = baseController.getDatabase().insertData();
				displayArea.setText(displayArea.getText() + "\nRows Affected: " + insert);
			}
		});
		
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				displayArea.setText("");
			}
		});
	}
}
