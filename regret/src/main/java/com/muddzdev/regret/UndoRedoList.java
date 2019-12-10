package com.muddzdev.regret;

import android.util.Log;

import androidx.annotation.NonNull;

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
     * Adds an element to the list
     */
//    public void add(E element) {
//        Node newNode = new Node(element);
//        if (head == null) {
//            head = newNode;
//        } else {
//
//            //TODO When there is a difference between last added key and newst added key, just replace them instead of adding them sequentielly
//
//            if (newNode.element instanceof Record && pointer.element instanceof Record) {
//                if (!((Record) newNode.element).getKey().equals(((Record) pointer.element).getKey())) {
//
//                    //Todo if different keys between last added and newly added... do something
//                    Log.d("XXX","1");
//                    newNode.prev = pointer.prev;
//                    pointer = newNode;
//                    return;
//
//                }else{
//                    Log.d("XXX","2");
//                    newNode.prev = pointer;
//                    pointer.next = newNode;
//                }
//            }
//        }
//
//            newNode.prev = pointer;
//            pointer.next = newNode;
//
//            pointer = newNode;
//            pointerIndex++;
//            size = pointerIndex;
//        }
//    }

    public E getPrevious() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.prev.element;
    }

    public E getNext() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.next.element;
    }

    public E getCurrent() {
        if (pointer == null) {
            throw new NoSuchElementException();
        }
        return pointer.element;
    }

    /**
     * @return Moves the pointer to the next element in the list
     */
    public E redo() {
        if (pointer.next == null) {
            throw new NoSuchElementException();
        }
        pointerIndex++;
        pointer = pointer.next;
        return pointer.element;
    }

    /**
     * @return Moves the pointer to the previous element in the list
     */
    public E undo() {
        if (pointer.prev == null) {
            throw new NoSuchElementException();
        }
        pointerIndex--;
        pointer = pointer.prev;
        return pointer.element;
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
            sb.append(tempHead.element);
            tempHead = tempHead.next;
            if (tempHead != null) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(']').toString();
    }
}
