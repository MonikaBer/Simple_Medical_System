package project.view;


import project.view.windows.otherWindows.*;
import project.view.windows.mainWindow.MainWindow;


@SuppressWarnings("serial")
public class View {

	private MainWindow mainWindow;
	private PatientsListWindow patientsListWindow;
	private NewPatientAdditionWindow newPatientAdditionWindow;
	private HospitalisationAdditionWindow hospitalisationAdditionWindow;
	private MedicalTestResultAdditionWindow medicalTestResultAdditionWindow;
	private VisitAdditionWindow visitAdditionWindow;

	public View() {
		this.mainWindow = new MainWindow();
		this.patientsListWindow = new PatientsListWindow();

	}

	public MainWindow getMainWindow() {
		return mainWindow;
	}

	public void setMainWindow(MainWindow mainWindow) {
		this.mainWindow = mainWindow;
	}

	public HospitalisationAdditionWindow getHospitalisationAdditionWindow() {
		return hospitalisationAdditionWindow;
	}

	public void setHospitalisationAdditionWindow(HospitalisationAdditionWindow hospitalisationAdditionWindow) {
		this.hospitalisationAdditionWindow = hospitalisationAdditionWindow;
	}

	public MedicalTestResultAdditionWindow getMedicalTestResultAdditionWindow() {
		return medicalTestResultAdditionWindow;
	}

	public void setMedicalTestResultAdditionWindow(MedicalTestResultAdditionWindow medicalTestResultAdditionWindow) {
		this.medicalTestResultAdditionWindow = medicalTestResultAdditionWindow;
	}

	public VisitAdditionWindow getVisitAdditionWindow() {
		return visitAdditionWindow;
	}

	public void setVisitAdditionWindow(VisitAdditionWindow visitAdditionWindow) {
		this.visitAdditionWindow = visitAdditionWindow;
	}

	public PatientsListWindow getPatientsListWindow() {
		return patientsListWindow;
	}

	public void setPatientsListWindow(PatientsListWindow patientsListWindow) {
		this.patientsListWindow = patientsListWindow;
	}

	public NewPatientAdditionWindow getNewPatientAdditionWindow() {
		return newPatientAdditionWindow;
	}

	public void setNewPatientAdditionWindow(NewPatientAdditionWindow newPatientAdditionWindow) {
		this.newPatientAdditionWindow = newPatientAdditionWindow;
	}
}
