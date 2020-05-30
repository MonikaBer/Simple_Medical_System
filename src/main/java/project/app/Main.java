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
				try {
					Database database = new Database();
					Controller controller = new Controller(view, database);
					view.getMainWindow().addListener(controller);
					view.getPatientsListWindow().addListener(controller);
					view.getNewPatientAdditionWindow().addListener(controller);
					view.getVisitAdditionWindow().addListener(controller);
					view.getMedicalTestResultAdditionWindow().addListener(controller);
					view.getHospitalisationAdditionWindow().addListener(controller);
				}
				catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}

				//database tests
//				try {
//					Database database = new Database();
//
//					//INSERT_AND_DELETE_PATIENT_TEST
////					database.addPatient(new Patient("Filip", "Kalski", "11111111111",
////							"NFZ", "ul. Komputerowa, Warszawa"));
////					database.addPatient(new Patient("Aleksandra", "Kowalska", "22222222222",
////							"Prywatne", "ul. Krakowska 33 Poznań"));
//
//					//database.deletePatient("11111111111");
//					//database.deletePatient("22222222222");
//					//database.printPatients();
//				} catch (ClassNotFoundException | SQLException e) {
//					e.printStackTrace();
//				}
			}
		};
		SwingUtilities.invokeLater(program);
	}
}
