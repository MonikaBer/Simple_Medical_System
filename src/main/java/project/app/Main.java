package project.app;

import javax.swing.SwingUtilities;

import project.AppException;
import project.controller.Controller;
import project.model.database.Database;
import project.view.View;


public class Main {

	public static void main(String[] args) {
		
		Runnable program = new Runnable() {
			@Override
			public void run() {
				View view = new View();
				//Model model = new Model();
				try {
					Database database = new Database();
					database.generateData();
					Controller controller = new Controller(view, database);
					view.getMainWindow().addListener(controller);
					view.getPatientsListWindow().addListener(controller);
					view.getNewPatientAdditionWindow().addListener(controller);
					view.getVisitAdditionWindow().addListener(controller);
					view.getMedicalTestResultAdditionWindow().addListener(controller);
					view.getHospitalisationAdditionWindow().addListener(controller);
					view.getDoctorsListWindow().addListener(controller);
				}
				catch (AppException exception) {
					System.out.println(exception.getMsg());
					exception.printStackTrace();
				}
			}
		};
		SwingUtilities.invokeLater(program);
	}
}
