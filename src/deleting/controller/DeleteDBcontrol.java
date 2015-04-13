package deleting.controller;

import deleting.view.DeleteGuiFrame;
import java.util.*;
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
}
