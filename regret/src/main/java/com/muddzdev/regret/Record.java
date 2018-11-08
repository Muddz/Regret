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

    public String getObjectName() {
        return objectName;
    }


    public void setIntValue(int intValue) {
        this.object = intValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.object = Double.valueOf(doubleValue);
    }

    public void setLongValue(Long longValue) {
        this.object = Long.valueOf(longValue);
    }

    public void setBooleanValue(boolean booleanValue) {
        this.object = Boolean.valueOf(booleanValue);
    }
}
