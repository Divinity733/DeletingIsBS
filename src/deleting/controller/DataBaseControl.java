package deleting.controller;

import java.sql.*;

import javax.swing.JOptionPane;

import deleting.controller.DeleteDBcontrol;
import deleting.model.QueryInfo;

public class DataBaseControl
{
	private String connectionString;
	private Connection dadaConnect;
	private DeleteDBcontrol baseController;
	private String currentQuery;
	
	/**
	 * Defines objects
	 * 
	 * @param baseController
	 */
	public DataBaseControl(DeleteDBcontrol baseController)
	{
		String pathToDBServer = "localhost";
		String databaseName = "games";
		String userName = "root";
		String password = "";
		
		this.baseController = baseController;
		checkDriver();
		connectionStringBuilder(pathToDBServer, databaseName, userName, password);
		setupConnection();
	}
	
	/**
	 * connectionStringBuilder() makes the whole URL instead of one line of code and it makes getting DBs more accessible.
	 * 
	 * @param pathToDBServer
	 * @param databaseName
	 * @param userName
	 * @param password
	 */
	public void connectionStringBuilder(
			String pathToDBServer, String databaseName, String userName, String password)
	{
		connectionString = "jdbc:mysql://";
		connectionString += pathToDBServer;
		connectionString += "/" + databaseName;
		connectionString += "?user=" + userName;
		connectionString += "&password=" + password;
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
	
	/**
	 * Closes the connection to DB
	 * 
	 * @param closeConnection
	 *            () NOT SURE WHEN TO USE YET!!!
	 */
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
	
	/**
	 * Displays the errors if an error is found.
	 * 
	 * @param currentException
	 */
	public void displayErrors(
			Exception currentException)
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;
	}
	
	/**
	 * Checks if statements are correct.
	 * 
	 * @return true/false
	 */
	private boolean checkDataViolation()
	{
		if (currentQuery.toUpperCase().contains(" DROP ") || currentQuery.toUpperCase().contains(" TRUNCATE ") || currentQuery.toUpperCase().contains(" SET ") || currentQuery.toUpperCase().contains(" ALTER "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Checks if the structure is correct.
	 * 
	 * @return true/false
	 */
	private boolean checkStructureViolation()
	{
		if (currentQuery.toUpperCase().contains(" DATABASE ") || currentQuery.toUpperCase().contains(" TABLE "))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Allows the use of DROP Statements
	 * 
	 * @param dropStatement
	 *            ()
	 */
	public void dropStatement()
	{
		String results;
		try
		{
			if (checkStructureViolation())
			{
				throw new SQLException("no dropping dbs!", ":(  you messed up Old Sport. :(", Integer.MIN_VALUE);
			}
			
			if (currentQuery.toUpperCase().contains(" INDEX "))
			{
				results = "The index was ";
			}
			else
			{
				results = "The table was ";
			}
			
			Statement dropStatement = dadaConnect.createStatement();
			int affected = dropStatement.executeUpdate(currentQuery);
			
			dropStatement.close();
			
			if (affected == 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch (SQLException dropError)
		{
			displayErrors(dropError);
		}
	}
	
	/**
	 * Allows the use of ALTER Statements
	 * 
	 * @param alterStatement
	 *            ()
	 */
	public void alterStatement()
	{
		String results;
		try
		{
			if (checkStructureViolation())
			{
				throw new SQLException("no dropping dbs!", ":(  you messed up Old Sport. :(", Integer.MIN_VALUE);
			}
			
			if (currentQuery.toUpperCase().contains(" ADD "))
			{
				results = "The added amount of columns was ";
			}
			else if (currentQuery.toUpperCase().contains(" DROP COLUMN "))
			{
				results = "Dropped column";
			}
			else if (currentQuery.toUpperCase().contains(" DROP INDEX "))
			{
				results = "Dropped index";
			}
			else
			{
				results = "dropped";
			}
			
			Statement alterStatement = dadaConnect.createStatement();
			int affected = alterStatement.executeUpdate(currentQuery);
			
			alterStatement.close();
			
			if (affected != 0)
			{
				results += "dropped";
			}
			JOptionPane.showMessageDialog(baseController.getAppFrame(), results);
		}
		catch (SQLException dropError)
		{
			displayErrors(dropError);
		}
	}
	
	/**
	 * Helps find specific data in said DB
	 * 
	 * @param selectQueryResults
	 *            ()
	 * @return results
	 */
	public String[][] selectQueryResults(
			String query)
	{
		this.currentQuery = query;
		String[][] results;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
		try
		{
			if (checkDataViolation())
			{
				throw new SQLException("illegal modification of data!", ":(  you messed up Old Sport. :(", Integer.MIN_VALUE);
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return results;
	}
	
	/**
	 * Lists all available databases/tables
	 * 
	 * @param tableInfo
	 *            ()
	 * @return results
	 */
	public String[][] tableInfo()
	{
		String[][] results;
		String query = "SHOW DATABASES";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			results = new String[][] { { "problem occurred :(" } };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			columnInfo = new String[] { "no existance" };
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
		return columnInfo;
	}
	
	/**
	 * Lists all available databases
	 * 
	 * @param getDatabaseColumnNames
	 *            (String tableName)
	 * @return columnInfo
	 */
	public String[] getDatabaseColumnNames(String tableName)
	{
		String[] columnInfo;
		String query = "SHOW * FROM `" + tableName + "`";
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			columnInfo = new String[] { "no existance" };
			displayErrors(currentSQLError);
		}
		
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(query, queryTime));
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
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		
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
			endTime = System.currentTimeMillis();
		}
		catch (SQLException currentSQLError)
		{
			endTime = System.currentTimeMillis();
			displayErrors(currentSQLError);
		}
		baseController.getTimingInfoList().add(new QueryInfo(query, endTime - startTime));
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
	
	/**
	 * Updates query info.
	 * 
	 * @param submitQuery
	 *            (String currentQuery)
	 */
	public void submitQuery(String currentQuery)
	{
		this.currentQuery = currentQuery;
		long startTime, endTime;
		startTime = System.currentTimeMillis();
		endTime = 0;
		if(!checkDataViolation())
		{
			try
			{
				Statement submitStatement = dadaConnect.createStatement();
				submitStatement.executeUpdate(currentQuery);
				submitStatement.close();
				endTime = System.currentTimeMillis();
			}
			catch (SQLException currentSQLError)
			{
				endTime = System.currentTimeMillis();
				displayErrors(currentSQLError);
			}
		}
		long queryTime = endTime - startTime;
		baseController.getTimingInfoList().add(new QueryInfo(currentQuery, queryTime));
	}
}
