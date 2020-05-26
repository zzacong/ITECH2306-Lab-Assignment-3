package operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Zac
 *
 */
public class ExitButtonListener implements ActionListener{

	private JFrame window;
	
	public ExitButtonListener(JFrame window) {
		this.setWindow(window);
	}

	public void setWindow(JFrame window) {
		this.window = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.window.dispose();
	}

}
