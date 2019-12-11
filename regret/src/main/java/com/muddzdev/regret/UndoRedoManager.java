package com.muddzdev.regret;

class UndoRedoManager {

    private UndoRedoList undoRedoList;

    public UndoRedoManager() {
        undoRedoList = new UndoRedoList();
    }

    public void add(String key, Object oldValue, Object newValue) {
        undoRedoList.add(key, oldValue, newValue);


// //TODO implement this logic in UndoRedoList
//        if (undoRedoList.isEmpty()) {
//            undoRedoList.add(oldRecord);
//            undoRedoList.add(newRecord);
//        } else if (getCurrent().getKey().equals(oldRecord.getKey())) {
//            undoRedoList.add(newRecord);
//        } else {
//            undoRedoList.add(oldRecord);
//            undoRedoList.add(newRecord);
//        }

    }


    public UndoRedoList.Record getCurrent() {
        return undoRedoList.getCurrent();
    }

//    public Record undo() {
//        if (undoRedoList.canUndo()) {
//
//            //TODO implement this logic in UndoRedoList
//
//            UndoRedoList.Record currentRecord = getCurrent();
//            UndoRedoList.Record nextRecord = undoRedoList.undo();
//            if (currentRecord.getKey().equals(nextRecord.getKey())) {
//                return nextRecord;
//            } else if (undoRedoList.canUndo()) {
//                return undoRedoList.undo();
//            }
//        }
//        return null;
//    }
//
//    public Record redo() {
//        if (undoRedoList.canRedo()) {
//
//            //TODO implement this logic in UndoRedoList
//
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


    public UndoRedoList.Record undo() {
        if (undoRedoList.canUndo()) {
            return undoRedoList.undo();
        }
        return null;
    }

    public UndoRedoList.Record redo() {
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
