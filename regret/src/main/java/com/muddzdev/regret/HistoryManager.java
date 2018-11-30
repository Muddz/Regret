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
        updateCanDoListener();
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
        updateCanDoListener();
        saveHistoryState();
    }

    public void undo() {
        if (history.canUndo() && listener != null) {
            Record nextRecord = history.undo();
            String key = nextRecord.getKey();
            Object value = nextRecord.getValue();
            listener.onDo(key, value);
            updateCanDoListener();
            saveHistoryState();
        }
    }

    public void redo() {
        if (history.canRedo() && listener != null) {
            Record nextRecord = history.redo();
            String key = nextRecord.getKey();
            Object value = nextRecord.getValue();
            listener.onDo(key, value);
            updateCanDoListener();
            saveHistoryState();
        }
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
        updateCanDoListener();
    }

    private void saveHistoryState() {
        storage.saveHistory(history);
    }

    private void updateCanDoListener() {
        listener.onCanDo(history.canUndo(), history.canRedo());
    }

}
