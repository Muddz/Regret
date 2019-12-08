package com.muddzdev.regret;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UndoRedoManagerTest {

    private static final int TEST_ENTRIES = 3;
    private UndoRedoManager undoRedoManager;

    @Before
    public void setUp() {
        undoRedoManager = new UndoRedoManager();
        for (int i = 0; i <= TEST_ENTRIES; i++) {
            undoRedoManager.add(String.valueOf(i), i);
        }
    }


    @Test
    public void testRedo() {
        while (undoRedoManager.canUndo()) {
            undoRedoManager.undo();
        }
        Assert.assertEquals(1, undoRedoManager.redo().getOldValue());
        Assert.assertEquals(2, undoRedoManager.redo().getOldValue());
        Assert.assertEquals(3, undoRedoManager.redo().getOldValue());
    }

    @Test
    public void testUndo() {
        Assert.assertEquals(2, undoRedoManager.undo().getOldValue());
        Assert.assertEquals(1, undoRedoManager.undo().getOldValue());
        Assert.assertEquals(0, undoRedoManager.undo().getOldValue());
    }

    @Test
    public void testCanRedo() {
        undoRedoManager.undo();
        Assert.assertTrue(undoRedoManager.canRedo());
    }

    @Test
    public void testCanUndo() {
        Assert.assertTrue(undoRedoManager.canUndo());
    }

    @Test
    public void testIsEmpty() {
        Assert.assertFalse(undoRedoManager.isEmpty());
        undoRedoManager.clear();
        Assert.assertTrue(undoRedoManager.isEmpty());
    }

    @Test
    public void testGetCurrentValue() {
        Record record = undoRedoManager.getCurrent();
        Assert.assertEquals(TEST_ENTRIES, record.getOldValue());
    }


    @Test
    public void testClearHistory() {
        undoRedoManager.clear();
        Assert.assertFalse(undoRedoManager.canUndo());
        Assert.assertFalse(undoRedoManager.canRedo());
        Assert.assertTrue(undoRedoManager.isEmpty());
    }

}
