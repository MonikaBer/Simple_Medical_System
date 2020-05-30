package project.view.windows.mainWindow.panels.actionPanel;

import project.view.windows.mainWindow.panels.actionPanel.views.*;

import javax.swing.*;
import java.awt.*;

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
        this.setPersonalDataViewVisibility(false);
        this.scheduledVisitsView = new ScheduledVisitsView(this);
        this.setScheduledVisitsViewVisibility(false);
        this.medicalTestsResultsView = new MedicalTestsResultsView(this);
        this.setMedicalTestsResultsViewVisibility(false);
        this.archivedVisitsView = new ArchivedVisitsView(this);
        this.setArchivedVisitsViewVisibility(false);
        this.hospitalisationsView = new HospitalisationsView(this);
        this.setHospitalisationsViewVisibility(true);
    }

    public void setPersonalDataViewVisibility(boolean isVisible) {
        this.personalDataView.setVisibility(isVisible);
    }

    public void setScheduledVisitsViewVisibility(boolean isVisible) {
        this.scheduledVisitsView.setVisibility(isVisible);
    }

    public void setMedicalTestsResultsViewVisibility(boolean isVisible) {
        this.medicalTestsResultsView.setVisibility(isVisible);
    }

    public void setArchivedVisitsViewVisibility(boolean isVisible) {
        this.archivedVisitsView.setVisibility(isVisible);
    }

    public void setHospitalisationsViewVisibility(boolean isVisible) {
        this.hospitalisationsView.setVisibility(isVisible);
    }
}
