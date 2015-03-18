package deleting.controller;

import deleting.view.DeleteGuiFrame;

public class DeleteDBcontrol
{
	private DeleteGuiFrame appFrame;
	private DataBaseControl database;
	
	/**
	 * Defines objects
	 */
	public DeleteDBcontrol()
	{
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
	 * @return appFrame
	 */
	public DeleteGuiFrame getAppFrame()
	{
		return appFrame;
	}
	
	/**
	 * Looks into the DataBaseControl.java file
	 * @return database
	 */
	public DataBaseControl getDatabase()
	{
		return database;
	}
}
