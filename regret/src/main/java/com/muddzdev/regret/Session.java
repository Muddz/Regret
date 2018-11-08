package com.muddzdev.regret;

import java.util.LinkedList;

class Session {

    static final String KEY_PAPER_SESSION = "REGRET_SESSION_KEY";
    private DoublyLinkedList<Record> list = new DoublyLinkedList<>();
    private int lastHeadPosition;

    DoublyLinkedList<Record> getList() {
        return list;
    }

    void setList(DoublyLinkedList<Record> list) {
        this.list = list;
    }

    int getLastHeadPosition() {
        return lastHeadPosition;
    }

    void setLastHeadPosition(int lastHeadPosition) {
        this.lastHeadPosition = lastHeadPosition;
    }
}
