package com.muddzdev.regret;

import android.content.Context;

class HistoryManager {

    private DoublyLinkedList<Record> history;
    private OnRegretListener listener;
    private Storage storage;

    public HistoryManager(Context context, OnRegretListener listener) {
        this.storage = new Storage(context);
        this.listener = listener;
        initHistory();
        updateUndoRedoListener();
    }

    private void initHistory() {
        if (storage.hasHistory()) {
            history = storage.loadHistory();
        } else {
            history = new DoublyLinkedList<>();
        }
    }

    public void add(String objectName, Object object) {
        history.add(new Record(objectName, object));
        storage.saveHistory(history);
        updateUndoRedoListener();
    }


    public void undo() {
        if (history.canUndo() && listener != null) {
            Record nextRecord = history.undo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
            updateUndoRedoListener();
        }
    }

    public void redo() {
        if (history.canRedo() && listener != null) {
            Record nextRecord = history.redo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
            updateUndoRedoListener();
        }
    }


    public int getCount() {
        return history.size();
    }

    public void clear() {
        history.clear();
        storage.clear();
        updateUndoRedoListener();
    }

    private void updateUndoRedoListener() {
        listener.onCanDo(history.canUndo(), history.canRedo());
    }

}
