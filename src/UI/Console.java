package UI;

import Driver.GenesisMain;

import java.awt.*;

public class Console extends UITextArea
{
    public void print(String str)
    {
        appendToPane(str, Color.BLACK);
    }

    public void println(String str)
    {
        appendToPane(str + "\n", Color.BLACK);
    }

    public void println()
    {
        appendToPane("\n", Color.BLACK);
    }

    public void printErrorMessage(String error, int line) throws RuntimeException
    {
        appendToPane(error + "\n", Color.RED);

        UIManager.codeEditorInstance.rewriteCodeTextWithErrorHighlight(line);

        GenesisMain.cleanPreviousData();
        throw new RuntimeException();
    }

    public void printErrorMessage(String error) throws RuntimeException
    {
        appendToPane(error + "\n", Color.RED);

        GenesisMain.cleanPreviousData();
        throw new RuntimeException();
    }

    public void printLogMessage(String message) throws RuntimeException
    {
        appendToPane(message + "\n", Color.BLUE);
    }
}
