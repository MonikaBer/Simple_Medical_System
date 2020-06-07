package project.view.windows.otherWindows;

import project.AppException;
import project.DataParsingException;
import project.interfaces.PatientsListWindowListener;
import project.model.person.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class PatientsListWindow extends JFrame implements ActionListener, MouseListener {

    private DefaultTableModel tableModel;
    private JTable tabPatientsList;
    private JScrollPane spPatientsList;
    private int rowSelectedNr;
    private JLabel lPesel;
    private JTextField tPesel;
    private JButton bChoose, bDelete, bChooseByPesel;

    private PatientsListWindowListener viewListener = null;

    public PatientsListWindow() {
        this.createPatientsListWindow();
        this.setPatientsListWindowProperties();
    }

    public void createPatientsListWindow() {
        this.tableModel = new DefaultTableModel();

        this.tabPatientsList = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Imię");
        this.tableModel.addColumn("Nazwisko");
        this.tableModel.addColumn("PESEL");
        this.tabPatientsList.isEditing();

        this.spPatientsList = new JScrollPane(this.tabPatientsList);
        this.spPatientsList.setBounds(30, 30, 450, 300);
        this.add(this.spPatientsList);

        this.rowSelectedNr = -1;

        this.bChoose = new JButton("Wybierz");
        this.bChoose.setBounds(100, 350, 120, 20);
        this.add(this.bChoose);

        this.bDelete = new JButton("Usuń");
        this.bDelete.setBounds(250, 350, 120, 20);
        this.add(this.bDelete);

        this.lPesel = new JLabel("PESEL pacjenta:");
        this.lPesel.setBounds(100, 400, 150, 20);
        this.add(this.lPesel);

        this.tPesel = new JTextField();
        this.tPesel.setBounds(250, 400, 150, 20);
        this.add(this.tPesel);

        this.bChooseByPesel = new JButton("Wybierz po PESELU");
        this.bChooseByPesel.setBounds(135, 430, 200, 20);
        this.add(this.bChooseByPesel);

        this.bChoose.addActionListener(this);
        this.bDelete.addActionListener(this);
        this.bChooseByPesel.addActionListener(this);
    }

    public void setPatientsListWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno wyboru pacjenta");
        this.setLayout(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public JTable getTabPatientsList() {
        return tabPatientsList;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
    }

    public JButton getbChoose() {
        return bChoose;
    }

    public JButton getbDelete() {
        return bDelete;
    }

    public JButton getbChooseByPesel() {
        return bChooseByPesel;
    }

    public void setRowSelectedNr(int rowSelectedNr) {
        this.rowSelectedNr = rowSelectedNr;
    }

    public void loadPatients(ArrayList<Patient> patients) {
        this.clearTabPatientsList();
        for (int i = 0; i < patients.size(); i++) {
            this.addPatient(patients.get(i));
        }
    }

    public void clearTabPatientsList() {
        while (this.tabPatientsList.getRowCount() != 0) {
            this.tableModel.removeRow(this.tabPatientsList.getRowCount()-1);
        }
        this.tPesel.setText("");
    }

    public void addPatient(Patient patient) {
        this.tableModel.addRow(new Object[]{patient.getName(), patient.getSurname(), patient.getPesel()});
    }

    public void deletePatient() {
        this.tableModel.removeRow(this.rowSelectedNr);
    }

    public Patient getPatient() {
        String name = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString().trim();
        String surname = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString().trim();
        String pesel = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString().trim();
        return new Patient(name, surname, pesel, "", "");
    }

    public String getPesel() throws DataParsingException {
        String pesel = this.tPesel.getText().trim();
        if (pesel.length() != 11)
            throw new DataParsingException();
        try {
            Double.parseDouble(pesel);
        } catch (Exception exception) {
            throw new DataParsingException();
        }
        return pesel;
    }

    public void clear() {
        this.tPesel.setText("");
    }

    //listeners management
    public void addListener(PatientsListWindowListener viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.viewListener.patientsListWindowChanged(this, e.getSource());
        } catch (AppException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            this.viewListener.patientsListWindowChanged(this, e.getSource());
        } catch (AppException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {}

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {}

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}
}
