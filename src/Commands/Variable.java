package Commands;

import Driver.Interpreter;
import UI.UIManager;
import Utilities.NumberExpressionSolver;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

import static UI.CodeEditor.singleCodePointVariables;

public class Variable extends NameAndStorage implements ICommand
{
    public static HashMap<String, Object> globalVariables = new HashMap<>();

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        int eqCounter = 0;
        boolean arrayFlag1 = false, arrayFlag2 = false;
        for(char c: par.toCharArray())
        {
            if(c == '=') eqCounter++;
            else if(c == '}') arrayFlag1 = true;
            else if(c == '{') arrayFlag2 = true;
        }
        if(eqCounter == 0) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 '=' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is at least one '='
        String varName = par.substring(0, par.indexOf("="));
        String varValue = par.substring(par.indexOf("=") + 1);

        varName = varName.replaceAll("\\s",""); // Remove all whitespaces
        if(isNameValid(varName)) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤔𐤌 𐤌𐤔𐤕𐤍𐤄 𐤋𐤀 𐤇𐤅𐤒𐤉 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is at least one '='

        while(varValue.startsWith(" ")) varValue = varValue.substring(1);
        while(varValue.endsWith(" ")) varValue = varValue.substring(0, varValue.length()-1);
        if(varValue.startsWith("\"") && varValue.endsWith("\"") && varValue.length() != 1) // String
        {
            globalVariables.put(varName, varValue.substring(1, varValue.length()-1));
            if(Interpreter.runningRoutineBlock != null) Interpreter.runningRoutineBlock.reportLocalVariable(varName); // In case of local variables
            return;
        }

        varValue = varValue.replaceAll("\\s",""); // Remove all whitespaces
        if(arrayFlag1 && arrayFlag2) // Array creation
        {
            varValue = varValue.substring(1, varValue.length()-1); // Remove '{' '}'
            String[] arrayValues = varValue.split(",");

            Object[] arrayVariable = new Object[arrayValues.length];
            for(int i = 0; i < arrayVariable.length; i++)
            {
                arrayVariable[i] = new NumberExpressionSolver(arrayValues[i], line).getResult();
            }

            globalVariables.put(varName, arrayVariable);
            if(Interpreter.runningRoutineBlock != null) Interpreter.runningRoutineBlock.reportLocalVariable(varName); // In case of local variables
            return;
        }

        Variable.globalVariables.put(varName, new NumberExpressionSolver(varValue, line).getResult()); // If not number this check for boolean as well
        if(Interpreter.runningRoutineBlock != null) Interpreter.runningRoutineBlock.reportLocalVariable(varName); // In case of local variables
    }

    public static boolean doesVariableExist(String name)
    {
        return !doesThisNameExistInStorage(globalVariables, name);
    }

    @Contract(pure = true)
    public static @NotNull Set<String> getVariablesNames()
    {
        return globalVariables.keySet();
    }

    public static Object getAVariableValue(String varName)
    {
        return globalVariables.get(varName);
    }

    public static String[] globalVariablesLog()
    {
        StringBuilder logOutput = new StringBuilder();
        for(String varKey: globalVariables.keySet())
        {
            if(globalVariables.get(varKey) instanceof Object[]) // Array
            {
                logOutput.append(varKey);
            }
            else
            {
                logOutput.append(varKey);
                logOutput.append("=");
                logOutput.append(globalVariables.get(varKey).toString());
            }

            logOutput.append(", ");
        }

        if (globalVariables.size() > 0) {
            logOutput.delete(logOutput.length() - 2, logOutput.length() - 1); // Delete last " ,"
        }
        final String[] logOutputStr = {logOutput.toString()};

        singleCodePointVariables.forEach((singlePoint, doublePoint) -> logOutputStr[0] = logOutputStr[0].replaceAll(doublePoint, singlePoint));

        return logOutputStr;
    }
}