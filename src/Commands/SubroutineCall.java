package Commands;

import UI.UIManager;

public class SubroutineCall implements ICommand
{
    @Override
    public void sendParameters(String par, int line, boolean preRun)
    {
        if(preRun) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line);

        par = par.replaceAll("\\s",""); // Remove all whitespaces
        if(!Subroutine.doesFunctionExist(par)) UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤓𐤌𐤈𐤓𐤉𐤌 𐤔𐤋 𐤄𐤐𐤒𐤅𐤃𐤄, 𐤐𐤅𐤍𐤒𐤑𐤉𐤄 𐤔𐤋𐤀 𐤒𐤉𐤉𐤌𐤕 𐤀𐤅 𐤈𐤏𐤅𐤕 𐤊𐤕𐤉𐤁𐤄 𐤁𐤔𐤅𐤓𐤄: " + line, line); // Function not found

        Subroutine.functions.get(par).duplicate().initiateCode();
    }
}
