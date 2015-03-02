package deleting.controller;

import deleting.view.DeleteGuiFrame;

public class DeleteDBcontrol
{
	private DeleteGuiFrame appFrame;
	private DataBaseControl database;
	
	public DeleteDBcontrol()
	{
		database = new DataBaseControl(this);
		appFrame = new DeleteGuiFrame(this);
	}
	
	public void start()
	{
		
	}
	
	public DeleteGuiFrame getAppFrame()
	{
		return appFrame;
	}
	
	public DataBaseControl getDatabase()
	{
		return database;
	}
}
