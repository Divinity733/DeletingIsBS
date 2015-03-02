package deleting.view;

import javax.swing.JFrame;

import deleting.controller.DeleteDBcontrol;

public class DeleteGuiFrame extends JFrame
{
	private DeleteGuiPanel basePanel;
	
	public DeleteGuiFrame(DeleteDBcontrol baseController)
	{
		basePanel = new DeleteGuiPanel(baseController);
		
		setupFrame();
	}
	
	private void setupFrame()
	{
		this.setContentPane(basePanel);
		this.setSize(1000, 1000);
		this.setVisible(true);
	}
}
