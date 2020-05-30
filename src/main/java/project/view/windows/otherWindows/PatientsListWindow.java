package project.view.windows.otherWindows;

import project.interfaces.ViewListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PatientsListWindow extends JFrame implements ActionListener, MouseListener {

    private DefaultTableModel tableModel;
    private JTable tabPatientsList;
    private JScrollPane spPatientsList;
    private int rowSelectedNr;
    private JLabel lPesel;
    private JTextField tPesel;
    private JButton bChoose;

    private ViewListener viewListener = null;

    public PatientsListWindow() {
        this.createPatientsListWindow();
        this.setPatientsListWindowProperties();
    }

    private void createPatientsListWindow() {
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

        this.lPesel = new JLabel("PESEL pacjenta:");
        this.lPesel.setBounds(30, 350, 150, 20);
        this.add(this.lPesel);

        this.tPesel = new JTextField();
        this.tPesel.setBounds(220, 300, 150, 20);
        this.add(this.tPesel);

        this.bChoose = new JButton("Wybierz");
        this.bChoose.setBounds(125, 350, 150, 20);
        this.add(this.bChoose);

        this.bChoose.addActionListener(this);
    }

    private void setPatientsListWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno wyboru pacjenta");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(false);
    }

    public JTable getTabPatientsList() {
        return tabPatientsList;
    }

    public int getRowSelectedNr() {
        return rowSelectedNr;
    }

    public JTextField gettPesel() {
        return tPesel;
    }

    public JButton getbChoose() {
        return bChoose;
    }

    public void setRowSelectedNr(int rowSelectedNr) {
        this.rowSelectedNr = rowSelectedNr;
    }

    //listeners management
    public void addListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.viewListener.viewChanged(this, e.getSource());
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        this.viewListener.viewChanged(this, e.getSource());
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
