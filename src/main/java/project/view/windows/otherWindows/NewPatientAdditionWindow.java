package project.view.windows.otherWindows;

import project.interfaces.ViewListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class NewPatientAdditionWindow extends JFrame implements ActionListener, MouseListener {

    private JLabel lName;
    private JLabel lSurname;
    private JLabel lPesel;
    private JLabel lInsurance;
    private JLabel lAddress;
    private JTextField tName, tSurname, tPesel, tAddress;
    private JComboBox<String> cbInsurance;
    private JButton bSave;

    private ViewListener viewListener = null;

    public NewPatientAdditionWindow() {
        this.createNewPatientWindow();
        this.setNewPatientWindowProperties();
    }

    private void createNewPatientWindow() {
        this.lName = new JLabel("Imię:");
        this.lName.setBounds(70, 50, 150, 20);
        this.add(this.lName);

        this.lSurname = new JLabel("Nazwisko:");
        this.lSurname.setBounds(70, 100, 150, 20);
        this.add(this.lSurname);

        this.lPesel = new JLabel("PESEL:");
        this.lPesel.setBounds(70, 150, 150, 20);
        this.add(this.lPesel);

        this.lInsurance = new JLabel("Ubezpieczenie:");
        this.lInsurance.setBounds(70, 200, 150, 20);
        this.add(this.lInsurance);

        this.lAddress = new JLabel("Adres:");
        this.lAddress.setBounds(70, 250, 150, 20);
        this.add(this.lAddress);

        this.tName = new JTextField();
        this.tName.setBounds(270, 50, 150, 20);
        this.add(this.tName);

        this.tSurname = new JTextField();
        this.tSurname.setBounds(270, 100, 150, 20);
        this.add(this.tSurname);

        this.tPesel = new JTextField();
        this.tPesel.setBounds(270, 150, 150, 20);
        this.add(this.tPesel);

        this.cbInsurance = new JComboBox<>();
        this.cbInsurance.setBounds(270, 200, 150, 20);
        this.cbInsurance.addItem("NFZ");
        this.cbInsurance.addItem("Prywatne");
        this.add(this.cbInsurance);

        this.tAddress = new JTextField();
        this.tAddress.setBounds(270, 250, 150, 20);
        this.add(this.tAddress);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(200, 350, 100, 20);
        this.add(this.bSave);

        this.bSave.addActionListener(this);
    }

    private void setNewPatientWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dodawania nowego pacjenta");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(false);
    }

    public void setCbInsurance(JComboBox<String> cbInsurance) {
        this.cbInsurance = cbInsurance;
    }

    public JTextField gettName() {
        return tName;
    }

    public JTextField gettSurname() {
        return tSurname;
    }

    public JTextField gettPesel() {
        return tPesel;
    }

    public JTextField gettAddress() {
        return tAddress;
    }

    public JComboBox<String> getCbInsurance() {
        return cbInsurance;
    }

    public JButton getbSave() {
        return bSave;
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
