package com.muddzdev.regret;

import java.io.Serializable;
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

//Should this be clonable?
class DoublyLinkedList<E> implements Serializable {

    private Node head;
    private int size;
    private boolean shouldRefreshSize;


    //Should this be static?
    private class Node {
        E element;
        Node next = null;
        Node prev = null;

        Node(E element) {
            this.element = element;
        }
    }


    public void add(E element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
        } else if (head.next == null) {
            newNode.prev = head;
            head.next = newNode;
        } else {
            head.next = null;
            newNode.prev = head;
            head.next = newNode;
            shouldRefreshSize = true;
        }

        head = newNode;
        if (shouldRefreshSize) {
            refreshSize();
        } else {
            size++;
        }
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
        size = 1;
        Node tempHead = head;
        while (tempHead.prev != null) {
            tempHead = tempHead.prev;
        }
        while (tempHead.next != null) {
            tempHead = tempHead.next;
            size++;
        }
        shouldRefreshSize = false;
    }
}
