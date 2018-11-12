package com.muddzdev.regret;

import android.content.Context;
import android.support.annotation.NonNull;

public class Regret {

    private OnRegretListener listener;
    private RegretHandler regretHandler;

    public Regret(@NonNull Context context, @NonNull OnRegretListener listener) {
        this.regretHandler = new RegretHandler(context);
        this.listener = listener;
        updateUndoRedoListener();
    }

    public void add(@NonNull String objectName, @NonNull Object object) {
        regretHandler.save(new Record(objectName, object));
        updateUndoRedoListener();
    }

    public void redo() {
        if (regretHandler.canRedo() && listener != null) {
            Record nextRecord = regretHandler.redo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
            updateUndoRedoListener();
        }
    }

    public void undo() {
        if (regretHandler.canUndo() && listener != null) {
            Record nextRecord = regretHandler.undo();
            String objectName = nextRecord.getObjectName();
            Object object = nextRecord.getObject();
            listener.onDo(objectName, object);
            updateUndoRedoListener();
        }
    }

    public void clear() {
        regretHandler.clear();
        updateUndoRedoListener();
    }

    public int getCount() {
        return regretHandler.getCount();
    }


    private void updateUndoRedoListener() {
        listener.status(regretHandler.canUndo(), regretHandler.canRedo());
    }

    public interface OnRegretListener {
        //TODO Refactor to better naming
        void onDo(String objectName, Object object);

        void status(boolean canUndo, boolean canRedo);
    }

}
