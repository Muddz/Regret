package com.muddzdev.regret;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class StorageTest {

    private Storage storage;
    private DoublyLinkedList<Record> doublyLinkedList;
    private LinkedList<Record> linkedList;

    @Before
    public void setup() throws IOException {
//        storage = new Storage(InstrumentationRegistry.getTargetContext());
//        doublyLinkedList = new DoublyLinkedList<>();
        linkedList = new LinkedList<>();
        linkedList.add(new Record("H", Integer.MAX_VALUE));
        linkedList.add(new Record("E", "HELLO"));
        linkedList.add(new Record("P", new JP()));

    }

    class JP {
        private int age = Integer.MAX_VALUE;
        private boolean hasSex = true;
        private boolean hasLicense = false;
        private List<Integer> girls = new ArrayList<>();
    }


    @Test
    public void testGson(){

        String json = new Gson().toJson(linkedList);

        linkedList = new Gson().fromJson(json, LinkedList.class);
    }

//    @Test
//    public void testLoadHistory() {
//        storage.saveHistory(doublyLinkedList);
//        assertTrue(storage.hasHistory());
//        assertNotNull(storage.loadHistory());
//    }
//
//    @Test
//    public void testSaveHistory() {
//        storage.saveHistory(doublyLinkedList);
//        assertTrue(storage.hasHistory());
//    }
//
//    @Test
//    public void testClearHistory() {
//        storage.clear();
//        assertFalse(storage.hasHistory());
//    }
}