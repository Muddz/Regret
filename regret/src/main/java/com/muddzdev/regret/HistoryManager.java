package com.muddzdev.regret;

import android.content.Context;

class HistoryManager {

    private DoublyLinkedList<Record> history;
    private Storage storage;

    public HistoryManager(Context context) {
        this.storage = new Storage(context.getApplicationContext());
        initHistory();
    }

    private void initHistory() {
        if (storage.hasHistory()) {
            history = storage.loadHistory();
        } else {
            history = new DoublyLinkedList<>();
        }
    }

    public void add(String key, Object value) {
        history.add(new Record(key, value));
        saveHistoryState();
    }

    public Record undo() {
        if (history.canUndo()) {
            saveHistoryState();
            return history.undo();
        }
        return null;
    }

    public Record redo() {
        if (history.canRedo()) {
            saveHistoryState();
            return history.redo();
        }
        return null;
    }

    public boolean canUndo() {
        return history.canUndo();
    }

    public boolean canRedo() {
        return history.canRedo();
    }

    public boolean isEmpty() {
        return history.isEmpty();
    }

    public void clear() {
        history.clear();
        storage.clear();
    }

    private void saveHistoryState() {
        storage.saveHistory(history);
    }

}
