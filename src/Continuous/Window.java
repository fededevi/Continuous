package Continuous;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends JFrame {

	int WIDTH = 512;
	int HEIGHT = 512;
	public Window(String string) {
		super(string);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		FixedSizeCanvas panel = new FixedSizeCanvas(WIDTH,HEIGHT);
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		getContentPane().add(panel, BorderLayout.CENTER);
	      //Display the window.
		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8255319694373975038L;

}
