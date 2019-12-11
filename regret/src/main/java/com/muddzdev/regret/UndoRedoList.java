package com.muddzdev.regret;

import android.util.Log;

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

    public class Record {
        String key;
        Object value;

        Record(String key, Object value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            return
                    obj instanceof Record && ((Record) obj).key.equals(key) && ((Record) obj).value.equals(value);
        }
    }


    private class Node {
        Record record;
        Node next = null;
        Node prev = null;
        Node(Record record) {
            this.record = record;
        }
    }


    /**
     * Adds an element to the list
     */
    public void add(@NonNull String key, @NonNull Object oldValue, @NonNull Object newValue) {
        Node oldNode = new Node(new Record(key, oldValue));
        Node newNode = new Node(new Record(key, newValue));

        if (head == null) {
            oldNode.next = newNode;
            newNode.prev = oldNode;
            head = oldNode;
        } else if (!oldNode.record.equals(newNode.record)) {
            if (pointer.record.key.equals(oldNode.record.key)) {
                if (pointer.record.equals(oldNode.record)) {
                    newNode.prev = pointer;
                    pointer.next = newNode;
                }
            } else {
                oldNode.prev = pointer;
                pointer.next = oldNode;
                oldNode.next = newNode;
                newNode.prev = oldNode;
            }
        }
        pointer = newNode;
        pointerIndex++;
        size = pointerIndex;
    }

    public Record getPrevious() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.prev.record;
    }

    public Record getNext() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.next.record;
    }

    public Record getCurrent() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.record;
    }

    /**
     * @return Moves the pointer to the next element in the list
     */
    public Record redo() {
        if (pointer.next == null) {
            throw new NoSuchElementException();
        }
        pointerIndex++;
        pointer = pointer.next;
        return pointer.record;
    }

    /**
     * @return Moves the pointer to the previous element in the list
     */
    public Record undo() {
        if (pointer.prev == null) {
            throw new NoSuchElementException();
        }
        pointerIndex--;
        pointer = pointer.prev;

        return pointer.record;
    }


    /**
     * @return A boolean for whether a next element exists
     */
    public boolean canRedo() {
        return pointer != null && pointer.next != null;
    }

    /**
     * @return A boolean for whether a previous element exists
     */
    public boolean canUndo() {
        return pointer != null && pointer.prev != null;
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
            sb.append(tempHead.record);
            tempHead = tempHead.next;
            if (tempHead != null) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(']').toString();
    }
}
