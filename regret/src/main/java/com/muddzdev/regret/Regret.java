package com.muddzdev.regret;

import android.content.Context;
import android.support.annotation.NonNull;

/*
 *       Copyright 2018 Muddi Walid
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */

public class Regret {

    private RegretListener listener;
    private UndoRedoManager undoRedoManager;

    public Regret(@NonNull Context context, @NonNull RegretListener listener) {
        this.listener = listener;
        this.undoRedoManager = new UndoRedoManager();
    }

    /**
     * @param key An identifier for the value
     * @param value A value associated with the key
     */
    public void add(@NonNull String key, @NonNull Object value) {
        undoRedoManager.add(key, value);
        updateCanDoListener();
    }


    /**
     * @return the current value
     */
    public Object getCurrent() {
        Record record = undoRedoManager.getCurrent();
        return record.getValue();
    }

    /**
     * undo() returns the previous key-value pair via the callback onDo() in RegretListener
     */
    public void undo() {
        Record record = undoRedoManager.undo();
        updateDoListener(record);
        updateCanDoListener();
    }

    /**
     * redo() returns the next key-value pair via the callback onDo() in RegretListener
     */
    public void redo() {
        Record record = undoRedoManager.redo();
        updateDoListener(record);
        updateCanDoListener();
    }

    /**
     * @return True if a previous-entry exists, else false
     */
    public boolean canUndo() {
        return undoRedoManager.canUndo();
    }

    /**
     * @return True if a next-entry exists, else false
     */
    public boolean canRedo() {
        return undoRedoManager.canRedo();
    }

    /**
     * @return Whether the undo-redo list has entries or not
     */
    public boolean isEmpty() {
        return undoRedoManager.isEmpty();
    }

    /**
     * Clears the undo-redo list
     */
    public void clear() {
        undoRedoManager.clear();
        updateCanDoListener();
    }


    private void updateCanDoListener() {
        if (listener != null) {
            listener.onCanDo(undoRedoManager.canUndo(), undoRedoManager.canRedo());
        }
    }

    private void updateDoListener(Record record) {
        String key = record.getKey();
        Object value = record.getValue();
        if (listener != null) {
            listener.onDo(key, value);
        }
    }

    public interface RegretListener {
        /**
         * onDo() returns a key-value pair when undo() or redo() is called
         * @param key   The key to identify the returned value
         * @param value The value associated with the key
         */
        void onDo(String key, Object value);

        /**
         * onCanDo() updates for every call to undo(), redo() or add().
         * This call is useful for updating the state of UI undo or redo buttons
         * @param canUndo
         * @param canRedo
         */
        void onCanDo(boolean canUndo, boolean canRedo);
    }
}
