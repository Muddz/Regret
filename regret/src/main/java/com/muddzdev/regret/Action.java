package com.muddzdev.regret;

import android.view.View;

class Action {

    String key;
    Object value;
    View view;
    Action(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    Action(String key, Object value,View view) {
        this.key = key;
        this.value = value;
        this.view=view;
    }
}
