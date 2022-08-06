package Driver;

import Commands.*;
import UI.UIManager;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Collections;

public class GenesisMain
{
    public static void main(String[] args)
    {
        UIManager.openUI();
    }

    public static void readFromFile()
    {
        Runnable r = () -> {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jfc.setSelectedFile(new File("\uD802\uDD01.\uD802\uDD12\uD802\uDD05\uD802\uDD03"));
            jfc.setFileFilter(new FileNameExtensionFilter("Genesis Code Files", "\uD802\uDD01"));
            if( jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION ){
                File selected = jfc.getSelectedFile();
                System.out.println(selected + " chosen:");

                StringBuilder input = new StringBuilder();
                BufferedReader br = null;
                try {
                    br = new BufferedReader(new FileReader(selected));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                String line = null;
                while (true) {
                    try {
                        assert br != null;
                        if ((line = br.readLine()) == null) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    input.append(line);
                    System.out.println(line);
                }
                Interpreter.sendCodeToInterpret(Collections.singletonList(input.toString()));
            }
        };
        SwingUtilities.invokeLater(r);
    }

    public static void interpretCode()
    {
        Interpreter.sendCodeToInterpret(UIManager.codeEditorInstance.getTheCode());
    }

    public static void cleanPreviousData()
    {
        Variable.globalVariables.clear();
        Subroutine.functions.clear();
        While.clearLoopsData();
        For.clearLoopsData();
        ForEach.clearLoopsData();
    }
}
