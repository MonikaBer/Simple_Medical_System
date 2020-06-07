package project.view.windows.otherWindows;

import com.toedter.calendar.JDateChooser;
import project.AppException;
import project.DataParsingException;
import project.Helper;
import project.interfaces.ViewListener;
import project.model.MedicalTestResult;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MedicalTestResultAdditionWindow extends JFrame implements ActionListener {

    private JDateChooser dcDate;
    private JLabel lDate, lMedicalTestType, lMedicalTestResult;
    private JTextField tMedicalTestType, tMedicalTestResult;
    private JButton bSave, bDiscard;

    private ViewListener viewListener = null;

    public MedicalTestResultAdditionWindow() {
        this.createMedicalTestResultAdditionWindow();
        this.setMedicalTestResultAdditionWindowProperties();
    }

    public void createMedicalTestResultAdditionWindow() {
        this.lDate = new JLabel("Data:");
        this.lDate.setBounds(50, 50, 200, 20);
        this.add(this.lDate);

        this.lMedicalTestType = new JLabel("Rodzaj badania:");
        this.lMedicalTestType.setBounds(50, 100, 200, 20);
        this.add(this.lMedicalTestType);

        this.lMedicalTestResult = new JLabel("Wynik badania:");
        this.lMedicalTestResult.setBounds(50, 150, 200, 20);
        this.add(this.lMedicalTestResult);

        this.dcDate = new JDateChooser();
        this.dcDate.setBounds(250, 50, 200, 20);
        this.add(this.dcDate);

        this.tMedicalTestType = new JTextField();
        this.tMedicalTestType.setBounds(250, 100, 200, 20);
        this.add(this.tMedicalTestType);

        this.tMedicalTestResult = new JTextField();
        this.tMedicalTestResult.setBounds(250, 150, 200, 20);
        this.add(this.tMedicalTestResult);

        this.bSave = new JButton("Zapisz");
        this.bSave.setBounds(100, 250, 120, 20);
        this.add(this.bSave);

        this.bDiscard = new JButton("Odrzuć");
        this.bDiscard.setBounds(250, 250, 120, 20);
        this.add(this.bDiscard);

        this.bSave.addActionListener(this);
        this.bDiscard.addActionListener(this);
    }

    public void setMedicalTestResultAdditionWindowProperties() {
        this.setSize(500, 400);
        this.setResizable(false);
        this.setTitle("Okno dodawania wyniku badania pacjenta");
        this.setLayout(null);
        this.setVisible(false);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    public void clear() {
        this.dcDate.setDate(null);
        this.tMedicalTestType.setText("");
        this.tMedicalTestResult.setText("");
    }

//    public String gettDate() {
//        return dcDate.toString();
//    }
//
//    public JTextField gettMedicalTestType() {
//        return tMedicalTestType;
//    }
//
//    public JTextField gettMedicalTestResult() {
//        return tMedicalTestResult;
//    }

    public JButton getbSave() {
        return bSave;
    }

    public JButton getbDiscard() {
        return bDiscard;
    }

    public MedicalTestResult getNewMedicalTestResult() throws DataParsingException {
        String date = Helper.dateToString(dcDate.getDate());
        String type = tMedicalTestType.getText().trim();
        String result = tMedicalTestResult.getText().trim();
        int index = result.indexOf(" ");
        if (index >= result.length() || index == -1)
            throw new DataParsingException();
        String resultNumber = result.substring(0, index).trim();
        String units = result.substring(index).trim();
        if (resultNumber.length() == 0 || units.length() == 0)
            throw new DataParsingException();
        try {
            Float resultFloatNumber = Float.parseFloat(resultNumber);
            return new MedicalTestResult(date, type, resultFloatNumber, units);
        } catch (Exception exception) {
            throw new DataParsingException();
        }
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
