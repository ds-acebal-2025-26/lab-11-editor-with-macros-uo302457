package es.uniovi.eii.ds.editor;

import es.uniovi.eii.ds.editor.model.Document;

public interface Action {
    void execute(Document document);
}
