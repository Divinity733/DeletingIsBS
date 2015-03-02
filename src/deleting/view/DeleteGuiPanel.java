package deleting.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SpringLayout;

import deleting.controller.DeleteDBcontrol;

public class DeleteGuiPanel extends JPanel
{
	
	private DeleteDBcontrol baseController;
	private SpringLayout appLayout;
	private JTextArea displayArea;
	private JButton dadaButton;
	private JButton tableButton;
	private JButton clearButton;
	private JScrollPane displayPane;
	private SpringLayout baseLayout;
	
	public DeleteGuiPanel(DeleteDBcontrol baseController)
	{
		this.baseController = baseController;
		dadaButton = new JButton("Test the query");
		tableButton = new JButton("See Table");
		clearButton = new JButton("Clear!");
		displayArea = new JTextArea(10, 30);
		displayPane = new JScrollPane(displayArea);
		baseLayout = new SpringLayout();
		baseLayout.putConstraint(SpringLayout.WEST, clearButton, 0, SpringLayout.WEST, displayPane);
		baseLayout.putConstraint(SpringLayout.SOUTH, clearButton, 1, SpringLayout.SOUTH, this);
		
		setupPane();
		setupPanel();
		setupLayout();
		heyListen();
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
		this.add(clearButton);
		this.add(displayPane);
	}
	
	private void setupLayout()
	{
		baseLayout.putConstraint(SpringLayout.SOUTH, dadaButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, dadaButton, 1, SpringLayout.EAST, this);
		baseLayout.putConstraint(SpringLayout.SOUTH, tableButton, 1, SpringLayout.SOUTH, this);
		baseLayout.putConstraint(SpringLayout.EAST, tableButton, 0, SpringLayout.WEST, dadaButton);
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
		
		clearButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent click)
			{
				displayArea.setText("");
			}
		});
	}
}
