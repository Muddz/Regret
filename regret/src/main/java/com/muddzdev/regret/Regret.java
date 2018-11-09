package com.muddzdev.regret;

import android.support.annotation.NonNull;

public class Regret {

    private OnRegretListener listener;
    private RegretHandler regretHandler;

    public Regret(@NonNull OnRegretListener listener) {
        this.listener = listener;
        this.regretHandler = new RegretHandler();
    }

    public void add(@NonNull String objectName, @NonNull Object object) {
        regretHandler.save(new Record(objectName, object));
    }

    public void redo() {
        if (regretHandler.canRedo() && listener != null) {
            Record nextRecord = regretHandler.redo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
        }
    }

    public void undo() {
        if (regretHandler.canUndo() && listener != null) {
            Record nextRecord = regretHandler.undo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
        }
    }


    public boolean canRedo() {
        return regretHandler.canRedo();
    }

    public boolean canUndo() {
        return regretHandler.canUndo();
    }

    public void clearSession() {
        regretHandler.clearSession();
    }

    public int getCount() {
        return regretHandler.getCount();
    }

    public interface OnRegretListener {
        void onDo(String objectName, Object object);
    }
}
