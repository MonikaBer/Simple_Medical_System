package project.view.windows.otherWindows;

import project.AppException;
import project.DataParsingException;
import project.interfaces.ViewListener;
import project.model.person.Patient;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewPatientAdditionWindow extends JFrame implements ActionListener {

    private JLabel lName;
    private JLabel lSurname;
    private JLabel lPesel;
    private JLabel lInsurance;
    private JLabel lAddress;
    private JTextField tName, tSurname, tPesel, tAddress;
    private JComboBox<String> cbInsurance;
    private JButton bSave;
    private JButton bDiscard;

    private ViewListener viewListener = null;

    public NewPatientAdditionWindow() {
        this.createNewPatientWindow();
        this.setNewPatientWindowProperties();
    }

    public void createNewPatientWindow() {
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
        this.bSave.setBounds(80, 350, 100, 20);
        this.add(this.bSave);

        this.bDiscard = new JButton("Odrzuć");
        this.bDiscard.setBounds(220, 350, 100, 20);
        this.add(this.bDiscard);

        this.bSave.addActionListener(this);
        this.bDiscard.addActionListener(this);
    }

    public void setNewPatientWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dodawania nowego pacjenta");
        this.setLayout(null);
        this.setVisible(false);
    }

    public void clear() {
        this.tName.setText("");
        this.tSurname.setText("");
        this.tPesel.setText("");
        this.cbInsurance.setSelectedItem("NFZ");
        this.tAddress.setText("");
    }

//    public JTextField gettName() {
//        return tName;
//    }
//
//    public JTextField gettSurname() {
//        return tSurname;
//    }
//
//    public JTextField gettPesel() {
//        return tPesel;
//    }
//
//    public JTextField gettAddress() {
//        return tAddress;
//    }
//
//    public String getInsurance() {
//        return cbInsurance.getSelectedItem().toString();
//    }

//    public void setInsurance(String insurance) {
//        this.cbInsurance.setSelectedItem(insurance);
//    }

    public Patient getNewPatient() throws DataParsingException {
        String name = tName.getText().trim();
        String surname = tSurname.getText().trim();
        String pesel = tPesel.getText().trim();
        String insurance = cbInsurance.getSelectedItem().toString();
        String address = tAddress.getText().trim();

        try {
            Float.parseFloat(pesel);
            return new Patient(name, surname, pesel, insurance, address);
        } catch (Exception exception) {
            throw new DataParsingException();
        }
    }

    public JButton getbSave() {
        return bSave;
    }

    public JButton getbDiscard() {
        return bDiscard;
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
}
