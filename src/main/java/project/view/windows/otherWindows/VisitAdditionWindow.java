package project.view.windows.otherWindows;

import com.toedter.calendar.JDateChooser;
import project.AppException;
import project.DataParsingException;
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
    private JLabel lDoctor;
    private JLabel lPayment;
    private JDateChooser dcDate;
    private JTextField tTime, tDoctor, tPayment;
    private JComboBox<String> cbType;
    private JButton bSave;
    private JButton bDiscard;

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

        this.lDoctor = new JLabel("Imię i nazwisko lekarza:");
        this.lDoctor.setBounds(50, 200, 200, 20);
        this.add(this.lDoctor);

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

        this.tDoctor = new JTextField();
        this.tDoctor.setBounds(250, 200, 200, 20);
        this.add(this.tDoctor);

        this.tPayment = new JTextField();
        this.tPayment.setBounds(250, 250, 200, 20);
        this.add(this.tPayment);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(175, 400, 150, 20);
        this.add(this.bSave);

        this.bDiscard = new JButton("Odrzuć");
        this.bDiscard.setBounds(175, 400, 150, 20);
        this.add(this.bDiscard);

        this.bSave.addActionListener(this);
        this.bDiscard.addActionListener(this);
    }

    public void setVisitAdditionWindowProperties() {
        this.setSize(500, 500);
        this.setResizable(false);
        this.setTitle("Okno dodawania nowej wizyty");
        this.setLayout(null);
        this.setVisible(false);
    }

    public void clear() {
        this.dcDate.setDate(null);
        this.tTime.setText("");
        this.cbType.setSelectedItem("Porada");
        this.tDoctor.setText("");
        this.tPayment.setText("");
    }

    public ScheduledVisit getNewScheduledVisit() throws DataParsingException {
        String date = dcDate.getDate().toString();
        String time = tTime.getText().trim();
        String type = cbType.getSelectedItem().toString();
        String doctor = tDoctor.getText().trim();
        int index = doctor.indexOf(" ");
        String doctorName = doctor.substring(0, index).trim();
        String doctorSurname = doctor.substring(index).trim();
        if (doctorName.length() == 0 || doctorSurname.length() == 0)
            throw new DataParsingException();
        try {
            Float payment = Float.parseFloat(tPayment.getText().trim());
            return new ScheduledVisit(date, time, type,
                    new Doctor(doctorName, doctorSurname, "", ""), payment);
        } catch (Exception exception) {
            throw new DataParsingException();
        }
    }

//    public String gettDate() {
//        return dcDate.toString();
//    }
//
//    public String gettTime() {
//        return tTime.getText();
//    }
//
//    public String gettType() {
//        return cbType.getSelectedItem().toString();
//    }
//
//    public String gettDoctor() {
//        return tDoctor.getText();
//    }
//
//    public String gettPayment() {
//        return tPayment.getText();
//    }

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
