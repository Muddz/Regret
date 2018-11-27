package com.muddzdev.regret;


import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RecordTest {

    private final String OBJECT_NAME = "OBJECT_NAME";

    @Test
    public void testIntObject() {
        Record record = new Record(OBJECT_NAME, 2);
        int object = (int) record.getValue();
        assertEquals(2, object);
    }

    @Test
    public void testIntArrayObject() {
        int testNumbers[] = {1, 2, 3};
        Record record = new Record(OBJECT_NAME, testNumbers);
        int[] object = (int[]) record.getValue();
        assertArrayEquals(testNumbers, object);
    }

    @Test
    public void testArrayListObject() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(Integer.MAX_VALUE);
        Record record = new Record(OBJECT_NAME, list);
        ArrayList object = (ArrayList) record.getValue();
        assertEquals(list, object);
    }


    @Test
    public void testObjectName() {
        Record record = new Record(OBJECT_NAME, OBJECT_NAME);
        String object = (String) record.getValue();
        assertEquals(OBJECT_NAME, object);
    }

}
