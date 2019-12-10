package com.muddzdev.regret;

import androidx.annotation.NonNull;

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

    //TODO new add() method support
    //TODO update tests to match the new add methods algortihm
    //TODO tests if photoshop and MC Office behaves the same way as Regret

    private RegretListener listener;
    private UndoRedoManager undoRedoManager;


    public Regret(@NonNull RegretListener listener) {
        this.listener = listener;
        this.undoRedoManager = new UndoRedoManager();
        updateCanDoListener();
    }

    /**
     * @param key      an identifier for the values
     * @param oldValue the old or current value
     * @param newValue the new value
     */
    public void add(@NonNull String key, @NonNull Object oldValue, @NonNull Object newValue) {
        undoRedoManager.add(key, oldValue, newValue);
        updateCanDoListener();
    }

    /**
     * @return the current value
     */
    public Record getCurrent() {
        return undoRedoManager.getCurrent();
    }

    /**
     * Returns the previous key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void undo() {
        Record record = undoRedoManager.undo();
        updateDoListener(record);
        updateCanDoListener();
//        Log.d("XXX", "UNDO   Key: " + record.getKey() + "  Value: " + record.getValue());
    }

    /**
     * Returns the next key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void redo() {
        Record record = undoRedoManager.redo();
        updateDoListener(record);
        updateCanDoListener();
    }

    /**
     * @return true if a previous-element exists, else false
     */
    public boolean canUndo() {
        return undoRedoManager.canUndo();
    }

    /**
     * @return true if a next-element exists, else false
     */
    public boolean canRedo() {
        return undoRedoManager.canRedo();
    }

    /**
     * @return true if the collection is empty else false
     */
    public boolean isEmpty() {
        return undoRedoManager.isEmpty();
    }

    /**
     * Deletes all elements in the collection
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
         * Returns a key-value pair when {@link #undo()} or {@link #redo()} is called
         *
         * @param key   the key to identify the returned value
         * @param value the value associated with the key
         */
        void onDo(String key, Object value);

        /**
         * onCanDo() updates for every call to {@link #undo()}, {@link #redo()} or {@link #add(String, Object, Object)}.
         * This callback is specifically useful for updating the states of undo and redo buttons.
         */
        void onCanDo(boolean canUndo, boolean canRedo);
    }
}
