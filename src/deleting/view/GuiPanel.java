package deleting.view;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.*;

import deleting.controller.DeleteDBcontrol;

public class GuiPanel extends JPanel
{
	private DeleteDBcontrol baseController;
	private JButton submitButton;
	private SpringLayout appLayout;
	
	public GuiPanel(DeleteDBcontrol baseController, String table)
	{
		this.baseController = baseController;
		appLayout = new SpringLayout();
		submitButton = new JButton();
		
		setupPanel(table);
		setupLayout();
		heyListen();
	}
	
	private void setupPanel(
			String table)
	{
		this.setLayout(appLayout);
		this.add(submitButton);
		int startOffset = 20;
		for (int fieldCount = 0; fieldCount < baseController.getDatabase().getDatabaseColumnNames(table).length; fieldCount++)
		{
			JLabel test = new JLabel(baseController.getDatabase().getDatabaseColumnNames(table)[fieldCount]);
			JTextField textField = new JTextField(10);
			this.add(test);
			this.add(textField);
			
			appLayout.putConstraint(SpringLayout.NORTH, test, startOffset, SpringLayout.NORTH, this);
			appLayout.putConstraint(SpringLayout.NORTH, textField, startOffset, SpringLayout.NORTH, this);
			appLayout.putConstraint(SpringLayout.EAST, textField, 60, SpringLayout.EAST, test);
			
			startOffset += 50;
		}
	}
	
	private void setupLayout()
	{
		
	}
	
	private void heyListen()
	{
		ArrayList<JTextField> myTextFields = new ArrayList<JTextField>();
		for (Component current : this.getComponents())
		{
			if (current instanceof JTextField)
			{
				myTextFields.add((JTextField) current);
			}
		}
	}
}
