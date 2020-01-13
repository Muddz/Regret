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

    private UndoRedoList undoRedoList;
    private RegretListener listener;

    @NonNull
    @Override
    public String toString() {
        return undoRedoList.toString();
    }

    public int getSize() {
        return undoRedoList.getSize();
    }

    public Regret(@NonNull RegretListener listener) {
        this.listener = listener;
        this.undoRedoList = new UndoRedoList();
        updateCanDoListener();
    }

    /**
     * @param key      an identifier for the values
     * @param currentValue the old or current value
     * @param newValue the new value
     */
    public void add(@NonNull String key, @NonNull Object currentValue, @NonNull Object newValue) {
        undoRedoList.add(key, currentValue, newValue);
        updateCanDoListener();
    }

    /**
     * @return the current value
     */
    public Action getCurrent() {
        return undoRedoList.getCurrent();
    }

    /**
     * Returns the previous key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void undo() {
        Action action = undoRedoList.undo();
        updateDoListener(action);
        updateCanDoListener();
    }

    /**
     * Returns the next key-value pair via the callback onDo() in {@link RegretListener}
     */
    public void redo() {
        Action action = undoRedoList.redo();
        updateDoListener(action);
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
        undoRedoList.clear();
        updateCanDoListener();
    }


    private void updateCanDoListener() {
        if (listener != null) {
            listener.onCanDo(undoRedoList.canUndo(), undoRedoList.canRedo());
        }
    }

    private void updateDoListener(Action action) {
        if (action != null && listener != null) {
            String key = action.key;
            Object value = action.value;
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
