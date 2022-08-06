package UI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class UIManager
{
    public static Console consoleInstance =  new Console();
    public static CodeEditor codeEditorInstance = new CodeEditor();

    public static void openUI()
    {
        SwingUtilities.invokeLater(() -> {
            try {
                JFrame.setDefaultLookAndFeelDecorated(true);
                JDialog.setDefaultLookAndFeelDecorated(true);
                new ProgramFrame().setVisible(true);
            } catch (IOException | FontFormatException e) {
                e.printStackTrace();
            }
        });
    }
}
