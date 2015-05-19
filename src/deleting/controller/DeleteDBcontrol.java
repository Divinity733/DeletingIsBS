package deleting.controller;

import deleting.view.DeleteGuiFrame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.swing.JOptionPane;

import deleting.model.QueryInfo;

public class DeleteDBcontrol
{
	private DeleteGuiFrame appFrame;
	private DataBaseControl database;
	private ArrayList<QueryInfo> timingInfoList;
	
	/**
	 * Defines objects
	 */
	public DeleteDBcontrol()
	{
		timingInfoList = new ArrayList<QueryInfo>();
		database = new DataBaseControl(this);
		appFrame = new DeleteGuiFrame(this);
	}
	
	/**
	 * Starts the program
	 */
	public void start()
	{
		loadTimeInfo();
	}
	
	/**
	 * Grabs the appFrame
	 * 
	 * @return appFrame
	 */
	public DeleteGuiFrame getAppFrame()
	{
		return appFrame;
	}
	
	/**
	 * Looks into the DataBaseControl.java file
	 * 
	 * @return database
	 */
	public DataBaseControl getDatabase()
	{
		return database;
	}
	
	/**
	 * Looks into the QueryInfo List
	 * 
	 * @return timingInfoList
	 */
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}
	
	/**
	 * Loads the timingInfoList
	 * 
	 */
	public void loadTimeInfo()
	{
		try
		{
			File loadFile = new File("sdfs.save");
			if (loadFile.exists())
			{
				timingInfoList.clear();
				Scanner readFileScanner = new Scanner(loadFile);
				while (readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.nextLine();
					long tempTime = Long.parseLong(readFileScanner.nextLine());
					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				readFileScanner.close();
				JOptionPane.showMessageDialog(getAppFrame(), timingInfoList.size() + " QueryInfo objects has been loaded.");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "There is no file there :(");
			}
		}
		catch (IOException current)
		{
			database.displayErrors(current);
		}
		
	}
	
	/**
	 * Saves the timingInfoList
	 * 
	 */
	public void saveQueryInfo()
	{		
		try
		{
			File saveFile = new File("sdf.save");
			PrintWriter outputWriter = new PrintWriter(saveFile);
			if(saveFile.exists())
			{
				for (QueryInfo current : timingInfoList)
				{
					outputWriter.println(current.getQuery());
					outputWriter.println(current.getQueryTime());
				}
				outputWriter.close();
				JOptionPane.showMessageDialog(getAppFrame(), timingInfoList.size() + " QueryInfo objects has been saved.");
			}
			else
			{
				JOptionPane.showMessageDialog(getAppFrame(), "There is no file there :(");
			}
		}
		catch (FileNotFoundException noExistingFile)
		{
			database.displayErrors(noExistingFile);
		}
	}
}
