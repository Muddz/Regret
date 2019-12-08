package com.muddzdev.regret;

class UndoRedoManager {

    private UndoRedoList<Record> undoRedoList;

    public UndoRedoManager() {
        undoRedoList = new UndoRedoList<>();
    }

    public void add(String key, Object oldValue, Object newValue) {
        undoRedoList.add(new Record(key, oldValue));
        undoRedoList.add(new Record(key, newValue));
    }

    public Record getCurrent() {
        return undoRedoList.getCurrent();
    }

//    public Record undo() {
//        if (undoRedoList.canUndo()) {
//            Record currentRecord = getCurrent();
//            Record previousRecord = undoRedoList.undo();
//            if (currentRecord.getKey().equals(previousRecord.getKey())) {
//                return previousRecord;
//            } else if (undoRedoList.canUndo()) {
//                return undoRedoList.undo();
//            }
//        }
//        return null;
//    }
//
//    public Record redo() {
//        if (undoRedoList.canRedo()) {
//            Record currentRecord = getCurrent();
//            Record nextRecord = undoRedoList.redo();
//            if (currentRecord.getKey().equals(nextRecord.getKey())) {
//                return nextRecord;
//            } else if (undoRedoList.canRedo()) {
//                return undoRedoList.redo();
//            }
//        }
//        return null;
//    }

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


}
