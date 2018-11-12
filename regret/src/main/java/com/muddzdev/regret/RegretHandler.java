package com.muddzdev.regret;

import android.content.Context;

class RegretHandler {

    private DoublyLinkedList<Record> list;
    private Storage storage;

    RegretHandler(Context context) {
        this.storage = new Storage(context);
        this.list = storage.getList();
    }

    void save(Record record) {
        list.add(record);
        storage.setList(list);
    }

    private DoublyLinkedList<Record> getList() {
        return list;
    }

    Record undo() {
        return getList().undo();
    }

    Record redo() {
        return getList().redo();
    }

    boolean canRedo() {
        return getList().canRedo();
    }

    boolean canUndo() {
        return getList().canUndo();
    }

    int getCount() {
        return getList().size();
    }

    void clear() {
        getList().clear();
        storage.clear();
    }
}
