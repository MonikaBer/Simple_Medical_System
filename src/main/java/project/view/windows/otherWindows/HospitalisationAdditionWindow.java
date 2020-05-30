package project.view.windows.otherWindows;

import project.interfaces.ViewListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class HospitalisationAdditionWindow extends JFrame implements ActionListener, MouseListener {

    private JLabel lFrom, lTo, lReason;
    private JTextField tFrom, tTo, tReason;
    private JButton bSave;

    private ViewListener viewListener = null;

    public HospitalisationAdditionWindow() {
        this.createHospitalisationAdditionWindow();
        this.setHospitalisationAdditionWindowProperties();
    }

    private void createHospitalisationAdditionWindow() {
        this.lFrom = new JLabel("Hospitalizacja od:");
        this.lFrom.setBounds(50, 50, 200, 20);
        this.add(this.lFrom);

        this.lTo = new JLabel("Hospitalizacja do");
        this.lTo.setBounds(50, 100, 200, 20);
        this.add(this.lTo);

        this.lReason = new JLabel("Powód:");
        this.lReason.setBounds(50, 150, 200, 20);
        this.add(this.lReason);

        this.tFrom = new JTextField();
        this.tFrom.setBounds(250, 50, 200, 20);
        this.add(this.tFrom);

        this.tTo = new JTextField();
        this.tTo.setBounds(250, 100, 200, 20);
        this.add(this.tTo);

        this.tReason = new JTextField();
        this.tReason.setBounds(250, 150, 200, 20);
        this.add(this.tReason);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(175, 250, 150, 20);
        this.add(this.bSave);

        this.bSave.addActionListener(this);
    }

    private void setHospitalisationAdditionWindowProperties() {
        this.setSize(500, 400);
        this.setResizable(false);
        this.setTitle("Okno dodawania hospitalizacji pacjenta");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setVisible(false);
    }

    public JTextField gettFrom() {
        return tFrom;
    }

    public JTextField gettTo() {
        return tTo;
    }

    public JTextField gettReason() {
        return tReason;
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
