package Commands;

import UI.UIManager;
import Utilities.NumberExpressionSolver;

public class VariableUpdate implements ICommand
{
    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int eqCounter = 0;
        boolean arrayFlag1 = false, arrayFlag2 = false, arrayFlag3 = false, arrayFlag4 = false;
        for(char c: par.toCharArray())
        {
            if(c == '=') eqCounter++;
            else if(c == '}') arrayFlag1 = true;
            else if(c == '{') arrayFlag2 = true;
            else if(c == '[') arrayFlag3 = true;
            else if(c == ']') arrayFlag4 = true;
        }
        if(eqCounter == 0) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 '=' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is at least one '='
        String varName = par.substring(0, par.indexOf("="));
        String varValue = par.substring(par.indexOf("=") + 1);
        if(arrayFlag3 && arrayFlag4)
        {
            if(Variable.doesVariableExist(varName.substring(0, varName.indexOf("[")))) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤌𐤔𐤕𐤍𐤄 𐤔𐤋𐤀 𐤒𐤉𐤉𐤌 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Variable not found
        }
        else if(Variable.doesVariableExist(varName)) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤌𐤔𐤕𐤍𐤄 𐤔𐤋𐤀 𐤒𐤉𐤉𐤌 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Variable not found

        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            Variable.globalVariables.replace(varName, varValue.substring(1, varValue.length()-1));
            return;
        }

        if(arrayFlag1 && arrayFlag2) // All array update
        {
            varValue = varValue.replaceAll("\\s",""); // Remove all whitespaces
            varValue = varValue.substring(1, varValue.length()-1); // Remove '{' '}'
            String[] arrayValues = varValue.split(",");

            Object[] arrayVariable = new Object[arrayValues.length];
            for(int i = 0; i < arrayVariable.length; i++)
            {
                arrayVariable[i] = new NumberExpressionSolver(arrayValues[i], line).getResult();
            }

            Variable.globalVariables.replace(varName, arrayVariable);
            return;
        }

        if(arrayFlag3 && arrayFlag4) // One value in the array update
        {
            //int index = Integer.parseInt(varName.substring(varName.indexOf("[")+1, varName.indexOf("]")));
            double index = (double) new NumberExpressionSolver(varName.substring(varName.indexOf("[")+1, varName.indexOf("]")), line).getResult();
            if(index % 1 != 0) UIManager.consoleInstance.printErrorMessage("𐤀𐤉𐤍𐤃𐤒𐤎 𐤋𐤀 𐤇𐤅𐤒𐤉 𐤋𐤌𐤏𐤓𐤊 𐤁𐤔𐤅𐤓𐤄 - " + line, line); // check that it is indeed always int
            Object[] newArray = (Object[]) Variable.getAVariableValue(varName.substring(0, varName.indexOf("[")));
            newArray[(int)index] = new NumberExpressionSolver(varValue, line).getResult();
            Variable.globalVariables.replace(varName.substring(0, varName.indexOf("[")), newArray);
        }

        else if(varValue.equals("𐤀𐤌𐤕")) // Boolean True אמת
        {
            Variable.globalVariables.replace(varName, true);
            return;
        }
        else if(varValue.equals("𐤔𐤒𐤓")) // Boolean False שקר
        {
            Variable.globalVariables.replace(varName, false);
            return;
        }

        for(String name: Variable.getVariablesNames()) // Other Variable
        {
            if(varValue.equals(name))
            {
                Variable.globalVariables.replace(varName, Variable.getAVariableValue(varValue));
                return;
            }
        }

        Variable.globalVariables.replace(varName, new NumberExpressionSolver(varValue, line).getResult()); // If not number this check for boolean as well
    }
}
