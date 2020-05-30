package project.view.windows.mainWindow;

import project.interfaces.ViewListener;
import project.view.windows.mainWindow.panels.MenuPanel;
import project.view.windows.mainWindow.panels.AboutPatientPanel;
import project.view.windows.mainWindow.panels.SelectionPanel;
import project.view.windows.mainWindow.panels.actionPanel.ActionPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;

public class MainWindow extends JFrame implements ActionListener, MouseListener {

    private JPanel mainPanel, leftPanel, rightPanel;
    private MenuPanel menuPanel;
    private AboutPatientPanel aboutPatientPanel;
    private SelectionPanel selectionPanel;
    private ActionPanel actionPanel;

    private ViewListener viewListener = null;

    public MainWindow() {
        this.createMainWindow();
        this.setMainWindowProperties();
    }

    public void createMainWindow() {
        this.menuPanel = new MenuPanel();
        setJMenuBar(this.menuPanel.getMenuBar());

        this.mainPanel = new JPanel();
        this.mainPanel.setLayout(new BoxLayout(this.mainPanel, BoxLayout.X_AXIS));
        this.getContentPane().add(this.mainPanel);

        this.leftPanel = new JPanel();
        this.leftPanel.setLayout(new BoxLayout(this.leftPanel, BoxLayout.Y_AXIS));
        this.mainPanel.add(this.leftPanel);

        this.rightPanel = new JPanel();
        this.rightPanel.setLayout(new BoxLayout(this.rightPanel, BoxLayout.Y_AXIS));
        this.mainPanel.add(this.rightPanel);

        this.aboutPatientPanel = new AboutPatientPanel(250, 150);
        this.leftPanel.add(this.aboutPatientPanel);

        this.selectionPanel = new SelectionPanel(250, 350);
        this.leftPanel.add(this.selectionPanel);

        this.actionPanel = new ActionPanel(650, 500);
        this.rightPanel.add(this.actionPanel);

        //add ActionListener for view elements
        this.menuPanel.getMClose().addActionListener(this);
        this.menuPanel.getMPatientsList().addActionListener(this);

        this.selectionPanel.getbPersonalData().addActionListener(this);
        this.selectionPanel.getbScheduledVisits().addActionListener(this);
        this.selectionPanel.getbMedicalTestsResults().addActionListener(this);
        this.selectionPanel.getbArchivedVisits().addActionListener(this);
        this.selectionPanel.getbHospitalisations().addActionListener(this);

        this.actionPanel.getPersonalDataView().getbSave().addActionListener(this);

        this.actionPanel.getScheduledVisitsView().getbAdd().addActionListener(this);
        this.actionPanel.getScheduledVisitsView().getbArchive().addActionListener(this);
        this.actionPanel.getScheduledVisitsView().getbDelete().addActionListener(this);

        this.actionPanel.getMedicalTestsResultsView().getbAdd().addActionListener(this);
        this.actionPanel.getMedicalTestsResultsView().getbDelete().addActionListener(this);

        this.actionPanel.getArchivedVisitsView().getbDelete().addActionListener(this);
        this.actionPanel.getArchivedVisitsView().getbShowDesc().addActionListener(this);

        this.actionPanel.getHospitalisationsView().getbAdd().addActionListener(this);
        this.actionPanel.getHospitalisationsView().getbDelete().addActionListener(this);
        this.actionPanel.getHospitalisationsView().getbShowDesc().addActionListener(this);

        this.pack();
    }

    public void setMainWindowProperties() {
        this.setSize(900, 500);
        this.setResizable(false);
        this.setTitle("System informacji medycznej pacjenta");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public MenuPanel getMenuPanel() {
        return menuPanel;
    }

    public AboutPatientPanel getAboutPatientPanel() {
        return aboutPatientPanel;
    }

    public SelectionPanel getSelectionPanel() {
        return selectionPanel;
    }

    public ActionPanel getActionPanel() {
        return actionPanel;
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
