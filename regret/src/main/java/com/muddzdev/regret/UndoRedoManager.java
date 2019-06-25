package com.muddzdev.regret;

class UndoRedoManager {

    /*
    Save to persistant storage
    https://stackoverflow.com/questions/9111899/how-do-i-build-a-java-type-object-at-runtime-from-a-generic-type-definition-and
     */

    private UndoRedoList<Record> undoRedoList;

    public UndoRedoManager() {
        undoRedoList = new UndoRedoList<>();
    }

    public void add(String key, Object value) {
        undoRedoList.add(new Record(key, value));
    }

    public Record getCurrent() {
        return undoRedoList.getCurrent();
    }

    public Record undo() {
        if (undoRedoList.canUndo()) {
            return undoRedoList.undo();
        }
        return null;
    }

    public Record redo() {
        if (undoRedoList.canRedo()) {
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
    }

    public String toString() {
        return undoRedoList.toString();
    }

}
