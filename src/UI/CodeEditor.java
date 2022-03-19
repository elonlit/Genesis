package UI;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.*;

import static Utilities.GematriaSolver.parseGematria;
import static Utilities.VariableSolver.getVariables;

public class CodeEditor extends UITextArea
{
    private File codeFile;

    public boolean displayingError = false;
    public static HashSet<String> surrogateInterimVariables = new HashSet<>();
    public static HashMap<String, String> singleCodePointVariables = new HashMap<>();

    public static final HashMap<String, Integer> gematriaMap = new HashMap<>() {{
        put("״", 0);
        put("״״", 0);
        put("𐤀", 1);
        put("𐤁", 2);
        put("𐤂", 3);
        put("𐤃", 4);
        put("𐤄", 5);
        put("𐤅", 6);
        put("𐤆", 7);
        put("𐤇", 8);
        put("𐤈", 9);
        put("𐤉", 10);
        put("𐤊", 20);
        put("𐤋", 30);
        put("𐤌", 40);
        put("𐤍", 50);
        put("𐤎", 60);
        put("𐤏", 70);
        put("𐤐", 80);
        put("𐤑", 90);
        put("𐤒", 100);
        put("𐤓", 200);
        put("𐤔", 300);
        put("𐤕", 400);
    }};

    public void loadCodeFile(File file)
    {
        if(file == null) return;
        clearTheTextArea();
        codeFile = file;

        List<String> codeList = readFile(codeFile);
        assert codeList != null;
        for(String line: codeList)
        {
            appendToPane(line + "\n", Color.BLACK);
        }
    }

    public List<String> getTheCode(@NotNull String input) throws FileNotFoundException {
        String[] lines = parseGematria(input
                .replaceAll("\uD802\uDD13\uD802\uDD0D\uD802\uDD03()", Math.random() + "") //𐤓𐤍𐤃 (random)
                .replaceAll("\uD802\uDD10\uD802\uDD09\uD802\uDD09()", Math.PI + "") //𐤐𐤉𐤉 (pi)
                .replaceAll("\\(\\)", "")
                .replaceAll("\uD802\uDD00\uD802\uDD06", "\uD802\uDD00")) //doesn't parse as two surrogate code points
                .split("\\r?\\n"); // Split by new lines
        getVariables(lines);
        return Arrays.asList(lines);
    }

    public String getBibleQuote() throws FileNotFoundException {
        File f = new File("BibleQuotes.txt");
        String result = null;
        Random rand = new Random();
        int n = 0;
        for(Scanner sc = new Scanner(f); sc.hasNext(); )
        {
            ++n;
            String line = sc.nextLine();
            if(rand.nextInt(n) == 0)
                result = line;
        }
        return result;
    }

    public List<String> getTheCode()
    {
        String[] lines = parseGematria(textPane.getText()
                .replaceAll("\uD802\uDD13\uD802\uDD0D\uD802\uDD03()", Math.random() + "") //𐤓𐤍𐤃 (random)
                .replaceAll("\uD802\uDD10\uD802\uDD09\uD802\uDD09()", Math.PI + "") //𐤐𐤉𐤉 (pi)
                .replaceAll("\\(\\)", "")
                .replaceAll("\uD802\uDD00\uD802\uDD06", "\uD802\uDD00")) //doesn't parse as two surrogate code points
                .split("\\r?\\n"); // Split by new lines
        getVariables(lines);
        return Arrays.asList(lines);
    }

    public List<String> getNothing()
    {
        String[] lines = "".split("\\r?\\n"); // Split by new lines
        return Arrays.asList(lines);
    }

    public String getTheText()
    {
        return textPane.getText();
    }

    private @Nullable List<String> readFile(File file)
    {
        List<String> records = new ArrayList<>();
        try
        {
            FileInputStream inputStream = new FileInputStream(file); // Open a file reader object
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_16); // Wrap the file reader with InputStreamReader with UTF16 encoding
            BufferedReader reader = new BufferedReader(inputStreamReader); // Wrap it with buffered reader

            String line;
            while ((line = reader.readLine()) != null)
            {
                records.add(line);
            }
            reader.close();
            return records;
        }
        catch (Exception e)
        {
            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤌𐤄𐤋𐤊 𐤄𐤍𐤉𐤎𐤉𐤅𐤍 𐤋𐤒𐤓𐤅𐤀 𐤀𐤕 𐤄𐤒𐤅𐤁𐤑 𐤒𐤅𐤃!");
            e.printStackTrace();
            return null;
        }
    }

    public void saveFile()
    {
        if(codeFile != null)
        {
            writeToFile(codeFile);
        }
        else
        {
            saveFileAs();
        }
    }

    public void saveFileAs()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Genesis Code Files", "𐤁")); // Setting file type filter
        fileChooser.setSelectedFile(new File("\uD802\uDD12\uD802\uDD05\uD802\uDD03.𐤁")); // Setting suggested file name
        fileChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());

        if(fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            File file;
            if(!fileChooser.getSelectedFile().getName().endsWith("𐤁.")) file = new File(fileChooser.getSelectedFile() + "𐤁.");
            else file = fileChooser.getSelectedFile();

            writeToFile(file);
            codeFile = file;
        }
    }

    private void writeToFile(File file)
    {
        try
        {
            FileOutputStream outputStream = new FileOutputStream(file); // Open a file writer object
            OutputStreamWriter streamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_16); // Wrap the file writer with OutputStreamWriter with UTF16 encoding
            BufferedWriter writer = new BufferedWriter(streamWriter); // Wrap it with buffered writer

            for(String line: getTheCode())
            {
                writer.write(line);
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e)
        {
            UIManager.consoleInstance.printErrorMessage("𐤔𐤂𐤉𐤀𐤄 𐤁𐤌𐤄𐤋𐤊 𐤄𐤍𐤉𐤎𐤉𐤅𐤍 𐤋𐤔𐤌𐤅𐤓 𐤀𐤕 𐤄𐤒𐤅𐤁𐤑 𐤒𐤅𐤃");
            e.printStackTrace();
        }
    }

    public void rewriteCodeTextWithErrorHighlight(int line)
    {
        List<String> oldCode = getNothing();
        clearTheTextArea();

        String str;
        for(int i = 0; i < oldCode.size(); i++)
        {
            str = oldCode.get(i);

            if(i == line - 1) appendToPane(str + "\n", Color.RED);
            else appendToPane(str + "\n", Color.BLACK);
        }
        displayingError = true;
    }

    public void rewriteCleanCodeText()
    {
        displayingError = false;
        List<String> oldCode = getTheCode();
        int caretPosition = textPane.getCaretPosition();

        clearTheTextArea();

        for(String str: oldCode)
        {
            appendToPane(str + "\n", Color.BLACK);
        }
        textPane.setCaretPosition(caretPosition);
    }
}
