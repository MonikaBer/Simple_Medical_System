package project.controller;

import project.AppException;
import project.DataParsingException;
import project.Helper;
import project.model.database.Database;
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

public class Controller implements ViewListener {

	private MainWindow mainWindow = null;
	private PatientsListWindow patientsListWindow = null;
	private NewPatientAdditionWindow newPatientAdditionWindow = null;
	private VisitAdditionWindow visitAdditionWindow = null;
	private MedicalTestResultAdditionWindow medicalTestResultAdditionWindow = null;
	private HospitalisationAdditionWindow hospitalisationAdditionWindow = null;
    private Database database = null;
    private Patient chosenPatient = null;

    public Controller(View view, Database database) throws AppException {
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
	public void viewChanged(JFrame window, Object source) throws AppException {
		if (window == this.mainWindow) {
			if (source == this.mainWindow.getMenuPanel().getMClose()) {
				this.mainWindow.dispose();
			}
			else if (source == this.mainWindow.getMenuPanel().getMPatientsList()) {
				this.patientsListWindow.loadPatients(database.getPatients());
				this.patientsListWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getSelectionPanel().getbPersonalData()) {
				this.mainWindow.getActionPanel().setPersonalDataViewVisibility(true);
				this.mainWindow.getActionPanel().getPersonalDataView().
						loadPatientPersonalData(database.getPatient(chosenPatient.getPesel()));
			}
			else if (source == this.mainWindow.getSelectionPanel().getbScheduledVisits()) {
				this.mainWindow.getActionPanel().setScheduledVisitsViewVisibility(true);
				this.mainWindow.getActionPanel().getScheduledVisitsView().
						loadPatientScheduledVisits(database.getPatientScheduledVisits(chosenPatient.getPesel()));
			}
			else if (source == this.mainWindow.getSelectionPanel().getbMedicalTestsResults()) {
				this.mainWindow.getActionPanel().setMedicalTestsResultsViewVisibility(true);
				this.mainWindow.getActionPanel().getMedicalTestsResultsView().
						loadPatientMedicalTestsResults(database.getPatientMedicalTestResults(chosenPatient.getPesel()));
			}
			else if (source == this.mainWindow.getSelectionPanel().getbArchivedVisits()) {
				this.mainWindow.getActionPanel().setArchivedVisitsViewVisibility(true);
				this.mainWindow.getActionPanel().getArchivedVisitsView().
						loadPatientArchivedVisits(database.getPatientArchivedVisits(chosenPatient.getPesel()));
			}
			else if (source == this.mainWindow.getSelectionPanel().getbHospitalisations()) {
				this.mainWindow.getActionPanel().setHospitalisationsViewVisibility(true);
				this.mainWindow.getActionPanel().getHospitalisationsView().
						loadPatientHospitalisations(database.getPatientHospitalisations(chosenPatient.getPesel()));
			}
			else if (source == this.mainWindow.getActionPanel().getPersonalDataView().getbSave()) {
				//save patient's data
				try {
					Patient newPatientData = this.mainWindow.getActionPanel().getPersonalDataView().getPatient();
					if (!newPatientData.getPesel().equals(chosenPatient.getPesel()) &&
							database.ifPatientExists(newPatientData.getPesel())) {
						Helper.showWarningDialog(this.mainWindow, "W bazie już istnieje inny pacjent o tym samym PESELU");
						return;
					}
					chosenPatient = newPatientData;
					database.updatePatient(chosenPatient);
					this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
					//this.mainWindow.getActionPanel().getPersonalDataView().loadPatientPersonalData(chosenPatient);
				} catch (DataParsingException exception) {
					Helper.showWarningDialog(this.mainWindow, "Błędne dane");
					return;
				}
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
					ArchivedVisit newArchivedVisit = new ArchivedVisit(scheduledVisit.getDate(),
							scheduledVisit.getType(),scheduledVisit.getDoctor(), "");
					if (!database.addArchivedVisit(chosenPatient.getPesel(), newArchivedVisit)) {
						Helper.showWarningDialog(this.mainWindow, "Wizyta usunięta z planowanych wizyt, natomiast nie " +
								"udało się jej zarchiwizować, bo w archiwum istniała już taka wizyta");
					} else {
						this.mainWindow.getActionPanel().getArchivedVisitsView().addArchivedVisit(newArchivedVisit);
					}
				}
			}
			else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbAdd()) {
				this.visitAdditionWindow.clear();
				this.visitAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getScheduledVisitsView().getbDelete()) {
				//delete this visit
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
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbAdd()) {
				this.medicalTestResultAdditionWindow.clear();
				this.medicalTestResultAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getMedicalTestsResultsView().getbDelete()) {
				//delete this medical test result
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
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbDelete()) {
				//delete this archived visit
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
			else if (source == this.mainWindow.getActionPanel().getArchivedVisitsView().getbShowDesc()) {
				//show description from this archived visit
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbAdd()) {
				this.hospitalisationAdditionWindow.clear();
				this.hospitalisationAdditionWindow.setVisible(true);
				this.mainWindow.setEnabled(false);
			}
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbDelete()) {
				//delete this hospitalisation
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
			else if (source == this.mainWindow.getActionPanel().getHospitalisationsView().getbShowDesc()) {
				//show description from this hospitalisation
			}
		}

		else if (window == this.patientsListWindow) {
			if (source == this.patientsListWindow.getbDelete()) {
				//delete this patient
				this.patientsListWindow.setRowSelectedNr(this.patientsListWindow.getTabPatientsList().getSelectedRow());
				if (this.patientsListWindow.getRowSelectedNr() != -1) {
					Patient patient = this.patientsListWindow.getPatient();
					this.patientsListWindow.deletePatient();
					database.deletePatient(patient.getPesel());
				}
			} else if (source == this.patientsListWindow.getbChoose()) {
				this.patientsListWindow.setRowSelectedNr(this.patientsListWindow.getTabPatientsList().getSelectedRow());
				if (this.patientsListWindow.getRowSelectedNr() != -1) {
					chosenPatient = this.patientsListWindow.getPatient();
					this.patientsListWindow.setVisible(false);
					this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
					this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
					this.mainWindow.setEnabled(true);
				}
			} else if (source == this.patientsListWindow.getbChooseByPesel()) {
				String pesel = this.patientsListWindow.gettPesel().getText().trim();
				try {
					Float.parseFloat(pesel);
				} catch (Exception exception) {
					Helper.showWarningDialog(this.patientsListWindow, "Błędny pesel");
					return;
				}
				if (this.database.ifPatientExists(pesel)) {
					chosenPatient = this.database.getPatient(pesel);
					this.patientsListWindow.setVisible(false);
					this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
					this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
					this.mainWindow.setEnabled(true);
				} else {
					int odp = Helper.showConfirmDialog("Pacjenta o tym PESELU nie ma w bazie. " +
							"Czy chcesz dodać nowego pacjenta?");
					if (odp == JOptionPane.YES_OPTION) {
						this.patientsListWindow.setEnabled(false);
						this.newPatientAdditionWindow.clear();
						this.newPatientAdditionWindow.setVisible(true);
						//} else if (odp == JOptionPane.NO_OPTION || odp == JOptionPane.CLOSED_OPTION) {
					}
				}
			}
		}

		else if (window == this.newPatientAdditionWindow) {
			if (source == this.newPatientAdditionWindow.getbSave()) {
				//save new patient data
				Patient patient = null;
				try {
					patient = this.newPatientAdditionWindow.getNewPatient();
				} catch (DataParsingException exception) {
					Helper.showWarningDialog(this.newPatientAdditionWindow, "Błędne dane");
					return;
				}
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
				this.mainWindow.getAboutPatientPanel().setPatientInfo(chosenPatient);
				this.mainWindow.getActionPanel().loadPatientData(chosenPatient, this.database);
				this.mainWindow.setEnabled(true);
			} else if (source == this.newPatientAdditionWindow.getbDiscard()) {
				this.newPatientAdditionWindow.clear();
				this.newPatientAdditionWindow.setVisible(false);
				this.patientsListWindow.setEnabled(true);
			}
		}

		else if (window == this.visitAdditionWindow) {
			if (source == this.visitAdditionWindow.getbSave()) {
				ScheduledVisit scheduledVisit = null;
				try {
					scheduledVisit = this.visitAdditionWindow.getNewScheduledVisit();
				} catch (DataParsingException exception) {
					Helper.showWarningDialog(this.visitAdditionWindow, "Błędne dane");
					return;
				}
				if (this.database.ifScheduledVisitExists(chosenPatient.getPesel(), scheduledVisit.getDate(),
						scheduledVisit.getTime())) {
					Helper.showWarningDialog(this.visitAdditionWindow,
							"Nie można dodać takiej wizyty, bo podobna już jest w bazie");
					return;
				}
				this.visitAdditionWindow.clear();
				this.visitAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
				this.database.addScheduledVisit(chosenPatient.getPesel(), scheduledVisit);
				this.mainWindow.getActionPanel().getScheduledVisitsView().addScheduledVisit(scheduledVisit);
			} else if (source == this.visitAdditionWindow.getbDiscard()) {
				this.visitAdditionWindow.clear();
				this.visitAdditionWindow.setVisible(false);
				this.mainWindow.setEnabled(true);
			}
		}

		else if (window == this.medicalTestResultAdditionWindow) {
				if (source == this.medicalTestResultAdditionWindow.getbSave()) {
					MedicalTestResult medicalTestResult = null;
					try {
						medicalTestResult = this.medicalTestResultAdditionWindow.getNewMedicalTestResult();
					} catch (DataParsingException exception) {
						Helper.showWarningDialog(this.medicalTestResultAdditionWindow, "Błędne dane");
						return;
					}
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
				} else if (source == this.medicalTestResultAdditionWindow.getbDiscard()) {
					this.medicalTestResultAdditionWindow.clear();
					this.medicalTestResultAdditionWindow.setVisible(false);
					this.mainWindow.setEnabled(true);
				}
		}

		else if (window == this.hospitalisationAdditionWindow) {
				if (source == this.hospitalisationAdditionWindow.getbSave()) {
					Hospitalisation hospitalisation = this.hospitalisationAdditionWindow.getNewHospitalisation();
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
				} else if (source == this.hospitalisationAdditionWindow.getbDiscard()) {
					this.hospitalisationAdditionWindow.clear();
					this.hospitalisationAdditionWindow.setVisible(false);
					this.mainWindow.setEnabled(true);
				}
		}
	}
}
