package com.muddzdev.regret;

class RegretHandler {

    private DoublyLinkedList<Record> list;
    private Database databaseManager;
    private Session session;

    RegretHandler() {
        this.databaseManager = Database.getDatabaseManager();
        this.session = databaseManager.getSession();
        this.list = session.getList();
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
            list = session.getList();
            if (list == null) {
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
