package com.muddzdev.regret;

class Record {

    private String objectName;
    private Object object;

    Record(String objectName, Object object) {
        this.objectName = objectName;
        this.object = object;
    }

    Object getObject() {
        return object;
    }
    String getObjectName() {
        return objectName;
    }
}
