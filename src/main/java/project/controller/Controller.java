package project.controller;

import project.Database;
import project.interfaces.ViewListener;
import project.model.person.Patient;
import project.model.visit.ArchivedVisit;
import project.model.visit.ScheduledVisit;
import project.view.View;
import project.view.windows.mainWindow.MainWindow;
import project.view.windows.otherWindows.*;

import javax.swing.*;
import java.sql.SQLException;


public class Controller implements ViewListener {

	private MainWindow mainWindow = null;
	private PatientsListWindow patientsListWindow = null;
	private NewPatientAdditionWindow newPatientAdditionWindow = null;
	private VisitAdditionWindow visitAdditionWindow = null;
	private MedicalTestResultAdditionWindow medicalTestResultAdditionWindow = null;
	private HospitalisationAdditionWindow hospitalisationAdditionWindow = null;
    private Database database = null;
    private Patient chosenPatient = null;
    
    public Controller(View view, Database database) {
		this.mainWindow = view.getMainWindow();
		this.patientsListWindow = view.getPatientsListWindow();
		this.newPatientAdditionWindow = view.getNewPatientAdditionWindow();
		this.visitAdditionWindow = view.getVisitAdditionWindow();
		this.medicalTestResultAdditionWindow = view.getMedicalTestResultAdditionWindow();
		this.hospitalisationAdditionWindow = view.getHospitalisationAdditionWindow();
    	this.database = database;
    }

	@Override
	public void viewChanged(JFrame window, Object source) throws SQLException {
		if (window == this.mainWindow) {
			if (source == this.mainWindow.getMenuPanel().getMClose()) {
				this.mainWindow.dispose();
			}
			else if (source == this.mainWindow.getMenuPanel().getMPatientsList()) {
				this.patientsListWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbPersonalData()) {
				this.mainWindow.getActionPanel().setPersonalDataViewVisibility(true);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbScheduledVisits()) {
				this.mainWindow.getActionPanel().setScheduledVisitsViewVisibility(true);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbMedicalTestsResults()) {
				this.mainWindow.getActionPanel().setMedicalTestsResultsViewVisibility(true);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbArchivedVisits()) {
				this.mainWindow.getActionPanel().setArchivedVisitsViewVisibility(true);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbHospitalisations()) {
				this.mainWindow.getActionPanel().setHospitalisationsViewVisibility(true);
			}
			else if (source == this.mainWindow.getActionPanel().getPersonalDataView().getbSave()) {
				//save patient's data
				chosenPatient.setName(this.mainWindow.getActionPanel().getPersonalDataView().gettName().getText());
				chosenPatient.setSurname(this.mainWindow.getActionPanel().getPersonalDataView().gettSurname().getText());
				chosenPatient.setPesel(this.mainWindow.getActionPanel().getPersonalDataView().gettPesel().getText());
				chosenPatient.setInsurance(this.mainWindow.getActionPanel().getPersonalDataView().getInsurance());
				chosenPatient.setAddress(this.mainWindow.getActionPanel().getPersonalDataView().gettAddress().getText());
				if (database.ifPatientExist(chosenPatient.getPesel())) {
					database.deletePatient(chosenPatient.getPesel());
				}
				database.addPatient(chosenPatient);
			}
			else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbArchive()) {
				//archive this visit
				this.mainWindow.getActionPanel().getScheduledVisitsView().
						setRowSelectedNr(this.mainWindow.getActionPanel().getScheduledVisitsView().
								getTabScheduledVisits().getSelectedRow());
				if(this.mainWindow.getActionPanel().getScheduledVisitsView().getRowSelectedNr() != -1) {
					ScheduledVisit scheduledVisit = this.mainWindow.getActionPanel().getScheduledVisitsView().
							getScheduledVisit();
					database.deleteScheduledVisit(chosenPatient.getPesel(), scheduledVisit.getDate(),
							scheduledVisit.getTime());
					this.mainWindow.getActionPanel().getScheduledVisitsView().deleteScheduledVisit();
					ArchivedVisit archivedVisit = new ArchivedVisit(scheduledVisit.getDate(), scheduledVisit.getType(),
							scheduledVisit.getDoctor(), "");
					database.addArchivedVisit(chosenPatient, archivedVisit);
					this.mainWindow.getActionPanel().getArchivedVisitsView().addArchivedVisit(archivedVisit);
				}
			}
			else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbAdd()) {
				this.visitAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbDelete()) {
				//delete this visit
			}
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbAdd()) {
				this.medicalTestResultAdditionWindow.setVisible(true);
			}
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbDelete()) {
				//delete this medical test result
			}
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbDelete()) {
				//delete this archived visit
			}
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbShowDesc()) {
				//show description from this archived visit
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbAdd()) {
				this.hospitalisationAdditionWindow.setVisible(true);
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbDelete()) {
				//delete this hospitalisation
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbShowDesc()) {
				//show description from this hospitalisation
			}
		}

		else if (window == this.patientsListWindow) {
			if (source == this.patientsListWindow.getbChoose()) {
				//TODO: add ChooseDialog
				this.newPatientAdditionWindow.setVisible(true);
				this.patientsListWindow.setEnabled(false);
			}
		}

		else if (window == this.newPatientAdditionWindow) {
			if (source == this.newPatientAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in newPatientAdditionWindow and patientsListWindow
				this.newPatientAdditionWindow.setVisible(false);
				this.patientsListWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.visitAdditionWindow) {
			if (source == this.visitAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.visitAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.medicalTestResultAdditionWindow) {
			if (source == this.medicalTestResultAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.medicalTestResultAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.hospitalisationAdditionWindow) {
			if (source == this.hospitalisationAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.hospitalisationAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}
	}
}
