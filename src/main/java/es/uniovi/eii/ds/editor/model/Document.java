package es.uniovi.eii.ds.editor.model;

/*
 * This is the text editor's model. It represents a text document and the
 * operations that can be performed on it. It knows nothing about macros or other
 * user interface–specific concerns (it could be opened, for example, by another
 * application without macros, or in read-only mode, etc.). It does not know either
 * about its physical representation (a file, a database entry, etc.) and how it is
 * loaded or saved.
 *
 * This class and its responsibilities have been defined during the initial redesign
 * phase (before implementing macros), and it has not been modified later.
 */
public class Document {

    // The contents of the document
    StringBuilder text;

    public Document() {
        text = new StringBuilder();
    }

    public Document(String contents) {
        text = new StringBuilder(contents);
    }

    public String getText() {
        return text.toString();
    }

    public void insert(String[] words) {
        for (String word : words) {
            text.append(" ").append(word);
        }
    }

    public void delete() {
        int indexOfLastWord = text.toString().trim().lastIndexOf(" ");
        if (indexOfLastWord == -1)
            text = new StringBuilder("");
        else
            text.setLength(indexOfLastWord);
    }

    public void replace(String find, String replace) {
        text = new StringBuilder(text.toString().replace(find, replace));
    }

    @Override
    public String toString() {
        return text.toString();
    }
}
