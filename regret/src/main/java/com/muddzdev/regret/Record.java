package com.muddzdev.regret;

class Record {

    private String key;
    private Object value;

    public Record(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }
}
