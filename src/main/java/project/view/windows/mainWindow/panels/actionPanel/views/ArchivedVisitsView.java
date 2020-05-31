package project.view.windows.mainWindow.panels.actionPanel.views;

import project.model.person.Doctor;
import project.model.visit.ArchivedVisit;
import project.model.visit.ScheduledVisit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ArchivedVisitsView implements ActionPanelViews {

    private DefaultTableModel tableModel;
    private JTable tabArchivedVisits;
    private JScrollPane spArchivedVisits;
    private int rowSelectedNr;
    private JButton bDelete, bShowDesc;

    public ArchivedVisitsView(JPanel actionPanel) {
        this.tableModel = new DefaultTableModel();

        this.tabArchivedVisits = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Data");
        this.tableModel.addColumn("Rodzaj wizyty");
        this.tableModel.addColumn("Lekarz");
        this.tableModel.addColumn("Specjalność");
        this.tabArchivedVisits.isEditing();

        this.spArchivedVisits = new JScrollPane(this.tabArchivedVisits);
        this.spArchivedVisits.setBounds(30, 30, 590, 350);
        actionPanel.add(this.spArchivedVisits);

        this.rowSelectedNr = -1;

        this.bDelete = new JButton("Usuń");
        this.bDelete.setBounds(150, 400, 150, 20);
        actionPanel.add(this.bDelete);

        this.bShowDesc = new JButton("Zobacz opis");
        this.bShowDesc.setBounds(350, 400, 150, 20);
        actionPanel.add(this.bShowDesc);

        this.setVisibility(false);
    }

    public void loadPatientArchivedVisits(ArrayList<ArchivedVisit> archivedVisits) {
        this.clearTabArchivedVisits();
        for (int i = 0; i < archivedVisits.size(); i++) {
            this.addArchivedVisit(archivedVisits.get(i));
        }
    }

    public void clearTabArchivedVisits() {
        for (int i = 0; i < this.tabArchivedVisits.getRowCount(); i++) {
            this.tableModel.removeRow(i);
        }
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            this.tabArchivedVisits.setVisible(true);
            this.spArchivedVisits.setVisible(true);
            this.bDelete.setVisible(true);
            this.bShowDesc.setVisible(true);
        } else{
            this.tabArchivedVisits.setVisible(false);
            this.spArchivedVisits.setVisible(false);
            this.bDelete.setVisible(false);
            this.bShowDesc.setVisible(false);
        }
    }

    public void addArchivedVisit(ArchivedVisit archivedVisit) {
        this.tableModel.addRow(new Object[]{archivedVisit.getDate(), archivedVisit.getType(),
                archivedVisit.getDoctor().getName()+" "+archivedVisit.getDoctor().getSurname(),
                archivedVisit.getDoctor().getSpecialisation(), archivedVisit.getDescription()});
    }

    public void deleteArchivedVisit() {
        this.tableModel.removeRow(this.rowSelectedNr);
    }

    public ArchivedVisit getArchivedVisit() {
        String date = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString();
        String type = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString();
        String doctor = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString();
        int index = doctor.indexOf(" ");
        String doctorName = doctor.substring(0, index).trim();
        String doctorSurname = doctor.substring(index).trim();
        String specialisation = this.tableModel.getValueAt(this.rowSelectedNr, 3).toString();
        return new ArchivedVisit(date, type, new Doctor(doctorName, doctorSurname, "", specialisation),
                "");
    }

    public void showScheduleVisitDescription() {
        //show message dialog with description
    }

    public JTable getTabArchivedVisits() {
        return tabArchivedVisits;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
    }

    public JButton getbDelete() {
        return bDelete;
    }

    public JButton getbShowDesc() {
        return bShowDesc;
    }

    public void setRowSelectedNr(int rowSelectedNr) {
        this.rowSelectedNr = rowSelectedNr;
    }
}
