package com.muddzdev.regret;

public interface OnRegretListener {
    void onDo(String key, Object value);
    void onCanDo(boolean canUndo, boolean canRedo);
}
