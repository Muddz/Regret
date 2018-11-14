package com.muddzdev.regret;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class StorageTest {

    private Storage storage;
    private DoublyLinkedList<Record> history;

    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        storage = new Storage(appContext);
        history = new DoublyLinkedList<>();
    }

    @Test
    public void testLoadHistory() {
        storage.saveHistory(history);
        assertTrue(storage.hasHistory());
        assertNotNull(storage.loadHistory());
    }

    @Test
    public void testSaveHistory() {
        storage.saveHistory(history);
        assertTrue(storage.hasHistory());
    }

    @Test
    public void testClearHistory() {
        storage.clear();
        assertFalse(storage.hasHistory());
    }
}