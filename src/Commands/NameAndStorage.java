package Commands;

import References.GenesisKeyWords;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public abstract class NameAndStorage
{
    protected static boolean isNameValid(String name)
    {
        for(GenesisKeyWords keyWord: GenesisKeyWords.values()) // Command names that are already taken
        {
            if(name.equals(keyWord.getGenesisCode()))
            {
                return true;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variable names that are already taken
        {
            if(name.equals(varName))
            {
                return true;
            }
        }

        for(String funcName: Subroutine.getFunctionsNames()) // Function names that are already taken
        {
            if(name.equals(funcName))
            {
                return true;
            }
        }

        return false;
    }

    protected static boolean doesThisNameExistInStorage(@NotNull HashMap<String, ?> storage, String name)
    {
        for(String funcName: storage.keySet())
        {
            if(name.equals(funcName)) return true;
        }
        return false;
    }
}
