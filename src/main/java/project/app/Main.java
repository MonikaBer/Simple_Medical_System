package project.app;

import javax.swing.SwingUtilities;

import project.controller.Controller;
import project.view.View;


public class Main {

	public static void main(String[] args) {
		
		Runnable program = new Runnable() {
			@Override
			public void run() {
				View view = new View();
				//Model model = new Model();
				//Controller controller = new Controller(view, model);
				Controller controller = new Controller(view);
				view.getMainWindow().addListener(controller);
			}	
		};
		SwingUtilities.invokeLater(program);
	}
}
