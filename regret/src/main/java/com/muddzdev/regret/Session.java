package com.muddzdev.regret;

class Session {

    static final String KEY_PAPER_SESSION = "REGRET_SESSION_KEY";
    private DoublyLinkedList<Record> list = new DoublyLinkedList<>();

    DoublyLinkedList<Record> getList() {
        return list;
    }

    void setList(DoublyLinkedList<Record> list) {
        this.list = list;
    }
}
