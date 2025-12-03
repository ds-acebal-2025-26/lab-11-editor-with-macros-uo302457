package es.uniovi.eii.ds.editor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import es.uniovi.eii.ds.editor.actions.*;
import es.uniovi.eii.ds.editor.model.Document;

/*
 * Represents the application. In a real editor, this would be the main window of the
 * editor, and it would be responsible for creating menus, toolbars, etc. In our case, that
 * part of user interaction is simulated from the main method, and it is assumed that methods
 * like open, insert, delete, etc. are the ones that would be called in response to user
 * interface events.
 * 
 * Redesign:
 * - During the initial redesign phase, this class has been defined to act as a wrapper
 *   around the model (the Document) and serve as the main entry point for user actions.
 * 
 * Implementation of macros:
 * - The Editor class now uses the Command pattern (Action interface) to support macros.
 * - A Macro is an action that contains the actions to be executed (Composite pattern).
 *   It acts as the invoker of the actions it contains, in the context of the Command pattern.
 * - All operations that modify the document are now actions implementing the Action interface.
 * - A macro is expected to be applied to the document currently opened in the editor, so
 *   instead of keeping a reference to the document, they receive it as a parameter.
 * - Important: opening a document is not an operation that makes sense to be recorded in a
 *   macro (it changes the state of the editor, not the document).
 */
public class Editor {

    Document document;

    private Map<String, Macro> macros = new HashMap<>();
    private Macro currentMacro; // The macro being recorded, if any

    public Editor() {
        document = new Document();
    }

    public void open(String filename) {
        document = new Document(readFile(filename));
    }

    private String readFile(String filename) {
        InputStream in = getClass().getResourceAsStream("/" + filename);
        if (in == null)
            throw new IllegalArgumentException("File not found: " + filename);
        
        try (BufferedReader input = new BufferedReader(new InputStreamReader(in))) {
            StringBuilder result = new StringBuilder();
            String line;
            boolean firstLine = true;
            while ((line = input.readLine()) != null) {
                if (!firstLine)
                    result.append(System.lineSeparator());
                result.append(line);
                firstLine = false;
            }
            return result.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public String getText() {
        return document.getText();
    }

    //$-- Document modification methods ----------------------------------------

    public void insert(String[] words) {
        run(new Insert(words));
    }

    public void delete() {
        run(new Delete());
    }

    public void replace(String find, String replace) {
        run(new Replace(find, replace));
    }

    //$-- Macro methods -------------------------------------------------------

    public boolean hasMacro(String name) {
        return macros.containsKey(name);
    }

    public void recordMacro(String name) {
        currentMacro = new Macro(name);
    }

    private boolean isRecording() {
        return currentMacro != null;
    }

    public void stopRecording() {
        if (isRecording()) {
            macros.put(currentMacro.name(), currentMacro);
            currentMacro = null;
        }
    }

    public void runMacro(String name) {
        Macro macro = macros.get(name);
        if (macro != null) {
            run(macro); // Macros can be nested => macro.execute(document) would not work <-- Be careful!
        }
    }

    // Executes an action and records it (if a macro is being recorded).
    private void run(Action action) {
        action.execute(document);
        if (isRecording()) {
            currentMacro.add(action);
        }
    }
}
