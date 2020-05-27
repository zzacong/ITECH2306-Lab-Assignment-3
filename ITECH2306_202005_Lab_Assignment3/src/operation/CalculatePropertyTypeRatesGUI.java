/**
 * 
 */
package operation;

import java.awt.event.*;
import java.util.Scanner;

/**
 * @author Zac
 *
 */
public class CalculatePropertyTypeRatesGUI extends FunctionalDialog {

	private static final int MAX_NO_USER_INPUTS = 1;
	boolean run = true;
	
	public CalculatePropertyTypeRatesGUI(Scanner console) {
		super(MAX_NO_USER_INPUTS, console);
	}

	@Override
	protected void obtainInput(int i) {
		switch(i) {
		case 0:
			// Use an IF statement to ensure the window is only created once, then wait until the window is disposed 
			if(run) {
				run = false;
				CalculatePropertyTypeRatesWindow window = new CalculatePropertyTypeRatesWindow();
				window.createAndShowWindow();
				window.addWindowListener(new WindowListener() {
		
					@Override
					public void windowOpened(WindowEvent e) {						
					}
		
					@Override
					public void windowClosing(WindowEvent e) {
						// Was meant to move back to MainMenu when window is closed from the window's system menu
						// The window's DefaultCloseOperation is set to DISPOSE_ON_CLOSE, so it doesn't matter at the moment
						// But I'll still leave it here
						setStillRunning(false);
						run = true;
					}
		
					@Override
					public void windowClosed(WindowEvent e) {
						// Move back to MainMenu when window is Disposed
						setStillRunning(false);
						run = true;
					}
		
					@Override
					public void windowIconified(WindowEvent e) {						
					}
		
					@Override
					public void windowDeiconified(WindowEvent e) {						
					}
		
					@Override
					public void windowActivated(WindowEvent e) {						
					}
		
					@Override
					public void windowDeactivated(WindowEvent e) {						
					}
					
				});
			}
		}
	}

	@Override
	protected void respondToInput() {
		
	}

	
}
