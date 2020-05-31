package project.controller;

import project.Database;
import project.interfaces.ViewListener;
import project.model.Hospitalisation;
import project.model.MedicalTestResult;
import project.model.person.Doctor;
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
    
    public Controller(View view, Database database) throws SQLException {
		this.mainWindow = view.getMainWindow();
		this.patientsListWindow = view.getPatientsListWindow();
		this.newPatientAdditionWindow = view.getNewPatientAdditionWindow();
		this.visitAdditionWindow = view.getVisitAdditionWindow();
		this.medicalTestResultAdditionWindow = view.getMedicalTestResultAdditionWindow();
		this.hospitalisationAdditionWindow = view.getHospitalisationAdditionWindow();
    	this.database = database;

		this.patientsListWindow.loadPatients(this.database.getPatients());
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
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
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
				this.mainWindow.getActionPanel().getScheduledVisitsView().
						setRowSelectedNr(this.mainWindow.getActionPanel().getScheduledVisitsView().
								getTabScheduledVisits().getSelectedRow());
				if(this.mainWindow.getActionPanel().getScheduledVisitsView().getRowSelectedNr() != -1) {
					ScheduledVisit scheduledVisit = this.mainWindow.getActionPanel().getScheduledVisitsView().
							getScheduledVisit();
					this.mainWindow.getActionPanel().getScheduledVisitsView().deleteScheduledVisit();
					database.deleteScheduledVisit(chosenPatient.getPesel(), scheduledVisit.getDate(),
							scheduledVisit.getTime());
				}
			}
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbAdd()) {
				this.medicalTestResultAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbDelete()) {
				//delete this medical test result
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().
						setRowSelectedNr(this.mainWindow.getActionPanel().getMedicalTestsResultsView().
								getTabMedicalTestsResults().getSelectedRow());
				if(this.mainWindow.getActionPanel().getMedicalTestsResultsView().getRowSelectedNr() != -1) {
					MedicalTestResult medicalTestResult = this.mainWindow.getActionPanel().getMedicalTestsResultsView().
							getMedicalTestResult();
					this.mainWindow.getActionPanel().getMedicalTestsResultsView().deleteMedicalTestResult();
					database.deleteMedicalTestResult(chosenPatient.getPesel(), medicalTestResult.getDate(),
							medicalTestResult.getType());
				}
			}
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbDelete()) {
				//delete this archived visit
				this.mainWindow.getActionPanel().getArchivedVisitsView().
						setRowSelectedNr(this.mainWindow.getActionPanel().getArchivedVisitsView().
								getTabArchivedVisits().getSelectedRow());
				if(this.mainWindow.getActionPanel().getArchivedVisitsView().getRowSelectedNr() != -1) {
					ArchivedVisit archivedVisit = this.mainWindow.getActionPanel().getArchivedVisitsView().
							getArchivedVisit();
					this.mainWindow.getActionPanel().getArchivedVisitsView().deleteArchivedVisit();
					database.deleteArchivedVisit(chosenPatient.getPesel(), archivedVisit.getDate(),
							archivedVisit.getDoctor().getPesel());
				}
			}
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbShowDesc()) {
				//show description from this archived visit
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbAdd()) {
				this.hospitalisationAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbDelete()) {
				//delete this hospitalisation
				this.mainWindow.getActionPanel().getHospitalisationsView().
						setRowSelectedNr(this.mainWindow.getActionPanel().getHospitalisationsView().
								getTabHospitalisations().getSelectedRow());
				if(this.mainWindow.getActionPanel().getHospitalisationsView().getRowSelectedNr() != -1) {
					Hospitalisation hospitalisation = this.mainWindow.getActionPanel().getHospitalisationsView().
							getHospitalisation();
					this.mainWindow.getActionPanel().getHospitalisationsView().deleteHospitalisation();
					database.deleteHospitalisation(chosenPatient.getPesel(), hospitalisation.getFrom(),
							hospitalisation.getTo());
				}
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbShowDesc()) {
				//show description from this hospitalisation
			}
		}

		else if (window == this.patientsListWindow) {
			if (source == this.patientsListWindow.getbChoose()) {
				String pesel = this.patientsListWindow.gettPesel().getText();
				if (!pesel.equals("")) {
					if (this.database.ifPatientExist(pesel)) {
						chosenPatient = this.database.getPatient(pesel);
						this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
						this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
						this.patientsListWindow.setVisible(false);
						this.mainWindow.setEnabled(true);
					} else {
						//TODO: add ChooseDialog "Czy chcesz dodać nowego pacjenta?"
						this.newPatientAdditionWindow.setVisible(true);
						this.patientsListWindow.setEnabled(false);
					}
				} else {
					this.patientsListWindow.setRowSelectedNr(this.patientsListWindow.getTabPatientsList().
							getSelectedRow());
					if(this.patientsListWindow.getRowSelectedNr() != -1) {
						chosenPatient = this.patientsListWindow.getPatient();
						this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
						this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
						this.patientsListWindow.setVisible(false);
						this.mainWindow.setEnabled(true);
					}
				}
			}
		}

		else if (window == this.newPatientAdditionWindow) {
			if (source == this.newPatientAdditionWindow.getbSave()) {
				//save new patient data
				String name = this.newPatientAdditionWindow.gettName().getText();
				String surname = this.newPatientAdditionWindow.gettSurname().getText();
				String pesel = this.newPatientAdditionWindow.gettPesel().getText();
				String insurance = this.newPatientAdditionWindow.getInsurance();
				String address = this.newPatientAdditionWindow.gettAddress().getText();
				//TODO: check inscribed data
				chosenPatient = new Patient(name, surname, pesel, insurance, address);
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
				this.patientsListWindow.addPatient(chosenPatient);
				database.addPatient(chosenPatient);
				//clear incribed data in patientsListWindow and newPatientAdditionWindow
				this.patientsListWindow.clear();
				this.newPatientAdditionWindow.clear();
				//
				this.newPatientAdditionWindow.setVisible(false);
				this.patientsListWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.visitAdditionWindow) {
			if (source == this.visitAdditionWindow.getbSave()) {
				String date = this.visitAdditionWindow.gettDate().getText();
				String time = this.visitAdditionWindow.gettTime().getText();
				String type = this.visitAdditionWindow.gettType().getText();
				String doctor = this.visitAdditionWindow.gettDoctor().getText();
				int index = doctor.indexOf(" ");
				String doctorName = doctor.substring(0, index).trim();
				String doctorSurname = doctor.substring(index).trim();
				Double payment = Double.parseDouble(this.visitAdditionWindow.gettPayment().getText());
				//TODO: check inscribed data
				ScheduledVisit scheduledVisit = new ScheduledVisit(date, time, type, new Doctor(doctorName, doctorSurname,
						"", ""), payment);
				//TODO: check if this scheduled visit exist in database
				database.addScheduledVisit(chosenPatient, scheduledVisit);
				this.mainWindow.getActionPanel().getScheduledVisitsView().addScheduledVisit(scheduledVisit);
				//clear incribed data in visitAdditionWindow
				this.visitAdditionWindow.clear();
				//
				this.visitAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.medicalTestResultAdditionWindow) {
			if (source == this.medicalTestResultAdditionWindow.getbSave()) {
				String date = this.medicalTestResultAdditionWindow.gettDate().getText();
				String type = this.medicalTestResultAdditionWindow.gettMedicalTestType().getText();
				String resultTmp = this.medicalTestResultAdditionWindow.gettMedicalTestResult().getText();
				int index = resultTmp.indexOf(" ");
				Double result = Double.parseDouble(resultTmp.substring(0, index).trim());
				String units = resultTmp.substring(index).trim();
				//TODO: check inscribed data
				MedicalTestResult medicalTestResult = new MedicalTestResult(date, type, result, units);
				//TODO: check if this medical test result exist in database
				database.addMedicalTestResult(chosenPatient, medicalTestResult);
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().addMedicalTestResult(medicalTestResult);
				//clear incribed data in medicalTestResultAdditionWindow
				this.medicalTestResultAdditionWindow.clear();
				//
				this.medicalTestResultAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.hospitalisationAdditionWindow) {
			if (source == this.hospitalisationAdditionWindow.getbSave()) {
				String from = this.hospitalisationAdditionWindow.gettFrom().getText();
				String to = this.hospitalisationAdditionWindow.gettTo().getText();
				String reason = this.hospitalisationAdditionWindow.gettReason().getText();
				//TODO: check inscribed data
				Hospitalisation hospitalisation = new Hospitalisation(from, to, reason);
				//TODO: check if this hospitalisation exist in database
				database.addHospitalisation(chosenPatient, hospitalisation);
				this.mainWindow.getActionPanel().getHospitalisationsView().addHospitalisation(hospitalisation);
				//clear incribed data in hospitalisationAdditionWindow
				this.hospitalisationAdditionWindow.clear();
				//
				this.hospitalisationAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}
	}
}
