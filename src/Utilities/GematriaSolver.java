package Utilities;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static UI.CodeEditor.gematriaMap;

public class GematriaSolver {
    public static @NotNull String parseGematria(String lines){
        //search for a single geresh ׳
        lines = lines.replaceAll("𐤀׳", "1");
        lines = lines.replaceAll("𐤁׳", "2");
        lines = lines.replaceAll("𐤂׳", "3");
        lines = lines.replaceAll("𐤃׳", "4");
        lines = lines.replaceAll("𐤄׳", "5");
        lines = lines.replaceAll("𐤅׳", "6");
        lines = lines.replaceAll("𐤆׳", "7");
        lines = lines.replaceAll("𐤇׳", "8");
        lines = lines.replaceAll("𐤈׳", "9");
        lines = lines.replaceAll("𐤉׳", "10");
        lines = lines.replaceAll("𐤊׳", "20");
        lines = lines.replaceAll("𐤋׳", "30");
        lines = lines.replaceAll("𐤌׳", "40");
        lines = lines.replaceAll("𐤍׳", "50");
        lines = lines.replaceAll("𐤎׳", "60");
        lines = lines.replaceAll("𐤏׳", "70");
        lines = lines.replaceAll("𐤐׳", "80");
        lines = lines.replaceAll("𐤑׳", "90");
        lines = lines.replaceAll("𐤒׳", "100");
        lines = lines.replaceAll("𐤓׳", "200");
        lines = lines.replaceAll("𐤔׳", "300");
        lines = lines.replaceAll("𐤕׳", "400");
        //search for gershayim ״
        String linesCopy = lines;
        ArrayList<String> validGematricSequences = new ArrayList<>();
        String[] tokens = linesCopy.split("\\r?\\n| |\\)|\\(|\\+|\\-|\\/|\\*");

        for (String token : tokens) {
            if (token.contains("״")) {
                validGematricSequences.add(token.trim().replaceAll("״", "״״").replaceAll("\\s", ""));
            }
        }

        for (String sequence : validGematricSequences) {
            int value = 0;
            for (int j = 0; j < sequence.length() - 1; j += 2) {
                value += gematriaMap.get(sequence.substring(j, j + 2));
            }
            sequence = sequence.replaceAll("״״", "״");
            lines = lines.replaceAll(sequence, String.valueOf(value));
        }
        lines = lines.replaceAll("״״", "״");
        return lines;
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
}
