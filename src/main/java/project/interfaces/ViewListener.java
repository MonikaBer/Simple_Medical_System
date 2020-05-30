package project.interfaces;

import javax.swing.*;
import java.sql.SQLException;

public interface ViewListener {
	
	public void viewChanged(JFrame jFrame, Object source) throws SQLException;
}
