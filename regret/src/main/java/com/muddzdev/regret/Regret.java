package com.muddzdev.regret;

import android.content.Context;
import android.support.annotation.NonNull;

public class Regret implements OnRegretListener {

    private HistoryManager regretHandler;
    private OnRegretListener listener;

    public Regret(@NonNull Context context, @NonNull OnRegretListener listener) {
        this.regretHandler = new HistoryManager(context, this);
        this.listener = listener;
    }

    public void add(@NonNull String objectName, @NonNull Object object) {
        regretHandler.add(objectName, object);
    }

    public void undo() {
        regretHandler.undo();
    }

    public void redo() {
        regretHandler.redo();
    }

    public void clear() {
        regretHandler.clear();
    }

    public int getCount() {
        return regretHandler.getCount();
    }

    @Override
    public void onRegret(String objectName, Object object) {
        listener.onRegret(objectName, object);
    }

    @Override
    public void onCanUndoRedo(boolean canUndo, boolean canRedo) {
        listener.onCanUndoRedo(canUndo, canRedo);
    }

}
