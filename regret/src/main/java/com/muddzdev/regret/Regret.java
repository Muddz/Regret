package com.muddzdev.regret;

import android.support.annotation.NonNull;

public class Regret {

    private OnRegretListener listener;
    private RegretHandler regretHandler;

    public Regret(@NonNull OnRegretListener listener) {
        this.listener = listener;
        this.regretHandler = new RegretHandler();
    }

    public void redo() {
        if (regretHandler.hasNext() && listener != null) {
            Record nextRecord = regretHandler.getNext();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
        }
    }

    public void undo() {
        if (regretHandler.hasPrevious() && listener != null) {
            Record nextRecord = regretHandler.getPrevious();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
        }
    }


    public void add(@NonNull String objectName, @NonNull Object object) {
        regretHandler.save(new Record(objectName, object));
    }

    public void clearSession() {
        regretHandler.clearSession();
    }

    public int getCount() {
        return regretHandler.getCount();
    }

    public boolean hasNext() {
        return regretHandler.hasNext();
    }

    public boolean hasPrevious() {
        return regretHandler.hasPrevious();
    }

    public interface OnRegretListener {
        void onDo(String objectName, Object object);
    }
}
