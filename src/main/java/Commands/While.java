package Commands;

import RoutineBlocks.RoutineBlock;
import Driver.Interpreter;
import UI.UIManager;
import Utilities.BooleanExpressionSolver;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class While implements ICommand
{
    private static final HashMap<Integer, RoutineBlock> loops = new HashMap<>(); // Tagged by the line number

    @Override
    public void sendParameters(@NotNull String par, int line, boolean preRun)
    {
        while(par.endsWith(" ")) par = par.substring(0, par.length()-1); // Removing whitespaces in the end of the line

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 ':' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is one ':'
        String condition = par.substring(0, par.indexOf(":"));
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤒𐤈𐤏 𐤋𐤀 𐤑𐤐𐤅𐤉 𐤋𐤀𐤇𐤓 𐤍𐤒𐤅𐤃𐤅𐤕𐤉𐤉𐤌 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        if(preRun)
        {
            RoutineBlock routineBlock = Interpreter.cutCodeBlock(line + 1, false);
            if (routineBlock == null) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤌𐤁𐤍𐤄 𐤔𐤋 𐤄𐤋𐤅𐤋𐤀𐤄, 𐤇𐤎𐤓 '𐤎𐤅𐤐', 𐤋𐤋𐤅𐤋𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);
            loops.put(line, routineBlock);
        }
        else
        {
            while((boolean) new BooleanExpressionSolver(condition, line).getResult())
            {
                loops.get(line).initiateCode();
            }
        }
    }

    public static void clearLoopsData()
    {
        loops.clear();
    }
}
