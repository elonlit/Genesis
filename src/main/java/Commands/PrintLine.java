package Commands;

import UI.UIManager;

public class PrintLine extends Print
{
    @Override
    protected void initiateCommand(String val)
    {
        UIManager.consoleInstance.println(val);
    }
}
