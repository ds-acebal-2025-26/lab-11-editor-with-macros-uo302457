package es.uniovi.eii.ds.editor.actions;

import es.uniovi.eii.ds.editor.Action;
import es.uniovi.eii.ds.editor.model.Document;

public class Replace implements Action {

    private String find;
    private String replace;

    public Replace(String find, String replace) {
        this.find = find;
        this.replace = replace;
    }

    @Override
    public void execute(Document document) {
        document.replace(find, replace);
    }

    @Override
    public String toString() {
        return "replace " + find + " " + replace;
    }
}
