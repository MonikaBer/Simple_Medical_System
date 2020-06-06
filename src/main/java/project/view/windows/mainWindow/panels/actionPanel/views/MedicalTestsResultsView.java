package project.view.windows.mainWindow.panels.actionPanel.views;

import project.model.MedicalTestResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class MedicalTestsResultsView implements ActionPanelViews {

    private DefaultTableModel tableModel;
    private JTable tabMedicalTestsResults;
    private JScrollPane spMedicalTestsResults;
    private int rowSelectedNr;
    private JButton bAdd, bDelete;

    public MedicalTestsResultsView(JPanel actionPanel) {
        this.tableModel = new DefaultTableModel();

        this.tabMedicalTestsResults = new JTable(this.tableModel) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.tableModel.addColumn("Data");
        this.tableModel.addColumn("Badanie");
        this.tableModel.addColumn("Wynik");
        this.tabMedicalTestsResults.isEditing();

        this.spMedicalTestsResults = new JScrollPane(this.tabMedicalTestsResults);
        this.spMedicalTestsResults.setBounds(30, 30, 590, 350);
        actionPanel.add(this.spMedicalTestsResults);

        this.rowSelectedNr = -1;

        this.bAdd = new JButton("Dodaj");
        this.bAdd.setBounds(150, 400, 150, 20);
        actionPanel.add(this.bAdd);

        this.bDelete = new JButton("Usuń");
        this.bDelete.setBounds(350, 400, 150, 20);
        actionPanel.add(this.bDelete);

        this.setVisibility(false);
    }

    public void loadPatientMedicalTestsResults(ArrayList<MedicalTestResult> medicalTestsResults) {
        this.clearTabMedicalTestsResults();
        for (int i = 0; i < medicalTestsResults.size(); i++) {
            this.addMedicalTestResult(medicalTestsResults.get(i));
        }
    }

    public void clearTabMedicalTestsResults() {
        for (int i = 0; i < this.tabMedicalTestsResults.getRowCount(); i++) {
            this.tableModel.removeRow(i);
        }
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            this.tabMedicalTestsResults.setVisible(true);
            this.spMedicalTestsResults.setVisible(true);
            this.bAdd.setVisible(true);
            this.bDelete.setVisible(true);
        } else {
            this.tabMedicalTestsResults.setVisible(false);
            this.spMedicalTestsResults.setVisible(false);
            this.bAdd.setVisible(false);
            this.bDelete.setVisible(false);
        }
    }

    public void addMedicalTestResult(MedicalTestResult medicalTestResult) {
        this.tableModel.addRow(new Object[]{medicalTestResult.getDate(), medicalTestResult.getType(),
                medicalTestResult.getResult()+" "+medicalTestResult.getUnits()});
    }

    public MedicalTestResult getMedicalTestResult() {
        String date = this.tableModel.getValueAt(this.rowSelectedNr, 0).toString();
        String type = this.tableModel.getValueAt(this.rowSelectedNr, 1).toString();
        String resultTmp = this.tableModel.getValueAt(this.rowSelectedNr, 2).toString();
        int index = resultTmp.indexOf(" ");
        Float result = Float.parseFloat(resultTmp.substring(0, index).trim());
        String units = resultTmp.substring(index).trim();
        return new MedicalTestResult(date, type, result, units);
    }

    public void deleteMedicalTestResult() {
        this.tableModel.removeRow(this.rowSelectedNr);
    }

    public JTable getTabMedicalTestsResults() {
        return tabMedicalTestsResults;
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
