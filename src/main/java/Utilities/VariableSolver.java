package Utilities;

import Commands.Variable;
import org.jetbrains.annotations.NotNull;

import static UI.CodeEditor.singleCodePointVariables;
import static UI.CodeEditor.surrogateInterimVariables;

public class VariableSolver {
    public static void getVariables(String @NotNull [] lines){
        for (String line : lines) {
            if(line.contains("\uD802\uDD04\uD802\uDD02\uD802\uDD03\uD802\uDD13")){ //𐤄𐤂𐤃𐤓
                String[] tokens = line.split("\\r?\\n| |=|\\)|\\(|\\+|\\-|\\/|\\*|\\{|\\}|\\,");
                String variable = tokens[1];
                surrogateInterimVariables.add(variable);
            }
            if(line.contains("\uD802\uDD0F\uD802\uDD01\uD802\uDD05\uD802\uDD13")){ //𐤏𐤁𐤅𐤓
                String[] tokens = line.split("\\r?\\n| |=|\\)|\\(|\\+|\\-|\\/|\\*|\\{|\\}|\\,");
                String loopVar = tokens[1];
                surrogateInterimVariables.add(loopVar);
            }
        }

        surrogateInterimVariables.remove("\uD802\uDD04\uD802\uDD02\uD802\uDD03\uD802\uDD13");

        for(int i=0; i<surrogateInterimVariables.size(); i++){
            String surrCodePointStr = surrogateInterimVariables.toArray()[i] + "";
            String singleCodePointStr = toSingleCodePointHebrew(surrCodePointStr);
            singleCodePointVariables.put(surrCodePointStr, singleCodePointStr);
        }

        //iterate over each surrogate code point var and replace its equivalent in lines[]
        //with its value (modern Hebrew equivalent) in the singleCodePointVariables HashMap.

        for(int i=0; i<lines.length; i++){
            for(String key : singleCodePointVariables.keySet()){
                lines[i] = lines[i].replaceAll(key, singleCodePointVariables.get(key));
            }
        }
    }

    public static @NotNull String toSingleCodePointHebrew(String line){
        //make into number equivalent, then hebrew chars
        String valueSurrogateStr = ((int)((Math.random()*100000)+1)) + "";
        valueSurrogateStr = valueSurrogateStr
                .replaceAll("0", "א")
                .replaceAll("1", "ט")
                .replaceAll("2", "ו")
                .replaceAll("3", "ן")
                .replaceAll("4", "ם")
                .replaceAll("5", "פ")
                .replaceAll("6", "ד")
                .replaceAll("7", "ג")
                .replaceAll("8", "כ")
                .replaceAll("9", "ע");
        return valueSurrogateStr;
    }

    public static void replaceVariables(String @NotNull [] lines){
        //get right assignment side (to the left of the = sign)
        for(int i=0; i<lines.length; i++){
            String line = lines[i];
            if(line.contains("=<") || line.contains("=>") ||
                    line.contains("=!") || line.contains("==")){
                int idxEquals = line.indexOf("=");
            } else if(line.contains("||") || line.contains("&&") ||
                    line.contains(">") || line.contains("<")){

            } else if(line.contains("=")){
                int idxOfEquals = line.indexOf("=");
                String rightSideOfLine = line.substring(idxOfEquals);
                String leftSideOfLine = line.substring(0, idxOfEquals);
                String[] tokens = rightSideOfLine.split("\\r?\\n| |\\)|\\(|\\+|\\-|\\/|\\*|\\{|\\}");

                for (String token : tokens) {
                    for (String varName : Variable.getVariablesNames()) {
                        if (token.contains(varName)) {
                            rightSideOfLine = rightSideOfLine.replaceAll(varName, Variable.getAVariableValue(varName) + "");
                        }
                    }
                }
                String newLine = leftSideOfLine + rightSideOfLine;
                lines[i] = newLine;
            }
        }
    }
}
