package com.muddzdev.regret;

import java.util.NoSuchElementException;


/*
 *       Copyright 2018 Muddi Walid
 *       Licensed under the Apache License, Version 2.0 (the "License");
 *       you may not use this file except in compliance with the License.
 *       You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *       Unless required by applicable law or agreed to in writing, software
 *       distributed under the License is distributed on an "AS IS" BASIS,
 *       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *       See the License for the specific language governing permissions and
 *       limitations under the License.
 */

/**
 * @author Muddi Walid
 * This DoublyLinkedList is specifically tailored towards an Undo/Redo data structure
 */

class DoublyLinkedList<E> {

    private Node head;
    private int size;
    private boolean shouldRefreshSize;

    private class Node {
        E element;
        Node next = null;
        Node prev = null;

        Node(E element) {
            this.element = element;
        }
    }


    //TODO make this better and shorter
    public void add(E element) {
        Node newNode = new Node(element);

        if (head == null) {
            head = newNode;
        } else if (head.next == null) {
            newNode.prev = head;
            head.next = newNode;
        } else if (head.prev != null) {
            //Middle of the list
            shouldRefreshSize = true;
            head.next = null;
            newNode.prev = head;
            head.next = newNode;
        } else if (head.prev == null && head.next != null) {
            //Start of the list
            head.next = null;
            newNode.prev = head;
            head.next = newNode;
            shouldRefreshSize = true;
        } else {
            newNode.prev = head.next;
        }

        head = newNode;
        if (shouldRefreshSize) {
            refreshSize();
        } else {
            size++;
        }

        shouldRefreshSize = false;
    }

    public E redo() {
        if (head == null || head.next == null) {
            throw new NoSuchElementException();
        }

        Node nextNode = head.next;
        head = nextNode;
        return nextNode.element;
    }

    public E undo() {
        if (head == null || head.prev == null) {
            throw new NoSuchElementException();
        }
        Node previousNode = head.prev;
        head = previousNode;
        return previousNode.element;
    }

    public boolean canRedo() {
        return head != null && head.next != null;
    }

    public boolean canUndo() {
        return head != null && head.prev != null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public void clear() {
        head = null;
        size = 0;
    }

    private void refreshSize() {
        //TODO should this start from 1 or 0?
        //TODO Can this be shorten?
        size = 0;
        Node tempHead = head;
        if (tempHead != null) {
            while (tempHead.prev != null) {
                tempHead = tempHead.prev;
            }
            while (tempHead.next != null) {
                tempHead = tempHead.next;
                size++;
            }
        }
    }
}
