package com.muddzdev.regret;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

public class UndoRedoList {

    private Node head;
    private Node pointer;
    private int pointerIndex;
    private int size;

    private static class Node {
        Action action;
        Node next = null;
        Node prev = null;

        Node(Action action) {
            this.action = action;
        }
    }

    /**
     * Adds an key-values pair data to the collection.
     * Both currentValue and newValue should be of the same key identifier
     */
    public void add(@NonNull String key, @NonNull Object currentValue, @NonNull Object newValue) {
        Node oldNode = new Node(new Action(key, currentValue));
        Node newNode = new Node(new Action(key, newValue));
        if (head == null || pointer == head) {
            oldNode.next = newNode;
            newNode.prev = oldNode;
            head = oldNode;
            pointerIndex = 2;
        } else {
            if (pointer.action.key.equals(key) || pointer.prev.action.key.equals(key)) {
                newNode.prev = pointer;
                pointer.next = newNode;
                pointerIndex++;
            } else {
                oldNode.next = newNode;
                newNode.prev = oldNode;
                pointer.next = oldNode;
                oldNode.prev = pointer;
                pointerIndex += 2;
            }
        }
        size = pointerIndex;
        pointer = newNode;
    }

    /**
     * @return the previous {@link Action} object without moving the pointer
     * @throws NoSuchElementException
     */
    public Action getPrevious() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.prev.action;
    }

    /**
     * @return the next {@link Action} object without moving the pointer
     * @throws NoSuchElementException
     */
    public Action getNext() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.next.action;
    }

    /**
     * @return the current {@link Action} object which the pointer is pointing at
     * @throws NoSuchElementException
     */
    public Action getCurrent() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.action;
    }

    /**
     * Moves the pointer one step forward
     *
     * @return Returns the next {@link Action} object or null if next object doesn't exists
     */
    @Nullable
    public Action redo() {
        if (pointer.next != null) {
            Node tempPointer = pointer;
            pointer = pointer.next;
            pointerIndex++;
            if (tempPointer.action.key.equals(pointer.action.key)) {
                return pointer.action;
            } else if (pointer.next != null) {
                pointerIndex++;
                pointer = pointer.next;
                return pointer.action;
            }
        }
        return null;
    }

    /**
     * Moves the pointer one step backwards
     *
     * @return Returns the previous {@link Action} object or null if next object doesn't exists
     */

    @Nullable
    public Action undo() {
        if (pointer.prev != null) {
            Node tempPointer = pointer;
            pointer = pointer.prev;
            pointerIndex--;
            if (tempPointer.action.key.equals(pointer.action.key)) {
                return pointer.action;
            } else if (pointer.prev != null) {
                pointerIndex--;
                pointer = pointer.prev;
                return pointer.action;
            }
        }
        return null;
    }

    /**
     * @return a boolean for whether a next element exists
     */
    public boolean canRedo() {
        return pointer != null && pointer.next != null;
    }

    /**
     * @return a boolean for whether a previous element exists
     */
    public boolean canUndo() {
        return pointer != null && pointer.prev != null;
    }

    /**
     * @return the size of the list
     */
    public int getSize() {
        return size;
    }

    /**
     * @return a boolean for whether the collection is empty or not
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Deletes all elements in the collection and sets the size to 0
     */
    public void clear() {
        head = null;
        pointer = null;
        size = 0;
        pointerIndex = 0;
    }


    /**
     * @return a string representation of all elements in the collection
     */
    @NonNull
    public String toString() {
        StringBuilder sb = new StringBuilder().append('{');
        Node tempNode = head;
        while (tempNode != null) {
            sb.append(String.format("%s=%s", tempNode.action.key, tempNode.action.value));
            tempNode = tempNode.next;
            if (tempNode != null) {
                sb.append(',').append(' ');
            }
        }
        return sb.append('}').toString();
    }
}
