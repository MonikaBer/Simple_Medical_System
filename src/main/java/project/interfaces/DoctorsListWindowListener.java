package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.DoctorsListWindow;

public interface DoctorsListWindowListener {
    void doctorsListWindowChanged(DoctorsListWindow doctorsListWindow, Object source) throws AppException;
}
