package project.view.windows.mainWindow.panels;

import javax.swing.*;
import java.awt.*;

public class SelectionPanel extends JPanel {

    private final JButton bPersonalData;
    private final JButton bScheduledVisits;
    private final JButton bMedicalTestsResults;
    private final JButton bArchivedVisits;
    private final JButton bHospitalisations;

    public SelectionPanel(int x, int y) {
        super();
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder("Panel wyboru"));
        this.setPreferredSize(new Dimension(x,y));

        this.bPersonalData = new JButton("Dane pacjenta");
        this.bPersonalData.setBounds(20, 40, 200, 30);
        this.add(this.bPersonalData);

        this.bScheduledVisits = new JButton("Zaplanowane wizyty");
        this.bScheduledVisits.setBounds(20, 90, 200, 30);
        this.add(this.bScheduledVisits);

        this.bMedicalTestsResults = new JButton("Wyniki badań");
        this.bMedicalTestsResults.setBounds(20, 140, 200, 30);
        this.add(this.bMedicalTestsResults);

        this.bArchivedVisits = new JButton("Historia wizyt");
        this.bArchivedVisits.setBounds(20, 190, 200, 30);
        this.add(this.bArchivedVisits);

        this.bHospitalisations = new JButton("Hospitalizacje");
        this.bHospitalisations.setBounds(20, 240, 200, 30);
        this.add(this.bHospitalisations);
    }

    public JButton getbPersonalData() {
        return bPersonalData;
    }

    public JButton getbScheduledVisits() {
        return bScheduledVisits;
    }

    public JButton getbMedicalTestsResults() {
        return bMedicalTestsResults;
    }

    public JButton getbArchivedVisits() {
        return bArchivedVisits;
    }

    public JButton getbHospitalisations() {
        return bHospitalisations;
    }
}
