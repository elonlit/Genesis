package Commands;

import UI.UIManager;
import Utilities.NumberExpressionSolver;

public class Print implements ICommand
{
    protected void initiateCommand(String val)
    {
        UIManager.consoleInstance.print(val);
    }

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        boolean arrayFlag1 = false, arrayFlag2 = false;
        for(char c: par.toCharArray())
        {
            if(c == '[') arrayFlag1 = true;
            else if(c == ']') arrayFlag2 = true;
        }

        if(arrayFlag1 && arrayFlag2) // One value in array
        {
            double index = (double) new NumberExpressionSolver(par.substring(par.indexOf("[")+1, par.indexOf("]")), line).getResult();
            if(index % 1 != 0) UIManager.consoleInstance.printErrorMessage("𐤀𐤉𐤍𐤃𐤒𐤎 𐤋𐤀 𐤇𐤅𐤒𐤉 𐤋𐤌𐤏𐤓𐤊 𐤁𐤔𐤅𐤓𐤄 - " + line, line); // check that it is indeed always int
            Object[] theArray = (Object[]) Variable.getAVariableValue(par.substring(0, par.indexOf("[")));
            initiateCommand(theArray[(int)index].toString());
        }
        else if(par.startsWith("\"") && par.endsWith("\"")) // Quote
        {
            initiateCommand(par.substring(1, par.length()-1));
        }
        else
        {
            for(String varName: Variable.getVariablesNames()) // Variable
            {
                if(par.equals(varName))
                {
                    Object value = Variable.getAVariableValue(varName);
                    StringBuilder printValue = new StringBuilder();
                    if(value instanceof Boolean)
                    {
                        if(value.equals(true))
                        {
                            printValue = new StringBuilder("𐤀𐤌𐤕");//אמת
                        }
                        else
                        {
                            printValue = new StringBuilder("𐤔𐤒𐤓");//שקר
                        }
                    }
                    else if(value instanceof Object[]) // All array
                    {
                        for(Object a: (Object[]) value)
                        {
                            if(a instanceof Boolean)
                            {
                                if(a.equals(true))
                                {
                                    printValue.append("𐤀𐤌𐤕 ");
                                }
                                else
                                {
                                    printValue.append("𐤔𐤒𐤓 ");
                                }
                            }
                            else
                            {
                                printValue.append(a.toString()).append(" ");
                            }
                        }
                    }
                    else
                    {
                        printValue = new StringBuilder(value.toString());
                    }

                    initiateCommand(printValue.toString());
                    return;
                }
            }

            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);
        }
    }
}
