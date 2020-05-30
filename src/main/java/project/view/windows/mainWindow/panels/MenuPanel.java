package project.view.windows.mainWindow.panels;

import javax.swing.*;

public class MenuPanel extends JMenu {

    private final JMenuBar menuBar;
    private JMenu menuApp;
    private JMenuItem mPatientsList;
    private JMenuItem mClose;

    public MenuPanel() {
        super();

        menuBar = new JMenuBar();
        menuApp = new JMenu("Aplikacja");
        mPatientsList = new JMenuItem("Lista pacjentów");
        mClose = new JMenuItem("Zamknij");

        menuApp.add(mPatientsList);
        menuApp.add(mClose);
        menuBar.add(menuApp);
        mClose.setAccelerator(KeyStroke.getKeyStroke("alt F4"));
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JMenuItem getMPatientsList() {
        return mPatientsList;
    }

    public JMenuItem getMClose() {
        return mClose;
    }
}
