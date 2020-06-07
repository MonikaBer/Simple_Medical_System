package project.view.windows.otherWindows;

import project.AppException;
import project.DataParsingException;
import project.Helper;

import com.toedter.calendar.JDateChooser;
import project.interfaces.HospitalisationAdditionWindowListener;
import project.model.Hospitalisation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HospitalisationAdditionWindow extends JFrame implements ActionListener {

    private JLabel lFrom, lTo, lReason;
    private JDateChooser dcFrom, dcTo;
    private JTextField tReason;
    private JButton bSave, bDiscard;

    private HospitalisationAdditionWindowListener viewListener = null;

    public HospitalisationAdditionWindow() {
        this.createHospitalisationAdditionWindow();
        this.setHospitalisationAdditionWindowProperties();
    }

    public void createHospitalisationAdditionWindow() {
        this.lFrom = new JLabel("Hospitalizacja od:");
        this.lFrom.setBounds(50, 50, 200, 20);
        this.add(this.lFrom);

        this.lTo = new JLabel("Hospitalizacja do");
        this.lTo.setBounds(50, 100, 200, 20);
        this.add(this.lTo);

        this.lReason = new JLabel("Powód:");
        this.lReason.setBounds(50, 150, 200, 20);
        this.add(this.lReason);

        this.dcFrom = new JDateChooser();
        this.dcFrom.setBounds(250, 50, 200, 20);
        this.add(this.dcFrom);

        this.dcTo = new JDateChooser();
        this.dcTo.setBounds(250, 100, 200, 20);
        this.add(this.dcTo);

        this.tReason = new JTextField();
        this.tReason.setBounds(250, 150, 200, 20);
        this.add(this.tReason);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(100, 250, 120, 20);
        this.add(this.bSave);

        this.bDiscard = new JButton("Odrzuć");
        this.bDiscard.setBounds(250, 250, 120, 20);
        this.add(this.bDiscard);

        this.bSave.addActionListener(this);
        this.bDiscard.addActionListener(this);
    }

    public void setHospitalisationAdditionWindowProperties() {
        this.setSize(500, 400);
        this.setResizable(false);
        this.setTitle("Okno dodawania hospitalizacji pacjenta");
        this.setLayout(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public void clear() {
        this.dcFrom.setDate(null);
        this.dcTo.setDate(null);
        this.tReason.setText("");
    }

    public Hospitalisation getNewHospitalisation() throws DataParsingException {
        String dateFrom = Helper.dateToString(dcFrom.getDate());
        String dateTo = Helper.dateToString(dcTo.getDate());
        if (!Helper.ifDatesOrderIsCorrect(dcFrom.getDate(), dcTo.getDate())) {
            throw new DataParsingException();
        }
        String reason = this.tReason.getText().trim();
        return new Hospitalisation(dateFrom, dateTo, reason);
    }

    public JButton getbSave() {
        return bSave;
    }

    public JButton getbDiscard() {
        return bDiscard;
    }

    //listeners management
    public void addListener(HospitalisationAdditionWindowListener viewListener) {
        this.viewListener = viewListener;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            this.viewListener.hospitalisationAdditionWindowChanged(this, e.getSource());
        } catch (AppException throwables) {
            throwables.printStackTrace();
        }
    }
}