package project.interfaces;

import project.AppException;
import project.view.windows.otherWindows.VisitAdditionWindow;

public interface VisitAdditionWindowListener {
    void visitAdditionWindowChanged(VisitAdditionWindow visitAdditionWindow, Object source) throws AppException;
}
