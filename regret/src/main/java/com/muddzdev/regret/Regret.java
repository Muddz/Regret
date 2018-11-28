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

public class Regret implements OnRegretListener {

    private HistoryManager historyManager;
    private OnRegretListener listener;

    public Regret(@NonNull Context context, @NonNull OnRegretListener listener) {
        this.historyManager = new HistoryManager(context, this);
        this.listener = listener;
    }

    public void add(@NonNull String key, @NonNull Object value) {
        historyManager.add(key, value);
    }

    public void undo() {
        historyManager.undo();
    }

    public void redo() {
        historyManager.redo();
    }

    public boolean canUndo() {
        return historyManager.canUndo();
    }

    public boolean canRedo() {
        return historyManager.canRedo();
    }

    public void clear() {
        historyManager.clear();
    }

    @Override
    public void onDo(String key, Object value) {
        listener.onDo(key, value);
    }

    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        listener.onCanDo(canUndo, canRedo);
    }

}
