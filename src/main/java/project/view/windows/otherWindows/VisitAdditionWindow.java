package project.view.windows.otherWindows;

import project.interfaces.ViewListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class VisitAdditionWindow extends JFrame implements ActionListener, MouseListener {

    private JLabel lDate;
    private JLabel lTime;
    private JLabel lType;
    private JLabel lDoctor;
    private JLabel lPayment;
    private JTextField tDate, tTime, tType, tDoctor, tPayment;
    private JButton bSave;

    private ViewListener viewListener = null;

    public VisitAdditionWindow() {
        this.createVisitAdditionWindow();
        this.setVisitAdditionWindowProperties();
    }

    private void createVisitAdditionWindow() {
        this.lDate = new JLabel("Data:");
        this.lDate.setBounds(50, 50, 200, 20);
        this.add(this.lDate);

        this.lTime = new JLabel("Godzina:");
        this.lTime.setBounds(50, 100, 200, 20);
        this.add(this.lTime);

        this.lType = new JLabel("Rodzaj wizyty:");
        this.lType.setBounds(50, 150, 200, 20);
        this.add(this.lType);

        this.lDoctor = new JLabel("Imię i nazwisko lekarza:");
        this.lDoctor.setBounds(50, 200, 200, 20);
        this.add(this.lDoctor);

        this.lPayment = new JLabel("Płatność za wizytę [zł]:");
        this.lPayment.setBounds(50, 250, 200, 20);
        this.add(this.lPayment);

        this.tDate = new JTextField();
        this.tDate.setBounds(250, 50, 200, 20);
        this.add(this.tDate);

        this.tTime = new JTextField();
        this.tTime.setBounds(250, 100, 200, 20);
        this.add(this.tTime);

        this.tType = new JTextField();
        this.tType.setBounds(250, 150, 200, 20);
        this.add(this.tType);

        this.tDoctor = new JTextField();
        this.tDoctor.setBounds(250, 200, 200, 20);
        this.add(this.tDoctor);

        this.tPayment = new JTextField();
        this.tPayment.setBounds(250, 250, 200, 20);
        this.add(this.tPayment);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(175, 400, 150, 20);
        this.add(this.bSave);

        this.bSave.addActionListener(this);
    }

    private void setVisitAdditionWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dodawania nowej wizyty");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(false);
    }

    public void clear() {
        this.tDate.setText("");
        this.tTime.setText("");
        this.tType.setText("");
        this.tDoctor.setText("");
        this.tPayment.setText("");
    }

    public JTextField gettDate() {
        return tDate;
    }

    public JTextField gettTime() {
        return tTime;
    }

    public JTextField gettType() {
        return tType;
    }

    public JTextField gettDoctor() {
        return tDoctor;
    }

    public JTextField gettPayment() {
        return tPayment;
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
        try {
            this.viewListener.viewChanged(this, e.getSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            this.viewListener.viewChanged(this, e.getSource());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
