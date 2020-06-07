package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.PatientsListWindow;

public interface PatientsListWindowListener {
    void patientsListWindowChanged(PatientsListWindow patientsListWindow, Object source) throws AppException;
}
