package com.muddzdev.regret;

class Record {

    private String objectName;
    private Object object;

    public Record(String objectName, Object object) {
        this.objectName = objectName;
        this.object = object;
    }

    public Object getObject() {
        return object;
    }

    public String getObjectName() {
        return objectName;
    }
}
