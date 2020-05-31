package project.view.windows.mainWindow.panels;

import project.model.person.Patient;

import javax.swing.*;
import java.awt.*;

public class AboutPatientPanel extends JPanel {

    private JLabel lName, lSurname, lPesel;

    public AboutPatientPanel(int x, int y) {
        super();
        this.setLayout(null);
        this.setBorder(BorderFactory.createTitledBorder("O pacjencie"));
        this.setPreferredSize(new Dimension(x,y));

        this.lName = new JLabel("");
        this.lName.setBounds(30, 30, 200, 20);
        this.add(this.lName);

        this.lSurname = new JLabel("");
        this.lSurname.setBounds(30, 60, 200, 20);
        this.add(this.lSurname);

        this.lPesel = new JLabel("");
        this.lPesel.setBounds(30, 90, 200, 20);
        this.add(this.lPesel);
    }

    public void setPatientInfo(Patient patient) {
        this.lName.setText(patient.getName());
        this.lSurname.setText(patient.getSurname());
        this.lPesel.setText(patient.getPesel());
    }
}
