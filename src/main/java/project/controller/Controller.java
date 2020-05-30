package project.controller;

import project.interfaces.ViewListener;
import project.view.View;
import project.view.windows.mainWindow.MainWindow;
import project.view.windows.otherWindows.PatientsListWindow;

import javax.swing.*;


public class Controller implements ViewListener {

	private MainWindow mainWindow = null;
	private PatientsListWindow patientsListWindow = null;
    //private Model model = null;
    
//    public Controller(View v, Model m) {
//    	this.view = v;
//    	this.model = m;
//    }

	public Controller(View view) {
		this.mainWindow = view.getMainWindow();
		this.patientsListWindow = view.getPatientsListWindow();
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
		} else if (window == this.patientsListWindow) {
			if (source == this.patientsListWindow.getbChoose()) {

			}
		}
	}
}
