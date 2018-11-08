package com.muddzdev.regret;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 */
public class DoublyLinkedListTest {

    private static final String OBJECT_NAME_NUMBER = "OBJECT_NAME_NUMBER";
    private DoublyLinkedList<Record> linkedList;
    private int testNumbers[] = {1, 2, 3, 4, 5, 6};

    //TODO refactor method namings
    //TODO shall there be comments?
    //TODO over all refactoring and improvements
    //TODO println shall it be there?

    @Before
    public void setup() {
        linkedList = new DoublyLinkedList<>();
        for (int i : testNumbers) {
            linkedList.add(new Record(OBJECT_NAME_NUMBER, i));
        }
    }


    @Test
    public void testUndo() {
        System.out.println("----- Undo Test -----");
        assertTrue(linkedList.canUndo());
        int listSize = linkedList.size() - 1;

        for (int i = listSize; i > 0; i--) {
            Record record = linkedList.undo();
            int value = (int) record.getObject();
            assertEquals(i, value);
            System.out.println(String.format("Undoing to %d", value));
        }
    }

    @Test
    public void testRedo() {
        System.out.println("----- Redo Test -----");

        //Undo from end to start
        assertTrue(linkedList.canUndo());
        while (linkedList.canUndo()) {
            linkedList.undo();
        }

        //And now redo from start to end
        assertTrue(linkedList.canRedo());
        int listSize = linkedList.size() + 1;

        for (int i = 2; i < listSize; i++) {
            Record record = linkedList.redo();
            int value = (int) record.getObject();
            assertEquals(i, value);
            System.out.println(String.format("Redoing to %d", value));
        }
    }

    @Test
    public void testInsertElementInTheMiddle() {
        System.out.println("----- Change Middle element Test -----");
        int newListSize = 0;
        int targetValue = 0;
        int newValue = 9;

        //We undo from the end to the start of the list.
        while (linkedList.canUndo()) {
            Record record = linkedList.undo();
            int value = (int) record.getObject();
            System.out.println(String.format("Undoing to %d", value));
            if (value == 3) {
                linkedList.add(new Record(OBJECT_NAME_NUMBER, newValue));
                linkedList.undo();
            }
        }

        // and then we redo the whole way to the end
        while (linkedList.canRedo()) {
            Record record = linkedList.redo();
            targetValue = (int) record.getObject();
            newListSize++;
            System.out.println(String.format("Redoing to %d", targetValue));
        }

        assertEquals(newValue, targetValue);
        assertEquals(newListSize, linkedList.size());
        assertTrue(!linkedList.canRedo());
        assertTrue(linkedList.canUndo());
    }
//
//    @Test
//    public void testChangeStartElement() {
//        System.out.println("----- Change Start element Test -----");
//    }
//
//    @Test
//    public void testMixedElementTypes() {
//        System.out.println("----- Mixed element types Test -----");
//    }

    @Test
    public void testSize() {
        int listSize = linkedList.size();
        assertEquals(testNumbers.length, listSize);
    }

    @Test
    public void clearListTest() {
        linkedList.clear();
        assertTrue(linkedList.isEmpty());
        assertEquals(0, linkedList.size());
    }

}