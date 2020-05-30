package project.controller;

import project.interfaces.ViewListener;
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
    //private Model model = null;
    
//    public Controller(View v, Model m) {
//    	this.view = v;
//    	this.model = m;
//    }

	public Controller(View view) {
		this.mainWindow = view.getMainWindow();
		this.patientsListWindow = view.getPatientsListWindow();
		this.newPatientAdditionWindow = view.getNewPatientAdditionWindow();
		this.visitAdditionWindow = view.getVisitAdditionWindow();
		this.medicalTestResultAdditionWindow = view.getMedicalTestResultAdditionWindow();
		this.hospitalisationAdditionWindow = view.getHospitalisationAdditionWindow();
	}

	@Override
	public void viewChanged(JFrame window, Object source) {
		if (window == this.mainWindow) {
			if (source == this.mainWindow.getMenuPanel().getMClose()) {
				this.mainWindow.dispose();
			} else if (source == this.mainWindow.getMenuPanel().getMPatientsList()) {
				this.patientsListWindow.setVisible(true);
			} else if (source == this.mainWindow.getSelectionPanel().getbPersonalData()) {
				this.mainWindow.getActionPanel().setPersonalDataViewVisibility(true);
			} else if (source == this.mainWindow.getSelectionPanel().getbScheduledVisits()) {
				this.mainWindow.getActionPanel().setScheduledVisitsViewVisibility(true);
			} else if (source == this.mainWindow.getSelectionPanel().getbMedicalTestsResults()) {
				this.mainWindow.getActionPanel().setMedicalTestsResultsViewVisibility(true);
			} else if (source == this.mainWindow.getSelectionPanel().getbArchivedVisits()) {
				this.mainWindow.getActionPanel().setArchivedVisitsViewVisibility(true);
			} else if (source == this.mainWindow.getSelectionPanel().getbHospitalisations()) {
				this.mainWindow.getActionPanel().setHospitalisationsViewVisibility(true);
			}
		}

		else if (window == this.patientsListWindow) {
			if (source == this.patientsListWindow.getbChoose()) {
				//TODO: add ChooseDialog
				this.newPatientAdditionWindow.setVisible(true);
			}
		}

		else if (window == this.newPatientAdditionWindow) {
			if (source == this.newPatientAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in newPatientAdditionWindow and patientsListWindow
				this.newPatientAdditionWindow.setVisible(false);
				this.patientsListWindow.setVisible(false);
			}
		}

		else if (window == this.visitAdditionWindow) {
			if (source == this.visitAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.visitAdditionWindow.setVisible(false);
			}
		}

		else if (window == this.medicalTestResultAdditionWindow) {
			if (source == this.medicalTestResultAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.medicalTestResultAdditionWindow.setVisible(false);
			}
		}

		else if (window == this.hospitalisationAdditionWindow) {
			if (source == this.hospitalisationAdditionWindow.getbSave()) {
				//TODO: check inscribed data
				//TODO: clear inscirbed data in this window
				this.hospitalisationAdditionWindow.setVisible(false);
			}
		}
	}
}
