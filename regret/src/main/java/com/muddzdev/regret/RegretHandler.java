package com.muddzdev.regret;

import android.content.Context;

class RegretHandler {

    private DoublyLinkedList<Record> list;
    private Database databaseManager;
    private Session session;

    RegretHandler(Context context) {
        this.databaseManager = Database.getDatabaseManager(context);
        this.session = databaseManager.getSession();
        if (session == null) {
            session = new Session();
        }
    }

    void save(Record record) {
        DoublyLinkedList<Record> list = getList();
        list.add(record);
        session.setList(list);
        databaseManager.saveSession(session);
    }

    private DoublyLinkedList<Record> getList() {
        if (list != null) {
            return list;
        } else {
            if (session != null) {
                list = session.getList();
            } else {
                list = new DoublyLinkedList<>();
            }
            return list;
        }
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

    void clearSession() {
        getList().clear();
        databaseManager.clearSession();
    }
}
