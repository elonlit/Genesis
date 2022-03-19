package UI;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class CodeFilter extends DocumentFilter
{
    @Override
    public void remove(FilterBypass fb, int offset, int length) throws BadLocationException
    {
        if (UIManager.codeEditorInstance.displayingError)
        {
            codeChanged();
        }

        super.remove(fb, offset, length);
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException
    {
        if (UIManager.codeEditorInstance.displayingError)
        {
            codeChanged();
        }

        super.replace(fb, offset, length, text, UIManager.codeEditorInstance.getDefaultAttributeSet());
    }

    private void codeChanged()
    {
        UIManager.codeEditorInstance.rewriteCleanCodeText();
    }
}
