package com.muddzdev.regret;

public interface OnRegretListener {
    void onRegret(String objectName, Object object);
    void onCanUndoRedo(boolean canUndo, boolean canRedo);
}
