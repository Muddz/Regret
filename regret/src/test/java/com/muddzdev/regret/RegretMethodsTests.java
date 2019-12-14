package com.muddzdev.regret;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class RegretMethodsTests implements Regret.RegretListener {

    private Regret regret;
    private static final String TEXT_COLOR_RED = "TEXT_COLOR_RED";
    private static final String TEXT_COLOR_GREEN = "TEXT_COLOR_GREEN";
    private static final String BACKGROUND_COLOR_WHITE = "BACKGROUND_COLOR_WHITE";
    private static final String BACKGROUND_COLOR_BLACK = "BACKGROUND_COLOR_BLACK";


    @Before
    public void setUp() {
        regret = new Regret(this);
        regret.add(TEXT_COLOR_RED, Color.WHITE, Color.RED);
        regret.add(TEXT_COLOR_GREEN, Color.RED, Color.GREEN);
        regret.add(BACKGROUND_COLOR_WHITE, Color.GREEN, Color.WHITE);
        regret.add(BACKGROUND_COLOR_BLACK, Color.WHITE, Color.BLACK);
    }


    @Test
    public void testCanUndo() {
        Assert.assertTrue(regret.canUndo());
        Assert.assertTrue(regret.canUndo());
    }

    @Test
    public void testCanRedo() {
        regret.undo();
        Assert.assertTrue(regret.canRedo());
    }


    @Test(expected = NoSuchElementException.class)
    public void testClear() {
        regret.clear();
        Assert.assertTrue(regret.isEmpty());
        Assert.assertFalse(regret.canUndo());
        Assert.assertFalse(regret.canRedo());
        regret.getCurrent();
    }

    @Test
    public void testGetCurrentValue() {
        UndoRedoList.Record record = regret.getCurrent();
        Assert.assertEquals(Color.BLACK, record.value);
        regret.undo();
        record = regret.getCurrent();
        Assert.assertEquals(Color.WHITE, record.value);
    }


    @Test
    public void testUndo() {


    }

    @Test
    public void testRedo() {


    }


    @Override
    public void onDo(String key, Object value) {

    }

    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {

    }
}
