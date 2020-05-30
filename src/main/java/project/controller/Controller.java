package project.controller;

import project.interfaces.ViewListener;
import project.view.View;
import project.view.windows.mainWindow.MainWindow;

import javax.swing.*;


public class Controller implements ViewListener {

	private MainWindow mainWindow = null;
    //private Model model = null;
    
//    public Controller(View v, Model m) {
//    	this.view = v;
//    	this.model = m;
//    }

	public Controller(View view) {
		mainWindow = view.getMainWindow();
	}

	@Override
	public void viewChanged(JFrame window, Object source) {
		if (window == mainWindow) {
			if (source == mainWindow.getMenuPanel().getMClose()) {
				mainWindow.dispose();
			} else if (source == mainWindow.getMenuPanel().getMPatientsList()) {
				
			} else if (source == mainWindow.getSelectionPanel().getbPersonalData()) {
				mainWindow.getActionPanel().setPersonalDataViewVisibility(true);
			} else if (source == mainWindow.getSelectionPanel().getbScheduledVisits()) {
				mainWindow.getActionPanel().setScheduledVisitsViewVisibility(true);
			} else if (source == mainWindow.getSelectionPanel().getbMedicalTestsResults()) {
				mainWindow.getActionPanel().setMedicalTestsResultsViewVisibility(true);
			} else if (source == mainWindow.getSelectionPanel().getbArchivedVisits()) {
				mainWindow.getActionPanel().setArchivedVisitsViewVisibility(true);
			} else if (source == mainWindow.getSelectionPanel().getbHospitalisations()) {
				mainWindow.getActionPanel().setHospitalisationsViewVisibility(true);
			}
		}
	}
}
