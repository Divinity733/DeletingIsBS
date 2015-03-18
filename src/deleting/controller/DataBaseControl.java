package deleting.controller;

import java.sql.*;

import javax.swing.JOptionPane;

import deleting.controller.DeleteDBcontrol;

public class DataBaseControl
{
	private String connectionString;
	private Connection dadaConnect;
	private DeleteDBcontrol baseController;
	private String currentQuery;
	
	public DataBaseControl(DeleteDBcontrol baseController)
	{
		String pathToDBServer = "localhost";
		String databaseName = "games";
		String userName = "root";
		String password = "";
		
//		connectionString = "jdbc:mysql://localhost/games?user=root";
		this.baseController = baseController;
		checkDriver();
		connectionStringBuilder(pathToDBServer, databaseName, userName, password);
		setupConnection();
	}
	
	public void connectionStringBuilder(String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/"
				+ databaseName;
		connectionString += "?user="
				+ userName;
		connectionString += "&password="
				+ password;
	}
	
	/**
	 * Checks if you have the correct driver to access database
	 * 
	 * @param checkDriver
	 *            ()
	 */
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
	
	/**
	 * Connects to the database
	 * 
	 * @param setupConnection
	 *            ()
	 * @return dadaConnect
	 */
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
	
	/**
	 * Lists all available databases
	 * 
	 * @param displayTables
	 *            ()
	 * @return results
	 */
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
	
	/**
	 * Lists all available databases
	 * 
	 * @param realInfo
	 *            ()
	 * @return results
	 */
	public String[][] realInfo()
	{
		String[][] results;
		String query = "SELECT * FROM `game_categories`";
		
		try
		{
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int columnCount = answer.getMetaData().getColumnCount();
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][columnCount];
			
			while (answer.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
			}
			
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	private boolean checkDataViolation()
	{
		if(currentQuery.toUpperCase().contains(" DROP ")
				|| currentQuery.toUpperCase().contains(" TRUNCATE ")
				|| currentQuery.toUpperCase().contains(" SET ")
				|| currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String [][] selectQueryResults(String query)
	{
		this.currentQuery = query;
		String [][] results;
		
		try
		{
			if(checkDataViolation())
			{
				throw new SQLException("illegal modification of data!",
						":(  you messed up Old Sport. :(",
						Integer.MIN_VALUE);
			}
			
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int columnCount = answer.getMetaData().getColumnCount();
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][columnCount];
			
			while (answer.next())
			{
				for (int col = 0; col < columnCount; col++)
				{
					results[answer.getRow() - 1][col] = answer.getString(col + 1);
				}
			}
			
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		
		
		return results;
	}
	
	/**
	 * Lists all available databases
	 * 
	 * @param tableInfo
	 *            ()
	 * @return results
	 */
	public String[][] tableInfo()
	{
		String[][] results;
		String query = "SHOW DATABASES";
		
		try
		{
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			int rowCount;
			answer.last();
			rowCount = answer.getRow();
			answer.beforeFirst();
			results = new String[rowCount][1];
			
			while (answer.next())
			{
				results[answer.getRow() - 1][0] = answer.getString(1);
			}
			
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		
		return results;
	}
	
	/**
	 * Lists all available databases
	 * 
	 * @param getMetaData
	 *            ()
	 * @return columnInfo
	 */
	public String[] getMetaData()
	{
		String[] columnInfo;
		String query = "SHOW DATABASES";
		
		try
		{
			Statement firstStatement = dadaConnect.createStatement();
			ResultSet answer = firstStatement.executeQuery(query);
			ResultSetMetaData myMeta = answer.getMetaData();
			
			columnInfo = new String[myMeta.getColumnCount()];
			for (int spot = 0; spot < myMeta.getColumnCount(); spot++)
			{
				columnInfo[spot] = myMeta.getColumnName(spot + 1);
			}
			
			answer.close();
			firstStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			columnInfo = new String[] { "no existance" };
			displayErrors(currentSQLError);
		}
		
		return columnInfo;
	}
	
	/**
	 * Gives entries in said table
	 * 
	 * @param describeTable
	 *            ()
	 * @return results
	 */
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
	
	/**
	 * Inserts data in said table
	 * 
	 * @param insertData
	 *            ()
	 * @return rowsAffected
	 */
	public int insertData()
	{
		int rowsAffected = 0;
		String insertQuery = "INSERT INTO `games`.`my_games` " + "(`number_of_players`,`name_of_game`,`platform`) " // Columns
				+ "VALUES (1, 'Kingdom Hearts 3D', 1);";
		
		try
		{
			Statement insertStatement = dadaConnect.createStatement();
			rowsAffected = insertStatement.executeUpdate(insertQuery);
			insertStatement.close();
		}
		catch (SQLException currentSQLError)
		{
			displayErrors(currentSQLError);
		}
		
		return rowsAffected;
	}
}
