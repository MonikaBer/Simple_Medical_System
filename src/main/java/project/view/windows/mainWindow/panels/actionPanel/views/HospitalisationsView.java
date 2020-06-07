package project.view.windows.mainWindow.panels.actionPanel.views;

import project.model.Hospitalisation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class HospitalisationsView implements ActionPanelViews {

    private DefaultTableModel tableModel;
    private JTable tabHospitalisations;
    private JScrollPane spHospitalisations;
    private int rowSelectedNr;
    private JButton bAdd, bDelete, bShowDesc;

    public HospitalisationsView(JPanel actionPanel) {
        this.tableModel = new DefaultTableModel();

        this.tabHospitalisations = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Data zapisu");
        this.tableModel.addColumn("Data wypisu");
        this.tableModel.addColumn("Powód");
        this.tabHospitalisations.isEditing();

        this.spHospitalisations = new JScrollPane(this.tabHospitalisations);
        this.spHospitalisations.setBounds(30, 30, 590, 350);
        actionPanel.add(this.spHospitalisations);

        this.rowSelectedNr = -1;

        this.bAdd = new JButton("Dodaj");
        this.bAdd.setBounds(50, 400, 150, 20);
        actionPanel.add(this.bAdd);

        this.bDelete = new JButton("Usuń");
        this.bDelete.setBounds(250, 400, 150, 20);
        actionPanel.add(this.bDelete);

        this.setVisibility(false);
    }

    public void loadPatientHospitalisations(ArrayList<Hospitalisation> hospitalisations) {
        this.clearTabHospitalisations();
        for (int i = 0; i < hospitalisations.size(); i++) {
            this.addHospitalisation(hospitalisations.get(i));
        }
    }

    public void clearTabHospitalisations() {
        while (this.tabHospitalisations.getRowCount() != 0) {
            this.tableModel.removeRow(this.tabHospitalisations.getRowCount()-1);
        }
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            this.tabHospitalisations.setVisible(true);
            this.spHospitalisations.setVisible(true);
            this.bAdd.setVisible(true);
            this.bDelete.setVisible(true);
        } else{
            this.tabHospitalisations.setVisible(false);
            this.spHospitalisations.setVisible(false);
            this.bAdd.setVisible(false);
            this.bDelete.setVisible(false);
        }
    }

    public void addHospitalisation(Hospitalisation hospitalisation) {
        this.tableModel.addRow(new Object[]{hospitalisation.getFrom(), hospitalisation.getTo(),
                hospitalisation.getReason()});
    }

    public void deleteHospitalisation() {
        this.tableModel.removeRow(this.rowSelectedNr);
    }

    public Hospitalisation getHospitalisation() {
        String from = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString();
        String to = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString();
        String reason = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString();
        return new Hospitalisation(from, to, reason);
    }

    public JTable getTabHospitalisations() {
        return tabHospitalisations;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
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
