package project.view.windows.mainWindow.panels.actionPanel;

import project.AppException;
import project.model.database.Database;
import project.model.Hospitalisation;
import project.model.MedicalTestResult;
import project.model.person.Patient;
import project.model.visit.ArchivedVisit;
import project.model.visit.ScheduledVisit;
import project.view.windows.mainWindow.panels.actionPanel.views.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ActionPanel extends JPanel {

    private PersonalDataView personalDataView;
    private ScheduledVisitsView scheduledVisitsView;
    private MedicalTestsResultsView medicalTestsResultsView;
    private ArchivedVisitsView archivedVisitsView;
    private HospitalisationsView hospitalisationsView;

    public ActionPanel(int x, int y) {
        super();
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder("Panel główny"));
        this.setPreferredSize(new Dimension(x,y));

        this.personalDataView = new PersonalDataView(this);
        this.scheduledVisitsView = new ScheduledVisitsView(this);
        this.medicalTestsResultsView = new MedicalTestsResultsView(this);
        this.archivedVisitsView = new ArchivedVisitsView(this);
        this.hospitalisationsView = new HospitalisationsView(this);
    }

    public void loadPatientData(Patient patient, Database database) throws AppException {
        this.personalDataView.loadPatientPersonalData(patient);
        ArrayList<MedicalTestResult> medicalTestsResults = database.getPatientMedicalTestResults(patient.getPesel());
        this.medicalTestsResultsView.loadPatientMedicalTestsResults(medicalTestsResults);
        ArrayList <ScheduledVisit> scheduledVisits = database.getPatientScheduledVisits(patient.getPesel());
        this.scheduledVisitsView.loadPatientScheduledVisits(scheduledVisits);
        ArrayList<ArchivedVisit> archivedVisits = database.getPatientArchivedVisits(patient.getPesel());
        this.archivedVisitsView.loadPatientArchivedVisits(archivedVisits);
        ArrayList<Hospitalisation> hospitalisations = database.getPatientHospitalisations(patient.getPesel());
        this.hospitalisationsView.loadPatientHospitalisations(hospitalisations);
    }

    public void setPersonalDataViewVisibility(boolean isVisible) {
        this.personalDataView.setVisibility(isVisible);
        if (isVisible) {
            this.scheduledVisitsView.setVisibility(false);
            this.medicalTestsResultsView.setVisibility(false);
            this.archivedVisitsView.setVisibility(false);
            this.hospitalisationsView.setVisibility(false);
        }
    }

    public void setScheduledVisitsViewVisibility(boolean isVisible) {
        this.scheduledVisitsView.setVisibility(isVisible);
        if (isVisible) {
            this.personalDataView.setVisibility(false);
            this.medicalTestsResultsView.setVisibility(false);
            this.archivedVisitsView.setVisibility(false);
            this.hospitalisationsView.setVisibility(false);
        }
    }

    public void setMedicalTestsResultsViewVisibility(boolean isVisible) {
        this.medicalTestsResultsView.setVisibility(isVisible);
        if (isVisible) {
            this.personalDataView.setVisibility(false);
            this.scheduledVisitsView.setVisibility(false);
            this.archivedVisitsView.setVisibility(false);
            this.hospitalisationsView.setVisibility(false);
        }
    }

    public void setArchivedVisitsViewVisibility(boolean isVisible) {
        this.archivedVisitsView.setVisibility(isVisible);
        if (isVisible) {
            this.personalDataView.setVisibility(false);
            this.scheduledVisitsView.setVisibility(false);
            this.medicalTestsResultsView.setVisibility(false);
            this.hospitalisationsView.setVisibility(false);
        }
    }

    public void setHospitalisationsViewVisibility(boolean isVisible) {
        this.hospitalisationsView.setVisibility(isVisible);
        if (isVisible) {
            this.personalDataView.setVisibility(false);
            this.scheduledVisitsView.setVisibility(false);
            this.medicalTestsResultsView.setVisibility(false);
            this.archivedVisitsView.setVisibility(false);
        }
    }

    public PersonalDataView getPersonalDataView() {
        return personalDataView;
    }

    public ScheduledVisitsView getScheduledVisitsView() {
        return scheduledVisitsView;
    }

    public MedicalTestsResultsView getMedicalTestsResultsView() {
        return medicalTestsResultsView;
    }

    public ArchivedVisitsView getArchivedVisitsView() {
        return archivedVisitsView;
    }

    public HospitalisationsView getHospitalisationsView() {
        return hospitalisationsView;
    }
}
