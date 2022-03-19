package References;

import Commands.*;

public enum GenesisKeyWords
{
    // Class instance, the command key word
    PRINT(new Print(), "𐤄𐤃𐤐𐤎"),
    PRINT_LINE(new PrintLine(), "𐤄𐤃𐤐𐤎𐤇"),
    VARIABLE(new Variable(), "𐤄𐤂𐤃𐤓"),
    VARIABLE_POST(new VariableUpdate(), "𐤀𐤕𐤇𐤅𐤋"),
    IF(new If(), "𐤀𐤌"),
    FUNCTION(new Subroutine(), "𐤐𐤅𐤍𐤒𐤑𐤉𐤄"),
    FUNCTION_CALL(new SubroutineCall(), "𐤄𐤐𐤏𐤋"),
    WHILE(new While(), "𐤁𐤏𐤅𐤃"),
    FOR(new For(), "𐤏𐤁𐤅𐤓"),
    FOR_EACH(new ForEach(), "𐤏𐤁𐤅𐤓𐤊𐤋"),
    SLEEP(new Sleep(), "𐤉𐤔𐤍");

    private final ICommand command;
    private final String genesisCode;

    GenesisKeyWords(ICommand com, String code)
    {
        this.command = com;
        this.genesisCode = code;
    }

    public String getGenesisCode()
    {
        return this.genesisCode;
    }

    public ICommand getCommand()
    {
        return this.command;
    }
}
