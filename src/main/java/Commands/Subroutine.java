package Commands;

import Driver.Interpreter;
import UI.UIManager;
import RoutineBlocks.RoutineBlock;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public class Subroutine extends NameAndStorage implements ICommand
{
    public static HashMap<String, RoutineBlock> functions = new HashMap<>();

    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(!preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        int dotsCounter = 0;
        for(char c: par.toCharArray())
        {
            if(c == ':') dotsCounter++;
        }
        if(dotsCounter != 1) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤇𐤎𐤓 ':' 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is one ':'
        String fucName = par.substring(0, par.indexOf(":"));
        if(!par.substring(par.indexOf(":") + 1).isEmpty()) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤒𐤈𐤏 𐤋𐤀 𐤑𐤐𐤅𐤉 𐤋𐤀𐤇𐤓 𐤍𐤒𐤅𐤃𐤅𐤕𐤉𐤉𐤌 𐤁𐤔𐤅𐤓𐤄: " + line, line);
        if(isNameValid(fucName)) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤔𐤌 𐤄𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤋𐤀 𐤇𐤅𐤒𐤉 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Make sure there is at least one '='

        RoutineBlock routineBlock = Interpreter.cutCodeBlock(line + 1, true);
        if(routineBlock == null) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤌𐤁𐤍𐤄 𐤔𐤋 𐤄𐤐𐤅𐤍𐤒𐤑𐤉𐤄, 𐤇𐤎𐤓 '𐤎𐤅𐤐', 𐤋𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        functions.put(fucName, routineBlock);
    }

    @Contract(pure = true)
    public static @NotNull Set<String> getFunctionsNames()
    {
        return functions.keySet();
    }

    public static boolean doesFunctionExist(String name)
    {
        return doesThisNameExistInStorage(functions, name);
    }
}
