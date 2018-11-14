package com.muddzdev.regret;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DoublyLinkedListTest {

    private static final String NAME_TEST_NUMBERS = "NAME_TEST_NUMBERS";
    private static final String NAME_INT_VALUE = "NAME_INT_VALUE";
    private static final String NAME_INT_VALUES = "NAME_INT_VALUES";
    private static final String NAME_INTEGER_VALUE = "NAME_INTEGER_VALUE";
    private static final String NAME_BOOLEAN_VALUE = "NAME_BOOLEAN_VALUE";
    private static final String NAME_CHAR_VALUE = "NAME_CHAR_VALUE";
    private static final String NAME_STRING_VALUE = "NAME_STRING_VALUE";
    private DoublyLinkedList<Record> linkedList;
    private int testNumbers[] = {1, 2, 3, 4, 5, 6};

    @Before
    public void setup() {
        linkedList = new DoublyLinkedList<>();
        for (int i : testNumbers) {
            linkedList.add(new Record(NAME_TEST_NUMBERS, i));
        }
    }


    @Test
    public void testUndo() {
        System.out.println("----- Undo Test -----");

        assertFalse(linkedList.canRedo());
        assertTrue(linkedList.canUndo());
        int listSize = linkedList.size() - 1;

        for (int i = listSize; i > 0; i--) {
            Record record = linkedList.undo();
            int value = (int) record.getObject();
            System.out.println(String.format("Undoing to %d", value));
            assertEquals(i, value);

        }
    }

    @Test
    public void testRedo() {
        System.out.println("----- Redo Test -----");

        while (linkedList.canUndo()) {
            linkedList.undo();
        }

        assertFalse(linkedList.canUndo());
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
        int targetValue = 0;
        int newValue = 9;

        //We undo from the end to the middle (3) of the list.
        while (linkedList.canUndo()) {
            Record record = linkedList.undo();
            int value = (int) record.getObject();
            System.out.println(String.format("Undoing to %d", value));
            if (value == 3) {
                linkedList.add(new Record(NAME_TEST_NUMBERS, newValue));
                linkedList.undo();
            }
        }

        //Then we redo the whole way to the end
        while (linkedList.canRedo()) {
            Record record = linkedList.redo();
            targetValue = (int) record.getObject();
            System.out.println(String.format("Redoing to %d", targetValue));
        }

        assertEquals(newValue, targetValue);
        assertTrue(!linkedList.canRedo());
        assertTrue(linkedList.canUndo());
    }

    @Test
    public void testInsertElementAtTheStart() {
        while (linkedList.canUndo()) {
            linkedList.undo();
        }

        linkedList.add(new Record(NAME_TEST_NUMBERS, 9));
        assertEquals(2, linkedList.size());
    }

    @Test
    public void testMixedElementTypes() {
        int intValue = 1;
        int[] intValues = {1, 2, 3, 4};
        Integer integerValue = 2;
        boolean booleanValue = true;
        char charValue = 'H';
        String stringValue = "Hello";

        linkedList.add(new Record(NAME_INT_VALUE, intValue));
        linkedList.add(new Record(NAME_INT_VALUES, intValues));
        linkedList.add(new Record(NAME_INTEGER_VALUE, integerValue));
        linkedList.add(new Record(NAME_BOOLEAN_VALUE, booleanValue));
        linkedList.add(new Record(NAME_CHAR_VALUE, charValue));
        linkedList.add(new Record(NAME_STRING_VALUE, stringValue));
        linkedList.add(new Record("EMPTY", 0));

        while (linkedList.canUndo()) {
            Record record = linkedList.undo();
            Object o = record.getObject();
            switch (record.getObjectName()) {
                case NAME_INT_VALUE:
                    assertEquals(intValue, o);
                    break;
                case NAME_INT_VALUES:
                    assertArrayEquals(intValues, (int[]) o);
                    break;
                case NAME_INTEGER_VALUE:
                    assertEquals(integerValue, o);
                    break;
                case NAME_BOOLEAN_VALUE:
                    assertEquals(booleanValue, o);
                    break;
                case NAME_CHAR_VALUE:
                    assertEquals(charValue, o);
                    break;
                case NAME_STRING_VALUE:
                    assertEquals(stringValue, o);
                    break;
            }
        }
    }

    @Test
    public void testSize() {
        int listSize = linkedList.size();
        assertEquals(testNumbers.length, listSize);
    }

    @Test
    public void testClearList() {
        linkedList.clear();
        assertTrue(linkedList.isEmpty());
        assertEquals(0, linkedList.size());
    }

}