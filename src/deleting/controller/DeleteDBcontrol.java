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
	
	public ArrayList<QueryInfo> getTimingInfoList()
	{
		return timingInfoList;
	}
	
	private void loadTimeInfo()
	{
		File saveFile = new File("C:/Users/blit1703/Documents/sdfs.txt");
		try
		{
			timingInfoList.clear();
			Scanner readFileScanner;
			if (saveFile.exists())
			{
				readFileScanner = new Scanner(saveFile);
				while (readFileScanner.hasNext())
				{
					String tempQuery = readFileScanner.nextLine();
					readFileScanner.next();
					long tempTime = readFileScanner.nextLong();
					timingInfoList.add(new QueryInfo(tempQuery, tempTime));
				}
				readFileScanner.close();
			}
		}
		catch (IOException current)
		{
			this.getDatabase().displayErrors(current);
		}
		
	}
	
	public void saveQueryInfo()
	{
		String fileName = "C:/Users/blit1703/Documents/sdfs.txt";
		ArrayList<QueryInfo> output = getTimingInfoList();
		PrintWriter outputWriter;
		
		try
		{
			outputWriter = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
			for (QueryInfo current : output)
			{
				outputWriter.write(current.getQuery());
				outputWriter.write(current.getQueryTime() + "\n");
			}
			outputWriter.close();
		}
		catch (FileNotFoundException noExistingFile)
		{
			JOptionPane.showMessageDialog(appFrame, "There is no file there :(");
			JOptionPane.showMessageDialog(appFrame, noExistingFile.getMessage());
		}
		catch (IOException inputOutputError)
		{
			JOptionPane.showMessageDialog(appFrame, "There is no file there :(");
			JOptionPane.showMessageDialog(appFrame, inputOutputError.getMessage());
		}
	}
}
