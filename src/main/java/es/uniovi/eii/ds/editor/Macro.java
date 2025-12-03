package es.uniovi.eii.ds.editor;

import java.util.*;

import es.uniovi.eii.ds.editor.model.Document;

public class Macro implements Action {

    private List<Action> actions;
    private String name;

    public Macro(String name) {
        actions = new ArrayList<>();
        this.name = name;
    }

    public String name() {
        return name;
    }

    public void add(Action action) {
        actions.add(action);
    }

    @Override
    public void execute(Document document) {
        for (Action action : actions) {
            action.execute(document);
        }
    }

    @Override
    public String toString() {
        return "macro " + name + ": " + actions;
    }
}
