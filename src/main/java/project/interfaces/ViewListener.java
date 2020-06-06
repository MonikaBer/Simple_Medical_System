package project.interfaces;

import project.AppException;

import javax.swing.*;
import java.sql.SQLException;

public interface ViewListener {
	
	void viewChanged(JFrame jFrame, Object source) throws AppException;
}
