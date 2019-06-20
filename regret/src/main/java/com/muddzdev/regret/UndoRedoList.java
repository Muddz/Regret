package com.muddzdev.regret;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.ListIterator;
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
 */

class UndoRedoList<E> extends AbstractSequentialList<E> implements Serializable {

    private Node head;
    private Node tail;
    private Node pointer;
    private int size;
    private boolean shouldRefreshSize;


    private class Node implements Serializable {
        E element;
        Node next = null;
        Node prev = null;

        Node(E element) {
            this.element = element;
        }
    }

    /**
     * Adds an element to the collection
     */
    public boolean add(E element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
        } else if (pointer.next == null) {
            newNode.prev = pointer;
            pointer.next = newNode;
        } else {
            pointer.next = null;
            newNode.prev = pointer;
            pointer.next = newNode;
            shouldRefreshSize = true;
        }

        pointer = newNode;
        tail = newNode;
        if (shouldRefreshSize) {
            refreshSize();
        } else {
            size++;
        }
        return true;
    }

    /**
     * @return The current element which is pointed at
     */
    public E getCurrent() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.element;
    }

    /**
     * @return The next element in the collection
     */
    public E redo() {
        if (pointer.next == null) {
            throw new NoSuchElementException();
        }

        Node next = pointer.next;
        pointer = next;
        return next.element;
    }

    /**
     * @return The previous element in the collection
     */
    public E undo() {
        if (pointer.prev == null) {
            throw new NoSuchElementException();
        }
        Node previousNode = pointer.prev;
        pointer = previousNode;
        return previousNode.element;
    }


    /**
     * @return A boolean for whether a next element exists
     */
    public boolean canRedo() {
        return pointer.next != null;
    }

    /**
     * @return A boolean for whether a previous element exists
     */
    public boolean canUndo() {
        return pointer.prev != null;
    }

    /**
     * @return The size of the collection
     */
    public int size() {
        return size;
    }

    /**
     * @return A boolean for whether the collection is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }


    /**
     * Clears the collection and sets the size to 0
     */
    public void clear() {
        head = null;
        tail = null;
        pointer = null;
        size = 0;
    }


    /**
     * @return A string representation of all elements in the collection
     */
    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder().append('[');
        Node tempHead = head;
        while (tempHead != null) {
            sb.append(tempHead.element);
            tempHead = tempHead.next;
            if (tempHead != null) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(']').toString();
    }

    /*
     This method is only called if we're adding an element between two existing elements.
     */
    private void refreshSize() {
        size = 0;
        Node tempHead = head;
        while (tempHead != null) {
            tempHead = tempHead.next;
            size++;
        }
        shouldRefreshSize = false;
    }


    @NonNull
    @Override
    public ListIterator<E> listIterator(int index) {
        return new UndoRedoIterator();
    }

    private class UndoRedoIterator implements ListIterator<E> {

        private Node tempHead = head;
        private Node tempTail = tail;
        private int nextIndex = 0;
        private int previousIndex = size;

        @Override
        public boolean hasNext() {
            return tempHead != null;
        }

        @Override
        public E next() {
            nextIndex++;
            Node nextNode = tempHead;
            tempHead = tempHead.next;
            return nextNode.element;
        }

        @Override
        public boolean hasPrevious() {
            return tempTail != null && tempHead.prev != null;
        }

        @Override
        public E previous() {
            previousIndex--;
            Node previousNode = tempTail;
            tempTail = tempTail.prev;
            return previousNode.element;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return previousIndex;
        }

        @Override
        public void set(E e) {
            UndoRedoList.this.add(e);
        }

        @Override
        public void add(E e) {
            UndoRedoList.this.add(e);
        }

        @Override
        public void remove() {
        }
    }
}