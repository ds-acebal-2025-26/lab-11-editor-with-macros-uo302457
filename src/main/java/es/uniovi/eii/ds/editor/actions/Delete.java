package es.uniovi.eii.ds.editor.actions;

import es.uniovi.eii.ds.editor.Action;
import es.uniovi.eii.ds.editor.model.Document;

public class Delete implements Action {

    @Override
    public void execute(Document document) {
        document.delete();
    }

    @Override
    public String toString() {
        return "delete";
    }
}
