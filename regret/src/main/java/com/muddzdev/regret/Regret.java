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
    private HistoryManager historyManager;

    public Regret(@NonNull Context context, @NonNull RegretListener listener) {
        this.listener = listener;
        this.historyManager = new HistoryManager(context);
    }

    public void add(@NonNull String key, @NonNull Object value) {
        historyManager.add(key, value);
        updateCanDoListener();
    }

    public void undo() {
        Record record = historyManager.undo();
        updateDoListener(record);
        updateCanDoListener();
    }

    public void redo() {
        Record record = historyManager.redo();
        updateDoListener(record);
        updateCanDoListener();
    }

    public boolean canUndo() {
        return historyManager.canUndo();
    }

    public boolean canRedo() {
        return historyManager.canRedo();
    }

    public boolean isEmpty() {
        return historyManager.isEmpty();
    }

    public void clear() {
        historyManager.clear();
        updateCanDoListener();
    }


    private void updateCanDoListener() {
        if (listener != null) {
            listener.onCanDo(historyManager.canUndo(), historyManager.canRedo());
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
        void onDo(String key, Object value);
        void onCanDo(boolean canUndo, boolean canRedo);
    }
}
