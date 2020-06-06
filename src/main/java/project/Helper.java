package project;

import javax.swing.*;

public class Helper {

    public static void showWarningDialog(JFrame window, String str) {
        JOptionPane.showMessageDialog(window, str, "Uwaga", JOptionPane.WARNING_MESSAGE);
    }

    public static int showConfirmDialog(String str) {
        return JOptionPane.showConfirmDialog(null, str, "Pytanie", JOptionPane.YES_NO_OPTION);
    }
}
