package com.muddzdev.regret;

public interface OnRegretListener {
    void onDo(String objectName, Object object);
    void onCanDo(boolean canUndo, boolean canRedo);
}
