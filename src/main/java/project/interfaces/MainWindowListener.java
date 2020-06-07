package project.interfaces;

import project.AppException;
import project.view.windows.mainWindow.MainWindow;

public interface MainWindowListener {
    void mainWindowChanged(MainWindow mainWindow, Object source) throws AppException;
}
