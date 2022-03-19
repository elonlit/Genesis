package Commands;

import Driver.Interpreter;
import UI.UIManager;
import Utilities.BooleanExpressionSolver;

public class If implements ICommand
{
    @Override
    public void sendParameters(String par, int lineNumber, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);

        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        if(!par.contains(" 𐤀 ")) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 '𐤀𐤆' 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);
        String condition = par.substring(0, par.indexOf(" 𐤀 "));
        String command = par.substring(par.indexOf(" 𐤀 ") + 4);
        //doesn't parse as surrogate pair

        condition = condition.replaceAll("\\s",""); // Remove all whitespaces
        if((boolean) new BooleanExpressionSolver(condition, lineNumber).getResult()) Interpreter.initiateLine(command, lineNumber);
    }
}
