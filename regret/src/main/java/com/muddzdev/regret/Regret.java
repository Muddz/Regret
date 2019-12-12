package com.muddzdev.regret;

import android.util.Log;

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

    //TODO update tests to match the new add methods algortihm
    //TODO tests if photoshop and MC Office behaves the same way as Regret

    private UndoRedoList undoRedoList;
    private RegretListener listener;

    public Regret(@NonNull RegretListener listener) {
        this.listener = listener;
        this.undoRedoList = new UndoRedoList();
        updateCanDoListener();
    }

    /**
     * @param key      an identifier for the values
     * @param oldValue the old or current value
     * @param newValue the new value
     */
    public void add(@NonNull String key, @NonNull Object oldValue, @NonNull Object newValue) {
        undoRedoList.add(key, oldValue, newValue);
        updateCanDoListener();
    }

    /**
     * @return the current value
     */
    public UndoRedoList.Record getCurrent() {
        return undoRedoList.getCurrent();
    }

    /**
     * Returns the previous key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void undo() {
        UndoRedoList.Record record = undoRedoList.undo();
        updateDoListener(record);
        updateCanDoListener();
    }

    /**
     * Returns the next key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void redo() {
        UndoRedoList.Record record = undoRedoList.redo();
        updateDoListener(record);
        updateCanDoListener();
    }

    /**
     * @return true if a previous-element exists, else false
     */
    public boolean canUndo() {
        return undoRedoList.canUndo();
    }

    /**
     * @return true if a next-element exists, else false
     */
    public boolean canRedo() {
        return undoRedoList.canRedo();
    }

    /**
     * @return true if the collection is empty else false
     */
    public boolean isEmpty() {
        return undoRedoList.isEmpty();
    }

    /**
     * Deletes all elements in the collection
     */
    public void clear() {
        Log.d("XXX", undoRedoList.toString());
        undoRedoList.clear();
        updateCanDoListener();
    }


    private void updateCanDoListener() {
        if (listener != null) {
            listener.onCanDo(undoRedoList.canUndo(), undoRedoList.canRedo());
        }
    }

    private void updateDoListener(UndoRedoList.Record record) {
        String key = record.key;
        Object value = record.value;
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
