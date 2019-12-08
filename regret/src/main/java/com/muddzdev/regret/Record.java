package com.muddzdev.regret;

import java.io.Serializable;

class Record  {

    private String key;
    private Object value;

    public Record(String key, Object value) {
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return key;
    }

    public Object getValue() {
        return value;
    }

}
