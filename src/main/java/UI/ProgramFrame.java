package UI;

import Driver.GenesisMain;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ProgramFrame extends JFrame
{
    private static final JButton buttonStart = new JButton("\uD802\uDD04\uD802\uDD13\uD802\uDD11 \uD802\uDD12\uD802\uDD05\uD802\uDD03");
    private static final JButton buttonClear = new JButton("\uD802\uDD0D\uD802\uDD12\uD802\uDD04 \uD802\uDD10\uD802\uDD0B\uD802\uDD08");
    private static final JButton buttonLoad = new JButton("\uD802\uDD01\uD802\uDD07\uD802\uDD13 \uD802\uDD12\uD802\uDD05\uD802\uDD01\uD802\uDD11");
    private static final JButton buttonSave = new JButton("\uD802\uDD14\uD802\uDD0C\uD802\uDD05\uD802\uDD13");
    private static final JButton buttonSaveAs = new JButton("\uD802\uDD14\uD802\uDD0C\uD802\uDD05\uD802\uDD13 \uD802\uDD01\uD802\uDD14\uD802\uDD0C");

    private static final JLabel codeHead = new JLabel("\uD802\uDD12\uD802\uDD05\uD802\uDD03");
    private static final JLabel consoleHead = new JLabel("\uD802\uDD10\uD802\uDD0B\uD802\uDD08");

    private final UndoManager undo = new UndoManager(); //instantiate an UndoManager

    public ProgramFrame() throws IOException, FontFormatException {
        super("Genesis Interpreter");

        buttonStart.setBorderPainted(true);
        buttonStart.setFocusPainted(true);
        buttonStart.setContentAreaFilled(false);

        buttonClear.setBorderPainted(true);
        buttonClear.setFocusPainted(true);
        buttonClear.setContentAreaFilled(false);

        buttonLoad.setBorderPainted(true);
        buttonLoad.setFocusPainted(true);
        buttonLoad.setContentAreaFilled(false);

        buttonSave.setBorderPainted(true);
        buttonSave.setFocusPainted(true);
        buttonSave.setContentAreaFilled(false);

        buttonSaveAs.setBorderPainted(true);
        buttonSaveAs.setFocusPainted(true);
        buttonSaveAs.setContentAreaFilled(false);

        // Setting the text areas
        JTextPane consoleArea = new JTextPane();
        consoleArea.setEditable(false);
        consoleArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        UIManager.consoleInstance.setTextArea(consoleArea);

        JTextPane codeArea = new JTextPane();
        codeArea.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        UIManager.codeEditorInstance.setTextArea(codeArea);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("./src/main/java/UI/CODE2001.TTF")));

        // Setting the fonts - font, font style, font size
        Font font = new Font("Code2001", Font.PLAIN, 14);

        codeHead.setFont(font);
        consoleHead.setFont(font);
        buttonStart.setFont(font);
        buttonClear.setFont(font);
        buttonLoad.setFont(font);
        buttonSave.setFont(font);
        buttonSaveAs.setFont(font);
        consoleArea.setFont(font);
        codeArea.setFont(font);

        // Setting the code editor document filter
        AbstractDocument document = (AbstractDocument) codeArea.getDocument();
        document.setDocumentFilter(new CodeFilter());

        // Creates the GUI
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.anchor = GridBagConstraints.WEST;

        // Buttons
        add(buttonStart, constraints);

        constraints.gridx = 1;
        add(buttonClear, constraints);

        constraints.gridx = 2;
        add(buttonLoad, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(buttonSave, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        add(buttonSaveAs, constraints);

        // Text Areas position and placing
        constraints.gridx = 2;
        constraints.gridy = 2;
        add(codeHead, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        add(consoleHead, constraints);

        // Scrolls
        JScrollPane scrollPane = new JScrollPane(codeArea);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        add(scrollPane, constraints);

        scrollPane = new JScrollPane(consoleArea);
        scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        constraints.gridy = 3;
        add(scrollPane, constraints);

        // Adds event handler for the Start button
        buttonStart.addActionListener(evt -> {
            UIManager.consoleInstance.clearTheTextArea();
            System.out.print("\033[H\033[2J");
            System.out.flush();
            GenesisMain.interpretCode();
        });

        // Adds event handler for the Clear button
        buttonClear.addActionListener(evt -> UIManager.consoleInstance.clearTheTextArea());

        // Adds event handler for the Load button
        buttonLoad.addActionListener(evt -> UIManager.codeEditorInstance.loadCodeFile(openFile()));

        // Adds event handler for the Save button
        buttonSave.addActionListener(evt -> UIManager.codeEditorInstance.saveFile());

        // Adds event handler for the Save-As button
        buttonSaveAs.addActionListener(evt -> UIManager.codeEditorInstance.saveFileAs());

        // Adds handler for drag-and-drop event, for dropping code files on the code editor
        codeArea.setDropTarget(new DropTarget() {
            @Override
            public synchronized void drop(DropTargetDropEvent event)
            {
                try
                {
                    event.acceptDrop(DnDConstants.ACTION_COPY);
                    java.util.List<File> droppedFiles = (java.util.List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    if(droppedFiles.size() == 1)
                    {
                        if(droppedFiles.get(0).getName().endsWith("𐤁.")) UIManager.codeEditorInstance.loadCodeFile(droppedFiles.get(0));
                        else UIManager.consoleInstance.printErrorMessage("𐤆𐤄𐤅 𐤋𐤀 𐤒𐤅𐤁𐤑 𐤒𐤅𐤃 𐤁. !");
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        // Undo & Redo Handling
        codeArea.getDocument().addUndoableEditListener(evt -> undo.addEdit(evt.getEdit()));
        // Undo Handling
        codeArea.getActionMap().put("Undo", new AbstractAction("Undo") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try
                {
                    if (undo.canUndo())
                    {
                        undo.undo();
                    }
                }
                catch (CannotUndoException e)
                {
                    UIManager.consoleInstance.printErrorMessage("𐤋𐤀 𐤌𐤑𐤋𐤉𐤇 𐤋𐤁𐤈𐤋 𐤌𐤄𐤋𐤊 𐤀𐤇𐤓𐤅𐤍!");
                }
            }
        });
        codeArea.getInputMap().put(KeyStroke.getKeyStroke("control Z"), "Undo");
        // Redo Handling
        codeArea.getActionMap().put("Redo", new AbstractAction("Redo") {
            @Override
            public void actionPerformed(ActionEvent event) {
                try
                {
                    if (undo.canRedo())
                    {
                        undo.redo();
                    }
                }
                catch (CannotRedoException e)
                {
                    UIManager.consoleInstance.printErrorMessage("𐤋𐤀 𐤌𐤑𐤋𐤉𐤇 𐤋𐤔𐤇𐤆𐤓 𐤌𐤄𐤋𐤊 𐤀𐤇𐤓𐤅𐤍!");
                }
            }
        });
        codeArea.getInputMap().put(KeyStroke.getKeyStroke("control Y"), "Redo");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700); // Set the default window size
        setLocationRelativeTo(null); // Centers on screen
    }

    private static @Nullable File openFile()
    {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Genesis Code Files", "𐤁")); // Setting file type filter
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));

        if(fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            if(fileChooser.getSelectedFile().getName().endsWith(".𐤁")) return fileChooser.getSelectedFile();
            else UIManager.consoleInstance.printErrorMessage("𐤆𐤄𐤅 𐤋𐤀 𐤒𐤅𐤁𐤑 𐤒𐤅𐤃 𐤁. !");
        }

        return null;
    }
}
