package operation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author Zac
 *
 */
public class ExitButtonListener implements ActionListener {

	private JFrame target;
	
	public ExitButtonListener(JFrame target) {
		this.setWindow(target);
	}

	public void setWindow(JFrame target) {
		this.target = target;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Dispose the target window when ActionListener is triggered
		this.target.dispose();
	}

}
