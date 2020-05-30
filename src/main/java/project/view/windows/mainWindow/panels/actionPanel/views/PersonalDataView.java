package project.view.windows.mainWindow.panels.actionPanel.views;

import javax.swing.*;

public class PersonalDataView implements ActionPanelViews {

    private JLabel lName, lSurname, lPesel, lInsurance, lAddress;
    private JTextField tName, tSurname, tPesel, tAddress;
    private JComboBox<String> cbInsurance;

    public JTextField gettName() {
        return tName;
    }

    public void settName(JTextField tName) {
        this.tName = tName;
    }

    public JTextField gettSurname() {
        return tSurname;
    }

    public void settSurname(JTextField tSurname) {
        this.tSurname = tSurname;
    }

    public JTextField gettPesel() {
        return tPesel;
    }

    public void settPesel(JTextField tPesel) {
        this.tPesel = tPesel;
    }

    public JTextField gettAddress() {
        return tAddress;
    }

    public void settAddress(JTextField tAddress) {
        this.tAddress = tAddress;
    }

    public JComboBox<String> getCbInsurance() {
        return cbInsurance;
    }

    public void setCbInsurance(JComboBox<String> cbInsurance) {
        this.cbInsurance = cbInsurance;
    }

    public PersonalDataView(JPanel actionPanel) {
        this.lName = new JLabel("Imię:");
        this.lName.setBounds(50, 50, 150, 20);
        actionPanel.add(this.lName);

        this.lSurname = new JLabel("Nazwisko:");
        this.lSurname.setBounds(50, 100, 150, 20);
        actionPanel.add(this.lSurname);

        this.lPesel = new JLabel("PESEL:");
        this.lPesel.setBounds(50, 150, 150, 20);
        actionPanel.add(this.lPesel);

        this.lInsurance = new JLabel("Ubezpieczenie:");
        this.lInsurance.setBounds(50, 200, 150, 20);
        actionPanel.add(this.lInsurance);

        this.lAddress = new JLabel("Adres:");
        this.lAddress.setBounds(50, 250, 150, 20);
        actionPanel.add(this.lAddress);

        this.tName = new JTextField();
        this.tName.setBounds(250, 50, 150, 20);
        actionPanel.add(this.tName);

        this.tSurname = new JTextField();
        this.tSurname.setBounds(250, 100, 150, 20);
        actionPanel.add(this.tSurname);

        this.tPesel = new JTextField();
        this.tPesel.setBounds(250, 150, 150, 20);
        actionPanel.add(this.tPesel);

        this.cbInsurance = new JComboBox<>();
        this.cbInsurance.setBounds(250, 200, 150, 20);
        this.cbInsurance.addItem("NFZ");
        this.cbInsurance.addItem("Prywatne");
        actionPanel.add(this.cbInsurance);

        this.tAddress = new JTextField();
        this.tAddress.setBounds(250, 250, 150, 20);
        actionPanel.add(this.tAddress);

        this.setVisibility(false);
    }

    @Override
    public void setVisibility(boolean isVisible) {
        if (isVisible) {
            this.lName.setVisible(true);
            this.lSurname.setVisible(true);
            this.lPesel.setVisible(true);
            this.lInsurance.setVisible(true);
            this.lAddress.setVisible(true);

            this.tName.setVisible(true);
            this.tSurname.setVisible(true);
            this.tPesel.setVisible(true);
            this.cbInsurance.setVisible(true);
            this.tAddress.setVisible(true);
        } else{
            this.lName.setVisible(false);
            this.lSurname.setVisible(false);
            this.lPesel.setVisible(false);
            this.lInsurance.setVisible(false);
            this.lAddress.setVisible(false);

            this.tName.setVisible(false);
            this.tSurname.setVisible(false);
            this.tPesel.setVisible(false);
            this.cbInsurance.setVisible(false);
            this.tAddress.setVisible(false);
        }
    }
}
