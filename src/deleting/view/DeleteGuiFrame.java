package deleting.view;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import deleting.controller.DeleteDBcontrol;
import deleting.model.QueryInfo;

public class DeleteGuiFrame extends JFrame
{
	private DeleteGuiPanel basePanel;
	private DeleteDBcontrol baseController;
	
	public DeleteGuiFrame(DeleteDBcontrol baseController)
	{
		this.baseController = baseController;
		basePanel = new DeleteGuiPanel(baseController);
		
		setupFrame();
		setupListeners();
	}
	
	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setSize(1000, 1000);
		this.setVisible(true);
	}
	
	private void setupListeners()
	{
		this.addWindowListener(new WindowListener()
		{

			@Override
			public void windowActivated(
					WindowEvent arg0)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(
					WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(
					WindowEvent e)
			{
				baseController.saveQueryInfo();
				
			}

			@Override
			public void windowDeactivated(
					WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(
					WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(
					WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(
					WindowEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
