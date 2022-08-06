package UI;

import Driver.GenesisMain;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class UITextArea
{
    protected JTextPane textPane;
    protected Style style;
    protected StyledDocument doc;
    private AttributeSet defaultAttributeSet;

    public void setTextArea(JTextPane textArea)
    {
        textPane = textArea;
        style = textPane.addStyle("StyleGenesis", null);
        doc = textPane.getStyledDocument();
        defaultAttributeSet = textPane.getCharacterAttributes();
    }

    public void clearTheTextArea()
    {
        GenesisMain.cleanPreviousData();

        try
        {
            textPane.getDocument().remove(0, textPane.getDocument().getLength());
        }
        catch (BadLocationException ex)
        {
            ex.printStackTrace();
        }
    }

    protected void appendToPane(String msg, Color c)
    {
        StyleConstants.setForeground(style, c);

        try
        {
            doc.insertString(doc.getLength(), msg, style);
            textPane.update(textPane.getGraphics());
        }
        catch (BadLocationException e)
        {
            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤌𐤄𐤋𐤊 𐤄𐤍𐤉𐤎𐤉𐤅𐤍 𐤋𐤄𐤃𐤐𐤉𐤎 𐤋𐤌𐤎𐤊!");
        }
    }

    public AttributeSet getDefaultAttributeSet()
    {
        return defaultAttributeSet;
    }
}
