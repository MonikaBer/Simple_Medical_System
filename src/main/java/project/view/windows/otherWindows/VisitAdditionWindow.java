package project.view.windows.otherWindows;

import com.toedter.calendar.JDateChooser;
import project.AppException;
import project.DataParsingException;
import project.Helper;
import project.interfaces.ViewListener;
import project.model.person.Doctor;
import project.model.visit.ScheduledVisit;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VisitAdditionWindow extends JFrame implements ActionListener {

    private JLabel lDate;
    private JLabel lTime;
    private JLabel lType;
    private JLabel lDoctorPesel;
    private JLabel lPayment;
    private JDateChooser dcDate;
    private JTextField tTime, tDoctorPesel, tPayment;
    private JComboBox<String> cbType;
    private JButton bSave;
    private JButton bDiscard;
    private JButton bShowDoctors;

    private ViewListener viewListener = null;

    public VisitAdditionWindow() {
        this.createVisitAdditionWindow();
        this.setVisitAdditionWindowProperties();
    }

    public void createVisitAdditionWindow() {
        this.lDate = new JLabel("Data:");
        this.lDate.setBounds(50, 50, 200, 20);
        this.add(this.lDate);

        this.lTime = new JLabel("Godzina:");
        this.lTime.setBounds(50, 100, 200, 20);
        this.add(this.lTime);

        this.lType = new JLabel("Rodzaj wizyty:");
        this.lType.setBounds(50, 150, 200, 20);
        this.add(this.lType);

        this.lDoctorPesel = new JLabel("PESEL lekarza:");
        this.lDoctorPesel.setBounds(50, 200, 200, 20);
        this.add(this.lDoctorPesel);

        this.lPayment = new JLabel("Płatność za wizytę [zł]:");
        this.lPayment.setBounds(50, 250, 200, 20);
        this.add(this.lPayment);

        this.dcDate = new JDateChooser();
        this.dcDate.setBounds(250, 50, 200, 20);
        this.add(this.dcDate);

        this.tTime = new JTextField();
        this.tTime.setBounds(250, 100, 200, 20);
        this.add(this.tTime);

        this.cbType = new JComboBox<>();
        this.cbType.setBounds(250, 150, 200, 20);
        this.cbType.addItem("Porada");
        this.cbType.addItem("Zabieg");
        this.cbType.addItem("Badanie");
        this.add(this.cbType);

        this.tDoctorPesel = new JTextField();
        this.tDoctorPesel.setBounds(250, 200, 200, 20);
        this.tDoctorPesel.setEnabled(false);
        this.add(this.tDoctorPesel);

        this.tPayment = new JTextField();
        this.tPayment.setBounds(250, 250, 200, 20);
        this.add(this.tPayment);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(70, 340, 150, 20);
        this.add(this.bSave);

        this.bDiscard = new JButton("Odrzuć");
        this.bDiscard.setBounds(280, 340, 150, 20);
        this.add(this.bDiscard);

        this.bShowDoctors = new JButton("Dostępni lekarze");
        this.bShowDoctors.setBounds(150, 410, 200, 20);
        this.add(this.bShowDoctors);

        this.bSave.addActionListener(this);
        this.bDiscard.addActionListener(this);
        this.bShowDoctors.addActionListener(this);
    }

    public void setVisitAdditionWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dodawania nowej wizyty");
        this.setLayout(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public void clear() {
        this.dcDate.setDate(null);
        this.tTime.setText("");
        this.cbType.setSelectedItem("Porada");
        this.tDoctorPesel.setText("");
        this.tPayment.setText("");
    }

    public ScheduledVisit getNewScheduledVisit() throws DataParsingException {
        String date = Helper.dateToString(dcDate.getDate());
        String time = tTime.getText().trim();
        String type = cbType.getSelectedItem().toString();
        String doctorPesel = tDoctorPesel.getText().trim();
        try {
            Float payment = Float.parseFloat(tPayment.getText().trim());
            Double doubleDoctorPesel = Double.parseDouble(doctorPesel);
            return new ScheduledVisit(date, time, type,
                    new Doctor("", "", doctorPesel, ""), payment);
        } catch (Exception exception) {
            throw new DataParsingException();
        }
    }

    public void settDoctorPesel(String doctorPesel) {
        this.tDoctorPesel.setText(doctorPesel);
    }

    public JButton getbSave() {
        return bSave;
    }

    public JButton getbDiscard() {
        return bDiscard;
    }

    public JButton getbShowDoctors() {
        return bShowDoctors;
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
