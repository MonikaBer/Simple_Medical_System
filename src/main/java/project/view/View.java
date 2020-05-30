package project.view;


import project.view.windows.otherWindows.HospitalisationAdditionWindow;
import project.view.windows.otherWindows.MedicalTestResultAdditionWindow;
import project.view.windows.otherWindows.VisitAdditionWindow;
import project.view.windows.mainWindow.MainWindow;


@SuppressWarnings("serial")
public class View {

	private MainWindow mainWindow;
	private HospitalisationAdditionWindow hospitalisationAdditionWindow;
	private MedicalTestResultAdditionWindow medicalTestResultAdditionWindow;
	private VisitAdditionWindow visitAdditionWindow;

	public View() {
		this.initMainView();
	}

	public void initMainView() {
		mainWindow = new MainWindow();
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
}
