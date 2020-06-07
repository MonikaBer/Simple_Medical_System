package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.MedicalTestResultAdditionWindow;

public interface MedicalTestResultAdditionWindowListener {
    void medicalTestResultAdditionWindowChanged(MedicalTestResultAdditionWindow medicalTestResultAdditionWindow,
                                                Object source) throws AppException;
}
