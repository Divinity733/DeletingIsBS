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
		connectionString = "jdbc:mysql://localhost/games?user=root";
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
		catch (Exception currentException)
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
		catch (SQLException error)
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
		catch (SQLException currentException)
		{
			displayErrors(currentException);
		}
	}
	
	public void displayErrors(Exception currentException)
	{
		JOptionPane.showMessageDialog(baseController.getAppFrame(), currentException.getMessage());
		
		if (currentException instanceof SQLException)
		{
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL State: " + ((SQLException) currentException).getSQLState());
			JOptionPane.showMessageDialog(baseController.getAppFrame(), "SQL Error Code: " + ((SQLException) currentException).getErrorCode());
		}
	}
	
	public String displayTables()
	{
		String results = "";
		String query = "SHOW DATABASES";
		
		try
		{
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while (answer.next())
			{
				results += answer.getString(1) + "\n";
			}
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	public String describeTable()
	{
		String results = "";
		String query = "DESCRIBE `game_categories`";
		
		try
		{
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			while (answer.next())
			{
				results += answer.getString(1) + "\t" + answer.getString(2) + "\t" + answer.getString(3) + "\n";
			}
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return results;
	}
}
