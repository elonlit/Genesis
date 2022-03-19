package RoutineBlocks;

import Commands.Variable;
import Driver.Interpreter;
import References.GenesisKeyWords;
import UI.UIManager;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineBlock
{
    private final String[] code;
    private final int startLineNumber;

    private final ArrayList<String> localVariables; // Stores the names of the local variables
    private HashMap<String, Object> variablesSnapshotForRecursion;
    private RoutineBlock blockSnapshotForRecursion;

    public RoutineBlock(String[] code, int startLineNumber)
    {
        this.code = code;
        this.startLineNumber = startLineNumber;
        localVariables = new ArrayList<>();

        checkForInvalidCode();
    }

    public void initiateCode()
    {
        boolean inRecursion = false;
        if(Interpreter.runningRoutineBlock != null)
        {
            createVariablesSnapshot(Interpreter.runningRoutineBlock.localVariables); // Save last code block variables
            cleanLocalVariables(Interpreter.runningRoutineBlock.localVariables); // Delete last code block variables from access
            blockSnapshotForRecursion = Interpreter.runningRoutineBlock; // Save last code block pointer
            inRecursion = true;
        }

        Interpreter.runningRoutineBlock = this;

        for(int i = 0; i < this.code.length; i++)
        {
            Interpreter.initiateLine(code[i], startLineNumber + i);
        }

        cleanLocalVariables(this.localVariables); // Deleting the local variable from the "globalVariables" hashMap
        localVariables.clear();

        if(!inRecursion) Interpreter.runningRoutineBlock = null;
        else
        {
            loadVariablesSnapshot(); // Reload the saved variables of the last code block
            Interpreter.runningRoutineBlock = blockSnapshotForRecursion; // Reset the pointer to the code block
        }
    }

    public void reportLocalVariable(String varName)
    {
        localVariables.add(varName);
    }

    private void cleanLocalVariables(@NotNull ArrayList<String> list)
    {
        for(String varName: list)
        {
            Variable.globalVariables.remove(varName);
        }
    }

    private void checkForInvalidCode()
    {
        for(int i = 0; i < this.code.length; i++)
        {
            if(this.code[i].startsWith(GenesisKeyWords.FUNCTION.getGenesisCode() + " ")) UIManager.consoleInstance.printErrorMessage("𐤀𐤎𐤅𐤓 𐤋𐤄𐤂𐤃𐤉𐤓 𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤁𐤕𐤅𐤊 𐤐𐤅𐤍𐤒𐤑𐤉𐤄 - 𐤔𐤅𐤓𐤄: " + startLineNumber + i, startLineNumber + i);
        }
    }

    private void createVariablesSnapshot(@NotNull ArrayList<String> localVariablesList)
    {
        variablesSnapshotForRecursion = new HashMap<>(); // Also reset previous values

        for(String varName: localVariablesList)
        {
            variablesSnapshotForRecursion.put(varName, Variable.globalVariables.get(varName));
        }
    }

    private void loadVariablesSnapshot()
    {
        for(String varName: variablesSnapshotForRecursion.keySet())
        {
            Variable.globalVariables.put(varName, variablesSnapshotForRecursion.get(varName));
        }
    }

    public String[] getCode()
    {
        return code;
    }

    public int getStartLineNumber()
    {
        return startLineNumber;
    }

    public RoutineBlock duplicate()
    {
        return new RoutineBlock(this.code, this.startLineNumber);
    }
}
