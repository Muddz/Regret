package com.muddzdev.regret;

import android.support.annotation.NonNull;

import java.util.NoSuchElementException;

/*
 *       Copyright 2019 Muddi Walid
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
 * https://github.com/Muddz/UndoRedoList
 */

public class UndoRedoList<E> {

    private Node head;
    private Node pointer;
    private int pointerIndex;
    private int size;


    private class Node {
        E element;
        Node next = null;
        Node prev = null;

        Node(E element) {
            this.element = element;
        }
    }

    /**
     * Adds an element to the list
     */
    public void add(E element) {
        Node newNode = new Node(element);
        if (head == null) {
            head = newNode;
        } else {
            newNode.prev = pointer;
            pointer.next = newNode;
        }

        pointer = newNode;
        pointerIndex++;
        size = pointerIndex;
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
     * @return The next element in the list
     */
    public E redo() {
        if (pointer.next == null) {
            throw new NoSuchElementException();
        }
        pointerIndex++;
        Node next = pointer.next;
        pointer = next;
        return next.element;
    }

    /**
     * @return The previous element in the list
     */
    public E undo() {
        if (pointer.prev == null) {
            throw new NoSuchElementException();
        }
        pointerIndex--;
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
     * @return The size of the list
     */
    public int size() {
        return size;
    }

    /**
     * @return A boolean for whether the list is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Deletes all elements in the list and sets the size to 0
     */
    public void clear() {
        head = null;
        pointer = null;
        size = 0;
        pointerIndex = 0;
    }


    /**
     * @return A string representation of all elements in the list
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
}
