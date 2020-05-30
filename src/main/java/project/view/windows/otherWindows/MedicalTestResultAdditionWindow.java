package project.view.windows.otherWindows;

import project.interfaces.ViewListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MedicalTestResultAdditionWindow extends JFrame implements ActionListener, MouseListener {

    private JLabel lDate, lMedicalTestType, lMedicalTestResult;
    private JTextField tDate, tMedicalTestType, tMedicalTestResult;
    private JButton bSave;

    private ViewListener viewListener = null;

    public MedicalTestResultAdditionWindow() {
        this.createMedicalTestResultAdditionWindow();
        this.setMedicalTestResultAdditionWindowProperties();
    }

    private void createMedicalTestResultAdditionWindow() {
        this.lDate = new JLabel("Data:");
        this.lDate.setBounds(50, 50, 200, 20);
        this.add(this.lDate);

        this.lMedicalTestType = new JLabel("Rodzaj badania:");
        this.lMedicalTestType.setBounds(50, 100, 200, 20);
        this.add(this.lMedicalTestType);

        this.lMedicalTestResult = new JLabel("Wynik badania:");
        this.lMedicalTestResult.setBounds(50, 150, 200, 20);
        this.add(this.lMedicalTestResult);

        this.tDate = new JTextField();
        this.tDate.setBounds(250, 50, 200, 20);
        this.add(this.tDate);

        this.tMedicalTestType = new JTextField();
        this.tMedicalTestType.setBounds(250, 100, 200, 20);
        this.add(this.tMedicalTestType);

        this.tMedicalTestResult = new JTextField();
        this.tMedicalTestResult.setBounds(250, 150, 200, 20);
        this.add(this.tMedicalTestResult);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(175, 250, 150, 20);
        this.add(this.bSave);

        this.bSave.addActionListener(this);
    }

    private void setMedicalTestResultAdditionWindowProperties() {
        this.setSize(500, 400);
        this.setResizable(false);
        this.setTitle("Okno dodawania wyniku badania pacjenta");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(false);
    }

    public JTextField gettDate() {
        return tDate;
    }

    public JTextField gettMedicalTestType() {
        return tMedicalTestType;
    }

    public JTextField gettMedicalTestResult() {
        return tMedicalTestResult;
    }

    public JButton getbSave() {
        return bSave;
    }

    //listeners management
    public void addListener(ViewListener viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

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
