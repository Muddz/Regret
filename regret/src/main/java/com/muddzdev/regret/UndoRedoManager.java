package com.muddzdev.regret;

import android.content.Context;

class UndoRedoManager {

    private UndoRedoList<Record> undoRedoList;
    private PersistentStorage persistentStorage;

    public UndoRedoManager(Context context) {
        this.persistentStorage = new PersistentStorage(context.getApplicationContext());
        if (persistentStorage.hasList()) {
            undoRedoList = persistentStorage.loadList();
        } else {
            undoRedoList = new UndoRedoList<>();
        }
    }

    public void add(String key, Object value) {
        undoRedoList.add(new Record(key, value));
        saveList();
    }

    public Record getCurrent() {
        return undoRedoList.getCurrent();
    }

    public Record undo() {
        if (undoRedoList.canUndo()) {
            saveList();
            return undoRedoList.undo();
        }
        return null;
    }

    public Record redo() {
        if (undoRedoList.canRedo()) {
            saveList();
            return undoRedoList.redo();
        }
        return null;
    }

    public boolean canUndo() {
        return undoRedoList.canUndo();
    }

    public boolean canRedo() {
        return undoRedoList.canRedo();
    }

    public boolean isEmpty() {
        return undoRedoList.isEmpty();
    }

    public void clear() {
        undoRedoList.clear();
        persistentStorage.clear();
    }

    private void saveList() {
        persistentStorage.saveList(undoRedoList);
    }

}
