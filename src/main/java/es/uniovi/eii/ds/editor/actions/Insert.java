package es.uniovi.eii.ds.editor.actions;

import es.uniovi.eii.ds.editor.Action;
import es.uniovi.eii.ds.editor.model.Document;

public class Insert implements Action {

    private String[] words;

    public Insert(String[] words) {
        this.words = words;
    }

    @Override
    public void execute(Document document) {
        document.insert(words);
    }

    @Override
    public String toString() {
        return "insert " + String.join(" ", words);
    }
}
