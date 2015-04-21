package deleting.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import deleting.controller.DeleteDBcontrol;

public class GuiPanel extends JPanel
{
	private DeleteDBcontrol baseController;
	private JButton submitButton;
	private SpringLayout appLayout;
	private ArrayList<JTextField> inputFieldList;
	private String table;
	
	public GuiPanel(DeleteDBcontrol baseController, String table)
	{
		this.baseController = baseController;
		this.table = table;
		appLayout = new SpringLayout();
		submitButton = new JButton();
		inputFieldList = new ArrayList<JTextField>();
		
		setupPanel(table);
		setupLayout();
		heyListen();
	}
	
	/**
	 * Generates objects for the panel.
	 * 
	 * @param setupPanel
	 *            ()
	 */
	private void setupPanel(
			String table)
	{
		this.setLayout(appLayout);
		this.add(submitButton);
		int startOffset = 20;
		String[] columns = baseController.getDatabase().getDatabaseColumnNames(table);
		for (int fieldCount = 0; fieldCount < columns.length; fieldCount++)
		{
			if (!columns[fieldCount].equalsIgnoreCase("id"))
			{
				JLabel test = new JLabel(baseController.getDatabase().getDatabaseColumnNames(table)[fieldCount]);
				JTextField textField = new JTextField(10);
				this.add(test);
				this.add(textField);
				
				inputFieldList.add(textField);
				
				appLayout.putConstraint(SpringLayout.NORTH, test, startOffset, SpringLayout.NORTH, this);
				appLayout.putConstraint(SpringLayout.NORTH, textField, startOffset, SpringLayout.NORTH, this);
				appLayout.putConstraint(SpringLayout.EAST, textField, 60, SpringLayout.EAST, test);
				
				startOffset += 50;
			}
		}
	}
	
	private void setupLayout()
	{
		appLayout.putConstraint(SpringLayout.SOUTH, submitButton, -50, SpringLayout.SOUTH, this);
	}
	
	/**
	 * Generates the value list for the submit statement.
	 * 
	 * @param getValueList
	 *            ()
	 * 
	 * @return values
	 */
	private String getValueList()
	{
		String values = "";
		for (int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getText();
			if (spot == inputFieldList.size() - 1)
			{
				values += "'" + temp + "')";
			}
			else
			{
				values += "'" + temp + "', ";
			}
			
		}
		return values;
	}
	
	/**
	 * Generates the field list for the submit statement.
	 * 
	 * @param getFieldList
	 *            ()
	 * 
	 * @return fields
	 */
	private String getFieldList()
	{
		String fields = "(";
		
		for (int spot = 0; spot < inputFieldList.size(); spot++)
		{
			String temp = inputFieldList.get(spot).getName();
			int cutOff = temp.indexOf("Field");
			temp = temp.substring(0, cutOff);
			if (spot == inputFieldList.size() - 1)
			{
				fields += "`" + temp + "`)";
			}
			else
			{
				fields += "`" + temp + "`, ";
			}
		}
		
		return fields;
	}
	
	/**
	 * Gives functionality to buttons.
	 * 
	 * @param heyListen
	 *            ()
	 */
	private void heyListen()
	{
		submitButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(
					ActionEvent click)
			{
				String myQuery = "INSERT INTO " + table + " " + getFieldList() + " VALUES " + getValueList() + ";";
				baseController.getDatabase().submitQuery(myQuery);
			}
		});
	}
}
