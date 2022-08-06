package Driver;

import Commands.Subroutine;
import Commands.Variable;
import References.GenesisKeyWords;
import UI.UIManager;
import RoutineBlocks.RoutineBlock;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Interpreter
{
    private static final String CODE_BLOCK_END_KEY_WORD = "\uD802\uDD12-\uD802\uDD03-\uD802\uDD14";//𐤒-𐤃-𐤔

    public static RoutineBlock runningRoutineBlock = null;

    private static List<String> theCurrentCode; // All the code file that we what to interpret in lines
    private static final ArrayList<Integer> cutedCodeLines = new ArrayList<>(); // "Black List" - code lines we need to ignore while running the code

    public static void sendCodeToInterpret(List<String> code)
    {
        // Initializing
        runningRoutineBlock = null;
        cutedCodeLines.clear();
        theCurrentCode = code;

        // Code handling
        scanForCodeBlocks();

        for(int i = 0; i < theCurrentCode.size(); i++)
        {
            if(!cutedCodeLines.contains(i + 1)){
                initiateLine(theCurrentCode.get(i), i + 1);
            }
        }

        // Logging
        UIManager.consoleInstance.printLogMessage("\n" + "\uD802\uDD0C\uD802\uDD14\uD802\uDD15\uD802\uDD0D\uD802\uDD09\uD802\uDD0C:" + "\n" + Arrays.toString(Variable.globalVariablesLog())); // For debugging only!
        UIManager.consoleInstance.printLogMessage("\n" + "\uD802\uDD10\uD802\uDD05\uD802\uDD0D\uD802\uDD12\uD802\uDD11\uD802\uDD09\uD802\uDD05\uD802\uDD15:" + "\n" + Subroutine.getFunctionsNames()); // For debugging only!
        GenesisMain.cleanPreviousData();
    }

    public static @NotNull String replaceVariableNamesWithValues(@NotNull String line){
        //get right assignment side (to the left of the = sign)
        if(line.indexOf("=") != -1){ //remember to add increment
            int idxOfEquals = line.indexOf("=");
            String rightSideOfLine = line.substring(idxOfEquals+1);
            String leftSideOfLine = line.substring(0, idxOfEquals);
            String[] tokens = rightSideOfLine.split("\\r?\\n|\s| |\\)|\\(|\\+|\\-|\\/|\\*|\\{|\\}");
            int idx = 0;
            for (String token : tokens) {
                for (String varName : Variable.getVariablesNames()) {
                    if (token.contains(varName)) {
                        rightSideOfLine = rightSideOfLine.replaceAll(varName, Variable.getAVariableValue(varName) + "");
                        idx++;
                    }
                }
            }
            //rightSideOfLine = reverseString(rightSideOfLine);
            if(idx == 0){
                return line;
            }
            line = rightSideOfLine + " = " + leftSideOfLine;
            line = line.trim();

            return line;
        } else{
            return line;
        }
    }

    @Contract("_ -> new")
    public static @NotNull String reverseString(@NotNull String s) {
        char[] chars = new char[s.length()];
        boolean twoCharCodepoint = false;
        for (int i = 0; i < s.length(); i++) {
            chars[s.length() - 1 - i] = s.charAt(i);
            if (twoCharCodepoint) {
                swap(chars, s.length() - 1 - i, s.length() - i);
            }
            twoCharCodepoint = !Character.isBmpCodePoint(s.codePointAt(i));
        }
        return new String(chars);
    }

    private static void swap(char @NotNull [] array, int i, int j) {
        char temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    public static void initiateLine(@NotNull String line, int lineNumber)
    {
        if(line.isEmpty()) return;
        while(line.startsWith(" ") || line.startsWith("\t")) line = line.substring(1); // Removing whitespaces and tabs in the beginning of the line

        for(GenesisKeyWords keyWord: GenesisKeyWords.values()) // Commands
        {
            if(line.startsWith(keyWord.getGenesisCode() + " "))
            {
                line = line.substring(keyWord.getGenesisCode().length() + 1); // Cutting the command part from the line and leaving only the parameters
                keyWord.getCommand().sendParameters(line, lineNumber, false);
                return;
            }
        }

        for(String varName: Variable.getVariablesNames()) // Variables
        {
            if(line.startsWith(varName))
            {
                GenesisKeyWords.VARIABLE_POST.getCommand().sendParameters(line, lineNumber, false);
                return;
            }
        }

        for(String funcName: Subroutine.getFunctionsNames()) // Functions (calls)
        {
            if(line.startsWith(funcName))
            {
                GenesisKeyWords.FUNCTION_CALL.getCommand().sendParameters(line, lineNumber, false);
                return;
            }
        }

        UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤏𐤌 𐤄𐤐𐤒𐤅𐤃𐤄 𐤁𐤔𐤅𐤓𐤄: " + lineNumber, lineNumber);
    }

    private static void scanForCodeBlocks()
    {
        int lineNumber = 0;

        for(String line: theCurrentCode)
        {
            lineNumber++;

            if(line.startsWith(GenesisKeyWords.FUNCTION.getGenesisCode() + " ") && !cutedCodeLines.contains(lineNumber))
            {
                line = line.substring(GenesisKeyWords.FUNCTION.getGenesisCode().length() + 1); // Cutting the command part from the line and leaving only the parameters
                GenesisKeyWords.FUNCTION.getCommand().sendParameters(line, lineNumber, true);
            }
            else if(line.startsWith(GenesisKeyWords.WHILE.getGenesisCode() + " ") && !cutedCodeLines.contains(lineNumber))
            {
                line = line.substring(GenesisKeyWords.WHILE.getGenesisCode().length() + 1); // Cutting the command part from the line and leaving only the parameters
                GenesisKeyWords.WHILE.getCommand().sendParameters(line, lineNumber, true);
            }
            else if(line.startsWith(GenesisKeyWords.FOR.getGenesisCode() + " ") && !cutedCodeLines.contains(lineNumber))
            {
                line = line.substring(GenesisKeyWords.FOR.getGenesisCode().length() + 1); // Cutting the command part from the line and leaving only the parameters
                GenesisKeyWords.FOR.getCommand().sendParameters(line, lineNumber, true);
            }
            else if(line.startsWith(GenesisKeyWords.FOR_EACH.getGenesisCode() + " ") && !cutedCodeLines.contains(lineNumber))
            {
                line = line.substring(GenesisKeyWords.FOR_EACH.getGenesisCode().length() + 1); // Cutting the command part from the line and leaving only the parameters
                GenesisKeyWords.FOR_EACH.getCommand().sendParameters(line, lineNumber, true);
            }
        }
    }

    public static @Nullable
    RoutineBlock cutCodeBlock(int startLine, boolean cutTheNameLine)
    {
        // startLine and cutedCodeLines number are like the user see, start in 1, while theCurrentCode line numbers are starting from 0
        for(int i = startLine; i < theCurrentCode.size() + 1; i++)
        {
            if(theCurrentCode.get(i - 1).replaceAll("\\s","").equals(CODE_BLOCK_END_KEY_WORD))
            {
                String[] block = new String[i - startLine];
                int j = startLine;
                for(; j < i; j++)
                {
                    block[j - startLine] = theCurrentCode.get(j - 1);
                    cutedCodeLines.add(j);
                }

                if(cutTheNameLine) cutedCodeLines.add(startLine - 1); // The function name line
                cutedCodeLines.add(j); // The CODE_BLOCK_END_KEY_WORD line
                return new RoutineBlock(block, startLine);
            }
        }
        return null;
    }
}
