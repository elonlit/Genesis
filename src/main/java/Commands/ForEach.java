package Commands;

import RoutineBlocks.RoutineBlock;
import Driver.Interpreter;
import UI.UIManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class ForEach implements ICommand
{
    private static final HashMap<Integer, RoutineBlock> loops = new HashMap<>(); // Tagged by the line number

    @Override
    public void sendParameters(@NotNull String par, int line, boolean preRun)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int commCounter = 0;
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
            else if(c == ',') commCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 ':' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is one ':'
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤒𐤈𐤏 𐤋𐤀 𐤑𐤐𐤅𐤉 𐤋𐤀𐤇𐤓 𐤍𐤒𐤅𐤃𐤅𐤕𐤉𐤉𐤌 𐤁𐤔𐤅𐤓𐤄: " + line, line);
        par = par.substring(0, par.indexOf(":"));

        if(commCounter != 1) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 ',' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is one ':'
        String[] loopValues = par.split(","); // 0 = holder variable,  1 = the array

        if(preRun)
        {
            RoutineBlock routineBlock = Interpreter.cutCodeBlock(line + 1, false);
            if (routineBlock == null) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤌𐤁𐤍𐤄 𐤔𐤋 𐤄𐤋𐤅𐤋𐤀𐤄, 𐤇𐤎𐤓 '𐤎𐤅𐤐', 𐤋𐤋𐤅𐤋𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

            loops.put(line, routineBlock);
        }
        else
        {
            if(NameAndStorage.isNameValid(loopValues[0])) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤔𐤌 𐤌𐤔𐤕𐤍𐤄 𐤋𐤀 𐤇𐤅𐤒𐤉 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is at least one '='
            Variable.globalVariables.put(loopValues[0], null);

            Object[] theArray;
            if(Variable.globalVariables.get(loopValues[1]) != null)
            {
                theArray = (Object[]) Variable.globalVariables.get(loopValues[1]);
            }
            else
            {
                UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤔𐤌 𐤌𐤏𐤓𐤊 𐤋𐤀 𐤇𐤅𐤒𐤉: " + line, line);
                return;
            }

            for (Object o : theArray) {
                Variable.globalVariables.replace(loopValues[0], o);
                loops.get(line).initiateCode();
            }

            Variable.globalVariables.remove(loopValues[0]);
        }
    }

    public static void clearLoopsData()
    {
        loops.clear();
    }
}
