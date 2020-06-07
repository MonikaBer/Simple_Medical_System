package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.NewPatientAdditionWindow;

public interface NewPatientAdditionWindowListener {
    void newPatientAdditionWindowChanged(NewPatientAdditionWindow newPatientAdditionWindow,
                                         Object source) throws AppException;
}
