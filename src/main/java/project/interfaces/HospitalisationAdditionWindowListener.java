package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.HospitalisationAdditionWindow;

public interface HospitalisationAdditionWindowListener {
    void hospitalisationAdditionWindowChanged(HospitalisationAdditionWindow hospitalisationAdditionWindow,
                                              Object source) throws AppException;
}
