package es.uniovi.eii.ds.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class EditorWithMacrosTest {

    private Editor editor;

    @BeforeEach
    public void setUp() {
        editor = new Editor();
    }

    @Test
    public void testInitialDocumentIsEmpty() {
        assertEquals("", editor.getText());
    }

    @Test
    public void testOpenDocument() {
        try {
            editor.open("nonexistent.txt");
            fail("An error was expected for non-existing file");
        } catch (IllegalArgumentException e) {
            // OK
        }
        try {
            editor.open("quijote.txt");
            assertEquals("En un lugar de la Mancha de cuyo nombre", editor.getText());
        } catch (Exception e) {
            fail("quijote.txt should exist");
        }
    }

    @Test
    public void testBasicFunctionality() {
        editor.open("quijote.txt");
        assertEquals("En un lugar de la Mancha de cuyo nombre", editor.getText());
        String[] wordsToInsert = "no quiero acordarme".split(" ");
        editor.insert(wordsToInsert);
        assertEquals("En un lugar de la Mancha de cuyo nombre no quiero acordarme", editor.getText());
        editor.delete();
        assertEquals("En un lugar de la Mancha de cuyo nombre no quiero", editor.getText());
        editor.replace("a", "x");
        assertEquals("En un lugxr de lx Mxnchx de cuyo nombre no quiero", editor.getText());
    }

    @Test
    public void testFirstMacro() {
        editor.recordMacro("m1");
        editor.delete();
        editor.insert(new String[]{"final"});
        editor.replace("i", "y");
        editor.stopRecording();

        editor.open("quijote.txt");
        assertEquals("En un lugar de la Mancha de cuyo nombre", editor.getText());
        editor.runMacro("m1");
        assertEquals("En un lugar de la Mancha de cuyo fynal", editor.getText());
    }

    @Test
    public void testNestedMacros() {
        editor.recordMacro("m1");
        editor.replace("a", "x");
        editor.insert(new String[]{"aa"});
        editor.stopRecording();

        editor.recordMacro("m2");
        editor.insert(new String[] { "bbb" });
        editor.runMacro("m1");
        editor.stopRecording();

        editor.open("quijote.txt");
        assertEquals("En un lugar de la Mancha de cuyo nombre", editor.getText());
        editor.runMacro("m2");
        assertEquals("En un lugxr de lx Mxnchx de cuyo nombre bbb aa", editor.getText());
    }
}
