package project.controller;

import project.AppException;
import project.DataParsingException;
import project.Helper;
import project.interfaces.*;
import project.model.database.Database;
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
import java.util.ArrayList;

public class Controller implements DoctorsListWindowListener, PatientsListWindowListener,
		MedicalTestResultAdditionWindowListener, VisitAdditionWindowListener, NewPatientAdditionWindowListener,
		HospitalisationAdditionWindowListener, MainWindowListener {

	private MainWindow mainWindow = null;
	private PatientsListWindow patientsListWindow = null;
	private NewPatientAdditionWindow newPatientAdditionWindow = null;
	private VisitAdditionWindow visitAdditionWindow = null;
	private MedicalTestResultAdditionWindow medicalTestResultAdditionWindow = null;
	private HospitalisationAdditionWindow hospitalisationAdditionWindow = null;
	private DoctorsListWindow doctorsListWindow = null;
    private Database database = null;
    private Patient chosenPatient = null;
    private Doctor chosenDoctor = null;

    public Controller(View view, Database database) throws AppException {
		this.mainWindow = view.getMainWindow();
		this.patientsListWindow = view.getPatientsListWindow();
		this.newPatientAdditionWindow = view.getNewPatientAdditionWindow();
		this.visitAdditionWindow = view.getVisitAdditionWindow();
		this.medicalTestResultAdditionWindow = view.getMedicalTestResultAdditionWindow();
		this.hospitalisationAdditionWindow = view.getHospitalisationAdditionWindow();
		this.doctorsListWindow = view.getDoctorsListWindow();
    	this.database = database;

    	this.doctorsListWindow.loadDoctors(this.database.getDoctors());
    }



	@Override
	public void mainWindowChanged(MainWindow mainWindow, Object source) throws AppException {
		if (source == this.mainWindow.getMenuPanel().getMClose()) {		//close main window
			this.mainWindow.dispose();
		}
		else if (source == this.mainWindow.getMenuPanel().getMPatientsList()) {		//show list of patients
			ArrayList<Patient> patients = database.getPatients();
			this.patientsListWindow.loadPatients(patients);
			this.mainWindow.setEnabled(false);
			this.patientsListWindow.setVisible(true);
			this.patientsListWindow.setEnabled(true);
		}
		else if (source == this.mainWindow.getSelectionPanel().getbPersonalData()) {	//show patient's personal data
			if (chosenPatient != null) {
				this.mainWindow.getActionPanel().setPersonalDataViewVisibility(true);
				Patient patient = database.getPatient(chosenPatient.getPesel());
				this.mainWindow.getActionPanel().getPersonalDataView().loadPatientPersonalData(patient);
			}
		}
		else if (source == this.mainWindow.getSelectionPanel().getbScheduledVisits()) {	//show patient's scheduled visits
			if (chosenPatient != null) {
				this.mainWindow.getActionPanel().setScheduledVisitsViewVisibility(true);
				ArrayList<ScheduledVisit> scheduledVisits =
						database.getPatientScheduledVisits(chosenPatient.getPesel());
				this.mainWindow.getActionPanel().getScheduledVisitsView().
						loadPatientScheduledVisits(scheduledVisits);
			}
		}
		else if (source == this.mainWindow.getSelectionPanel().getbMedicalTestsResults()) {	//show patient's medical tests results
			if (chosenPatient != null) {
				this.mainWindow.getActionPanel().setMedicalTestsResultsViewVisibility(true);
				ArrayList<MedicalTestResult> medicalTestsResults =
						database.getPatientMedicalTestResults(chosenPatient.getPesel());
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().
						loadPatientMedicalTestsResults(medicalTestsResults);
			}
		}
		else if (source == this.mainWindow.getSelectionPanel().getbArchivedVisits()) { //show patient's archived visits
			if (chosenPatient != null) {
				this.mainWindow.getActionPanel().setArchivedVisitsViewVisibility(true);
				ArrayList<ArchivedVisit> archivedVisits =
						database.getPatientArchivedVisits(chosenPatient.getPesel());
				this.mainWindow.getActionPanel().getArchivedVisitsView().
						loadPatientArchivedVisits(archivedVisits);
			}
		}
		else if (source == this.mainWindow.getSelectionPanel().getbHospitalisations()) { //show patient's hospitalisations
			if (chosenPatient != null) {
				this.mainWindow.getActionPanel().setHospitalisationsViewVisibility(true);
				ArrayList<Hospitalisation> hospitalisations =
						database.getPatientHospitalisations(chosenPatient.getPesel());
				this.mainWindow.getActionPanel().getHospitalisationsView().
						loadPatientHospitalisations(hospitalisations);
			}
		}
		else if (source == this.mainWindow.getActionPanel().getPersonalDataView().getbSave()) { //save patient's personal data
			try {
				Patient newPatientData = this.mainWindow.getActionPanel().getPersonalDataView().getPatient();
				if (!newPatientData.getPesel().equals(chosenPatient.getPesel()) &&
						database.ifPatientExists(newPatientData.getPesel())) {
					Helper.showWarningDialog(this.mainWindow, "W bazie już istnieje inny pacjent o tym samym PESELU");
					return;
				}
				database.updatePatient(newPatientData);
				chosenPatient = newPatientData;
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				//this.mainWindow.getActionPanel().getPersonalDataView().loadPatientPersonalData(chosenPatient);
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.mainWindow, "Błędne dane");
				return;
			}
		}
		else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbArchive()) {	//archive scheduled visit
			this.mainWindow.getActionPanel().getScheduledVisitsView().
					setRowSelectedNr(this.mainWindow.getActionPanel().getScheduledVisitsView().
							getTabScheduledVisits().getSelectedRow());
			if(this.mainWindow.getActionPanel().getScheduledVisitsView().getRowSelectedNr() != -1) {
				ScheduledVisit scheduledVisit = this.mainWindow.getActionPanel().getScheduledVisitsView().
						getScheduledVisit();
				this.mainWindow.getActionPanel().getScheduledVisitsView().deleteScheduledVisit();
				ArchivedVisit newArchivedVisit = new ArchivedVisit(scheduledVisit.getDate(),
						scheduledVisit.getType(),scheduledVisit.getDoctor(), "");
				if (!database.addArchivedVisit(chosenPatient.getPesel(), newArchivedVisit, scheduledVisit)) {
					Helper.showWarningDialog(this.mainWindow, "Wizyta usunięta z planowanych wizyt, natomiast " +
							"nie udało się jej zarchiwizować, bo w archiwum istniała już taka wizyta");
				} else {
					this.mainWindow.getActionPanel().getArchivedVisitsView().addArchivedVisit(newArchivedVisit);
				}
				database.deleteScheduledVisit(chosenPatient.getPesel(), scheduledVisit.getDate(),
						scheduledVisit.getTime());
			}
		}
		else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbAdd()) {	//add new scheduled visit
			chosenDoctor= null;
			this.mainWindow.setEnabled(false);
			this.visitAdditionWindow.clear();
			this.visitAdditionWindow.setVisible(true);
		}
		else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbDelete()) {	//delete scheduled visit
			this.mainWindow.getActionPanel().getScheduledVisitsView().
					setRowSelectedNr(this.mainWindow.getActionPanel().getScheduledVisitsView().
							getTabScheduledVisits().getSelectedRow());
			if (this.mainWindow.getActionPanel().getScheduledVisitsView().getRowSelectedNr() != -1) {
				ScheduledVisit scheduledVisit = this.mainWindow.getActionPanel().getScheduledVisitsView().
						getScheduledVisit();
				this.mainWindow.getActionPanel().getScheduledVisitsView().deleteScheduledVisit();
				database.deleteScheduledVisit(chosenPatient.getPesel(), scheduledVisit.getDate(),
						scheduledVisit.getTime());
			}
		}
		else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbAdd()) {  //add new medical test result
			this.mainWindow.setEnabled(false);
			this.medicalTestResultAdditionWindow.clear();
			this.medicalTestResultAdditionWindow.setVisible(true);
		}
		else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbDelete()) {  //delete medical test result
			this.mainWindow.getActionPanel().getMedicalTestsResultsView().
					setRowSelectedNr(this.mainWindow.getActionPanel().getMedicalTestsResultsView().
							getTabMedicalTestsResults().getSelectedRow());
			if (this.mainWindow.getActionPanel().getMedicalTestsResultsView().getRowSelectedNr() != -1) {
				MedicalTestResult medicalTestResult = this.mainWindow.getActionPanel().
						getMedicalTestsResultsView().getMedicalTestResult();
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().deleteMedicalTestResult();
				database.deleteMedicalTestResult(chosenPatient.getPesel(), medicalTestResult.getDate(),
						medicalTestResult.getType());
			}
		}
		else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbDelete()) {	//delete archived visit
			this.mainWindow.getActionPanel().getArchivedVisitsView().
					setRowSelectedNr(this.mainWindow.getActionPanel().getArchivedVisitsView().
							getTabArchivedVisits().getSelectedRow());
			if (this.mainWindow.getActionPanel().getArchivedVisitsView().getRowSelectedNr() != -1) {
				ArchivedVisit archivedVisit = this.mainWindow.getActionPanel().getArchivedVisitsView().
						getArchivedVisit();
				this.mainWindow.getActionPanel().getArchivedVisitsView().deleteArchivedVisit();
				database.deleteArchivedVisit(chosenPatient.getPesel(), archivedVisit.getDate(),
						archivedVisit.getDoctor().getPesel());
			}
		}
		else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbAdd()) {	//add hospitalisation
			this.mainWindow.setEnabled(false);
			this.hospitalisationAdditionWindow.clear();
			this.hospitalisationAdditionWindow.setVisible(true);
		}
		else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbDelete()) {	//delete hospitalisation
			this.mainWindow.getActionPanel().getHospitalisationsView().
					setRowSelectedNr(this.mainWindow.getActionPanel().getHospitalisationsView().
							getTabHospitalisations().getSelectedRow());
			if (this.mainWindow.getActionPanel().getHospitalisationsView().getRowSelectedNr() != -1) {
				Hospitalisation hospitalisation = this.mainWindow.getActionPanel().getHospitalisationsView().
						getHospitalisation();
				this.mainWindow.getActionPanel().getHospitalisationsView().deleteHospitalisation();
				database.deleteHospitalisation(chosenPatient.getPesel(), hospitalisation.getFrom(),
						hospitalisation.getTo());
			}
		}
	}




	@Override
	public void doctorsListWindowChanged(DoctorsListWindow doctorsListWindow, Object source) throws AppException {
		if (source == this.doctorsListWindow.getbOk()) {	//choose doctor for visit
			this.doctorsListWindow.setRowSelectedNr(this.doctorsListWindow.getTabDoctorsList().getSelectedRow());
			if (this.doctorsListWindow.getRowSelectedNr() != -1) {
				chosenDoctor = this.doctorsListWindow.getDoctor();
				this.doctorsListWindow.setVisible(false);
				this.visitAdditionWindow.setEnabled(true);
				this.visitAdditionWindow.settDoctorPesel(chosenDoctor.getPesel());
			}
		}
	}



	@Override
	public void hospitalisationAdditionWindowChanged(HospitalisationAdditionWindow hospitalisationAdditionWindow,
													 Object source) throws AppException {

		if (source == this.hospitalisationAdditionWindow.getbSave()) {	//save new hospitalisation
			Hospitalisation hospitalisation = null;
			try {
				hospitalisation = this.hospitalisationAdditionWindow.getNewHospitalisation();
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.hospitalisationAdditionWindow,
						"Niepoprawna kolejność dat");
				return;
			}
			if (this.database.ifHospitalisationExists(chosenPatient.getPesel(), hospitalisation.getFrom(),
					hospitalisation.getTo())) {
				Helper.showWarningDialog(this.hospitalisationAdditionWindow,
						"Nie można dodać takiej hospitalizacji, bo podobna już istnieje w bazie");
				return;
			}
			this.hospitalisationAdditionWindow.clear();
			this.hospitalisationAdditionWindow.setVisible(false);
			this.mainWindow.setEnabled(true);
			this.database.addHospitalisation(chosenPatient.getPesel(), hospitalisation);
			this.mainWindow.getActionPanel().getHospitalisationsView().addHospitalisation(hospitalisation);
		} else if (source == this.hospitalisationAdditionWindow.getbDiscard()) {	//dicard changes
			this.hospitalisationAdditionWindow.clear();
			this.hospitalisationAdditionWindow.setVisible(false);
			this.mainWindow.setEnabled(true);
		}
	}



	@Override
	public void medicalTestResultAdditionWindowChanged(MedicalTestResultAdditionWindow medicalTestResultAdditionWindow,
													   Object source) throws AppException {

		if (source == this.medicalTestResultAdditionWindow.getbSave()) {  //add new medical test result
			MedicalTestResult medicalTestResult = null;
			try {
				medicalTestResult = this.medicalTestResultAdditionWindow.getNewMedicalTestResult();
				if (this.database.ifMedicalTestResultExists(chosenPatient.getPesel(), medicalTestResult.getDate(),
						medicalTestResult.getType())) {
					Helper.showWarningDialog(this.medicalTestResultAdditionWindow,
							"Nie można dodać takiego badania, bo podobne już jest w bazie");
					return;
				}
				this.medicalTestResultAdditionWindow.clear();
				this.medicalTestResultAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.database.addMedicalTestResult(chosenPatient.getPesel(), medicalTestResult);
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().
						addMedicalTestResult(medicalTestResult);
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.medicalTestResultAdditionWindow, "Błędne dane");
				return;
			}
		} else if (source == this.medicalTestResultAdditionWindow.getbDiscard()) {  //dicard changes
			this.medicalTestResultAdditionWindow.clear();
			this.medicalTestResultAdditionWindow.setVisible(false);
			this.mainWindow.setEnabled(true);
		}
	}



	@Override
	public void newPatientAdditionWindowChanged(NewPatientAdditionWindow newPatientAdditionWindow, Object source)
			throws AppException {

		if (source == this.newPatientAdditionWindow.getbSave()) {  //add new patient
			Patient patient = null;
			try {
				patient = this.newPatientAdditionWindow.getNewPatient();
				if (this.database.ifPatientExists(patient.getPesel())) {
					Helper.showWarningDialog(this.newPatientAdditionWindow,
							"Pacjent o takim PESELU już istnieje w bazie");
					return;
				}
				chosenPatient = patient;
				this.database.addPatient(chosenPatient);
				this.patientsListWindow.clear();
				this.newPatientAdditionWindow.clear();
				this.newPatientAdditionWindow.setVisible(false);
				this.patientsListWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.newPatientAdditionWindow, "Błędne dane");
				return;
			}
		} else if (source == this.newPatientAdditionWindow.getbDiscard()) {  //dicard changes
			this.newPatientAdditionWindow.clear();
			this.newPatientAdditionWindow.setVisible(false);
			this.patientsListWindow.setEnabled(true);
		}
	}



	@Override
	public void patientsListWindowChanged(PatientsListWindow patientsListWindow, Object source) throws AppException {
		if (source == this.patientsListWindow.getbDelete()) {  //delete patient
			//delete this patient
			this.patientsListWindow.setRowSelectedNr(this.patientsListWindow.getTabPatientsList().getSelectedRow());
			if (this.patientsListWindow.getRowSelectedNr() != -1) {
				Patient patient = this.patientsListWindow.getPatient();
				//this.patientsListWindow.deletePatient();
				this.database.deletePatient(patient.getPesel());
				ArrayList<Patient> patients = this.database.getPatients();
				this.patientsListWindow.clear();
				this.patientsListWindow.loadPatients(patients);
			}
		} else if (source == this.patientsListWindow.getbChoose()) {
			this.patientsListWindow.setRowSelectedNr(this.patientsListWindow.getTabPatientsList().getSelectedRow());
			if (this.patientsListWindow.getRowSelectedNr() != -1) {
				chosenPatient = this.patientsListWindow.getPatient();
				chosenPatient = this.database.getPatient(chosenPatient.getPesel());
				this.patientsListWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
			}
		} else if (source == this.patientsListWindow.getbChooseByPesel()) {
			String pesel = null;
			try {
				pesel = this.patientsListWindow.getPesel();
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.patientsListWindow, "Błędny pesel");
				return;
			}
			if (this.database.ifPatientExists(pesel)) {
				chosenPatient = this.database.getPatient(pesel);
				this.patientsListWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
			} else {
				int odp = Helper.showConfirmDialog("Pacjenta o tym PESELU nie ma w bazie. " +
						"Czy chcesz dodać nowego pacjenta?");
				if (odp == JOptionPane.YES_OPTION) {
					this.patientsListWindow.setEnabled(false);
					this.newPatientAdditionWindow.clear();
					this.newPatientAdditionWindow.setVisible(true);
				}
			}
		}
	}



	@Override
	public void visitAdditionWindowChanged(VisitAdditionWindow visitAdditionWindow, Object source) throws AppException {
		if (source == this.visitAdditionWindow.getbSave()) {
			ScheduledVisit scheduledVisit = null;
			try {
				scheduledVisit = this.visitAdditionWindow.getNewScheduledVisit();
				scheduledVisit.setDoctor(chosenDoctor);
				if (this.database.ifScheduledVisitExists(chosenPatient.getPesel(), scheduledVisit.getDate(),
						scheduledVisit.getTime())) {
					Helper.showWarningDialog(this.visitAdditionWindow,
							"Nie można dodać takiej wizyty, bo podobna już jest w bazie");
					return;
				}
				this.visitAdditionWindow.clear();
				this.visitAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.mainWindow.getActionPanel().getScheduledVisitsView().addScheduledVisit(scheduledVisit);
				this.database.addScheduledVisit(chosenPatient.getPesel(), scheduledVisit);
			} catch (DataParsingException exception) {
				Helper.showWarningDialog(this.visitAdditionWindow, "Błędne dane");
				return;
			}
		} else if (source == this.visitAdditionWindow.getbDiscard()) {
			this.visitAdditionWindow.clear();
			this.visitAdditionWindow.setVisible(false);
			this.mainWindow.setEnabled(true);
		} else if (source == this.visitAdditionWindow.getbShowDoctors()) {
			this.visitAdditionWindow.setEnabled(false);
			this.doctorsListWindow.setRowSelectedNr(-1);
			this.doctorsListWindow.setVisible(true);
		}
	}
}
