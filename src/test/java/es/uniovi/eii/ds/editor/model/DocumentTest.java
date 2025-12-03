package es.uniovi.eii.ds.editor.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DocumentTest {

    private Document document;

    @BeforeEach
    public void setUp() {
        document = new Document("En un lugar de la Mancha");
    }

    @Test
    public void testEmptyDocument() {
        assertEquals("", new Document().getText());
    }

    @Test
    public void testDocumentWithInitialContent() {
        assertEquals("En un lugar de la Mancha", document.getText());
    }

    @Test
    public void testInsert() {
        String[] words = "de cuyo nombre no quiero acordarme".split(" ");
        document.insert(words);
        assertEquals("En un lugar de la Mancha de cuyo nombre no quiero acordarme", document.getText());
    }

    @Test
    public void testDeleteFromEmptyDocument() {
        Document emptyDoc = new Document();
        emptyDoc.delete();
        assertEquals("", emptyDoc.getText());
    }

    @Test
    public void testDelete() {
        document.delete();
        assertEquals("En un lugar de la", document.getText());
    }

    @Test
    public void testReplace() {
        document.replace("a", "x");
        assertEquals("En un lugxr de lx Mxnchx", document.getText());
    }

    @Test
    public void testReplaceNonExistentWord() {
        document.replace("Java", "Python");
        assertEquals("En un lugar de la Mancha", document.getText());
    }

    @Test
    public void testMultipleOperations() {
        document.insert("de cuyo nombre no quiero acordarme".split(" "));
        document.delete();
        document.replace("a", "x");
        assertEquals("En un lugxr de lx Mxnchx de cuyo nombre no quiero", document.getText());
    }
}
