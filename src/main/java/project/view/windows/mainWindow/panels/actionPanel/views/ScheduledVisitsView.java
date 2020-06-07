package project.view.windows.mainWindow.panels.actionPanel.views;

import project.DataParsingException;
import project.model.person.Doctor;
import project.model.visit.ScheduledVisit;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ScheduledVisitsView implements ActionPanelViews {

    private DefaultTableModel tableModel;
    private JTable tabScheduledVisits;
    private JScrollPane spScheduledVisits;
    private int rowSelectedNr;
    private JButton bArchive, bAdd, bDelete;

    public ScheduledVisitsView(JPanel actionPanel) {
        this.tableModel = new DefaultTableModel();

        this.tabScheduledVisits = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Data");
        this.tableModel.addColumn("Godzina");
        this.tableModel.addColumn("Rodzaj wizyty");
        this.tableModel.addColumn("Lekarz");
        this.tableModel.addColumn("Specjalność");
        this.tableModel.addColumn("Płatność");
        this.tabScheduledVisits.isEditing();

        this.spScheduledVisits = new JScrollPane(this.tabScheduledVisits);
        this.spScheduledVisits.setBounds(30, 30, 590, 350);
        actionPanel.add(this.spScheduledVisits);

        this.rowSelectedNr = -1;

        this.bArchive = new JButton("Archiwizuj");
        this.bArchive.setBounds(50, 400, 150, 20);
        actionPanel.add(this.bArchive);

        this.bAdd = new JButton("Dodaj");
        this.bAdd.setBounds(250, 400, 150, 20);
        actionPanel.add(this.bAdd);

        this.bDelete = new JButton("Usuń");
        this.bDelete.setBounds(450, 400, 150, 20);
        actionPanel.add(this.bDelete);

        this.setVisibility(false);
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            this.tabScheduledVisits.setVisible(true);
            this.spScheduledVisits.setVisible(true);
            this.bArchive.setVisible(true);
            this.bAdd.setVisible(true);
            this.bDelete.setVisible(true);
        } else{
            this.tabScheduledVisits.setVisible(false);
            this.spScheduledVisits.setVisible(false);
            this.bArchive.setVisible(false);
            this.bAdd.setVisible(false);
            this.bDelete.setVisible(false);
        }
    }

    public void loadPatientScheduledVisits(ArrayList<ScheduledVisit> scheduledVisits) {
        this.clearTabScheduledVisits();
        for (int i = 0; i < scheduledVisits.size(); i++) {
            this.addScheduledVisit(scheduledVisits.get(i));
        }
    }

    public void clearTabScheduledVisits() {
        while (this.tabScheduledVisits.getRowCount() != 0) {
            this.tableModel.removeRow(this.tabScheduledVisits.getRowCount()-1);
        }
    }

    public void addScheduledVisit(ScheduledVisit scheduledVisit) {
        this.tableModel.addRow(new Object[]{scheduledVisit.getDate(), scheduledVisit.getTime(),
                scheduledVisit.getType(), scheduledVisit.getDoctor().getName()+" "+
                scheduledVisit.getDoctor().getSurname(), scheduledVisit.getDoctor().getSpecialisation(),
                scheduledVisit.getPayment().toString()});
    }

    public void deleteScheduledVisit() {
        this.tableModel.removeRow(this.rowSelectedNr);
    }

    public ScheduledVisit getScheduledVisit() {
        String date = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString();
        String time = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString();
        String type = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString();
        String doctor = this.tableModel.getValueAt(this.rowSelectedNr, 3).toString();
        int index = doctor.indexOf(" ");
        String doctorName = doctor.substring(0, index).trim();
        String doctorSurname = doctor.substring(index).trim();
        String specialisation = this.tableModel.getValueAt(this.rowSelectedNr, 4).toString();
        Float payment = Float.parseFloat(this.tableModel.getValueAt(this.rowSelectedNr, 5).toString());
        return new ScheduledVisit(date, time, type, new Doctor(doctorName, doctorSurname, "", specialisation),
                    payment);
    }

    public JTable getTabScheduledVisits() {
        return tabScheduledVisits;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
    }

    public JButton getbArchive() {
        return bArchive;
    }

    public JButton getbAdd() {
        return bAdd;
    }

    public JButton getbDelete() {
        return bDelete;
    }

    public void setRowSelectedNr(int rowSelectedNr) {
        this.rowSelectedNr = rowSelectedNr;
    }
}
