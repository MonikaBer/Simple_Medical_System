package project.view.windows.otherWindows;

import project.AppException;
import project.interfaces.ViewListener;
import project.model.person.Doctor;
import project.model.person.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class DoctorsListWindow extends JFrame implements ActionListener, MouseListener {

    private DefaultTableModel tableModel;
    private JTable tabDoctorsList;
    private JScrollPane spDoctorsList;
    private int rowSelectedNr;
    private JButton bOk;

    private ViewListener viewListener = null;

    public DoctorsListWindow() {
        this.createDoctorsListWindow();
        this.setDoctorsListWindowProperties();
    }

    public void createDoctorsListWindow() {
        this.tableModel = new DefaultTableModel();

        this.tabDoctorsList = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Imię");
        this.tableModel.addColumn("Nazwisko");
        this.tableModel.addColumn("PESEL");
        this.tableModel.addColumn("Specjalność");
        this.tabDoctorsList.isEditing();

        this.spDoctorsList = new JScrollPane(this.tabDoctorsList);
        this.spDoctorsList.setBounds(30, 30, 450, 300);
        this.add(this.spDoctorsList);

        this.rowSelectedNr = -1;

        this.bOk = new JButton("Ok");
        this.bOk.setBounds(135, 430, 200, 20);
        this.add(this.bOk);

        this.bOk.addActionListener(this);
    }

    public void setDoctorsListWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dostępnych lekarzy");
        this.setLayout(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public JTable getTabDoctorsList() {
        return tabDoctorsList;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
    }

    public void setRowSelectedNr(int rowSelectedNr) {
        this.rowSelectedNr = rowSelectedNr;
    }

    public JButton getbOk() {
        return bOk;
    }

    public void loadDoctors(ArrayList<Doctor> doctors) {
        this.clearTabDoctorsList();
        for (int i = 0; i < doctors.size(); i++) {
            this.addDoctor(doctors.get(i));
        }
    }

    public void clearTabDoctorsList() {
        while (this.tabDoctorsList.getRowCount() != 0) {
            this.tableModel.removeRow(this.tabDoctorsList.getRowCount()-1);
        }
    }

    public void addDoctor(Doctor doctor) {
        this.tableModel.addRow(new Object[]{doctor.getName(), doctor.getSurname(), doctor.getPesel(),
                doctor.getSpecialisation()});
    }

    public Doctor getDoctor() {
        String name = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString().trim();
        String surname = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString().trim();
        String pesel = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString().trim();
        String specialisation = this.tableModel.getValueAt(this.rowSelectedNr, 3).toString().trim();
        return new Doctor(name, surname, pesel, specialisation);
    }



    //listeners management
    public void addListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.viewListener.viewChanged(this, e.getSource());
        } catch (AppException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            this.viewListener.viewChanged(this, e.getSource());
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

