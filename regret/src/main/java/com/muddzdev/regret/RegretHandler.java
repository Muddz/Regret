package com.muddzdev.regret;

class RegretHandler {

    private int pointerIndex;
    private DoublyLinkedList<Record> history = new DoublyLinkedList<>();
    //    private Database storage;
    private Session session;

    RegretHandler() {
//        this.storage = Database.getDatabaseManager();
//        this.session = storage.getSession();
//        this.history = session.getList();
    }

    void save(Record record) {
        history.add(record);
//        session.setList(history);
//        storage.saveSession(session);
    }

    Record getPrevious() {
        return history.undo();
    }

    Record getNext() {
        return history.redo();
    }

    boolean hasNext() {
        return history.canRedo();
    }

    boolean hasPrevious() {
        return history.canUndo();
    }

    int getCount() {
        return history.size();
    }

    void clearSession() {
        history.clear();
//        storage.clearSession();
    }


}
