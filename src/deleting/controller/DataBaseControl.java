package deleting.controller;

import java.sql.*;

import javax.swing.JOptionPane;

import deleting.controller.DeleteDBcontrol;


public class DataBaseControl
{
	private String connectionString;
	private Connection dadaConnect;
	private DeleteDBcontrol baseController;
	
	public DataBaseControl(DeleteDBcontrol baseController)
	{
		connectionString = "jdbc:mysql:/localhost/database_name?user=root";
		this.baseController = baseController;
		checkDriver();
		setupConnection();
	}
	
	private void checkDriver()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception currentException)
		{
			displayErrors(currentException);
			System.exit(1);
		}
	}
	
	private void closeConnection()
	{
		try
		{
			dadaConnect.close();
		}
		catch(SQLException error)
		{
			displayErrors(error);
		}
	}
	
	private void setupConnection()
	{
		try
		{
			dadaConnect = DriverManager.getConnection(connectionString);
		}
		catch(SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		
		if(currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
	}
}
