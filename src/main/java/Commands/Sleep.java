package Commands;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import UI.UIManager;
import Utilities.NumberExpressionSolver;

public class Sleep implements ICommand
{
    @Override
    public void sendParameters(String par, int lineNumber, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);

        Calendar calendar = Calendar.getInstance();
        // You may only invoke the sleep command on Shabbat
        if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            UIManager.consoleInstance.printErrorMessage("𐤌𐤅𐤕𐤓 𐤋𐤉𐤔𐤅𐤍 𐤓𐤒 𐤁𐤔𐤁𐤕: " + lineNumber, lineNumber);
            return;
        }

        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line
        par = par.replaceAll("\\s",""); // Remove all whitespaces
        Object result = new NumberExpressionSolver(par, lineNumber).getResult();
        if (result instanceof Double)
        {
            try
            {
                TimeUnit.SECONDS.sleep(((Double) result).intValue());
            }
            catch (InterruptedException e)
            {
                UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤁𐤉𐤑𐤅𐤏 𐤄𐤌𐤕𐤍𐤄 𐤔𐤋 𐤐𐤒𐤅𐤃𐤕 𐤇𐤊𐤄, 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);
            }
        }
        else
        {
            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤃𐤓𐤅𐤔 𐤌𐤎𐤐𐤓 𐤋𐤁𐤉𐤈𐤅𐤉 𐤇𐤊𐤄, 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);
        }
    }
}
