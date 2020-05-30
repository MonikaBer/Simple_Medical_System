package project.app;

import javax.swing.SwingUtilities;

import project.Database;
import project.controller.Controller;
import project.model.person.Patient;
import project.view.View;

import java.sql.SQLException;


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
				view.getPatientsListWindow().addListener(controller);
				view.getNewPatientAdditionWindow().addListener(controller);
				view.getVisitAdditionWindow().addListener(controller);
				view.getMedicalTestResultAdditionWindow().addListener(controller);
				view.getHospitalisationAdditionWindow().addListener(controller);

				//database tests
				try {
					Database database = new Database();
//					database.addPatient(new Patient("Filip", "Kalski", "11111111111",
//							"NFZ", "ul. Komputerowa, Warszawa"));
//					database.addPatient(new Patient("Aleksandra", "Kowalska", "22222222222",
//							"Prywatne", "ul. Krakowska 33 Poznań"));
//					database.printPatients();
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
			}
		};
		SwingUtilities.invokeLater(program);
	}
}
