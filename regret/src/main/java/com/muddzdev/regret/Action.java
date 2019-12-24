package com.muddzdev.regret;

import androidx.annotation.Nullable;

class Action {

    String key;
    Object value;

    Action(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof Action &&
                ((Action) obj).key.equals(key) &&
                ((Action) obj).value.equals(value);
    }
}
