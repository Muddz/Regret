package com.muddzdev.regret;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UndoRedoManagerTest {

    private UndoRedoManager undoRedoManager;
    private static final int TEST_ENTRIES = 3;

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
        Assert.assertEquals(1, undoRedoManager.redo().getValue());
        Assert.assertEquals(2, undoRedoManager.redo().getValue());
        Assert.assertEquals(3, undoRedoManager.redo().getValue());
    }

    @Test
    public void testUndo() {
        Assert.assertEquals(2, undoRedoManager.undo().getValue());
        Assert.assertEquals(1, undoRedoManager.undo().getValue());
        Assert.assertEquals(0, undoRedoManager.undo().getValue());
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
        Assert.assertEquals(TEST_ENTRIES, record.getValue());
    }


}
