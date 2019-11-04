package com.muddzdev.regret;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UseCaseTest {

    //TODO better naming or include comment about this test

    private UndoRedoManager undoRedoManager;
    private static final String TEXT_COLOR_RED = "TEXT_COLOR_RED";
    private static final String TEXT_COLOR_GREEN = "TEXT_COLOR_GREEN";
    private static final String TEXT_COLOR_BLUE = "TEXT_COLOR_BLUE";
    private static final String BACKGROUND_COLOR_WHITE = "BACKGROUND_COLOR_WHITE";
    private static final String BACKGROUND_COLOR_BLACK = "BACKGROUND_COLOR_BLACK";


    @Before
    public void setUp() {
        undoRedoManager = new UndoRedoManager();
        undoRedoManager.add(TEXT_COLOR_RED, Color.RED);
        undoRedoManager.add(TEXT_COLOR_BLUE, Color.BLUE);
        undoRedoManager.add(TEXT_COLOR_GREEN, Color.GREEN);
        undoRedoManager.add(BACKGROUND_COLOR_WHITE, Color.WHITE);
        undoRedoManager.add(BACKGROUND_COLOR_BLACK, Color.BLACK);
    }

    @Test
    public void testUndoRedo() {
        Assert.assertEquals(Color.WHITE, undoRedoManager.undo().getValue());
        Assert.assertEquals(Color.GREEN, undoRedoManager.undo().getValue());
        Assert.assertEquals(Color.BLUE, undoRedoManager.undo().getValue());
        Assert.assertEquals(Color.RED, undoRedoManager.undo().getValue());

        Assert.assertEquals(Color.BLUE, undoRedoManager.redo().getValue());
        Assert.assertEquals(Color.GREEN, undoRedoManager.redo().getValue());
        Assert.assertEquals(Color.WHITE, undoRedoManager.redo().getValue());
        Assert.assertEquals(Color.BLACK, undoRedoManager.redo().getValue());
    }

}
