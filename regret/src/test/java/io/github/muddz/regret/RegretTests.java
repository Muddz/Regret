package io.github.muddz.regret;

import android.graphics.Color;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegretTests implements Regret.RegretListener {

    private static final String KEY_TEXT_COLOR = "KEY_TEXT_COLOR";
    private static final String KEY_BACKGROUND_COLOR = "KEY_BACKGROUND_COLOR";
    private boolean isUndoing, isRedoing, isClearing;
    private Regret regret;

    @Before
    public void setUp() {
        regret = new Regret(this);
        regret.add(KEY_TEXT_COLOR, Color.BLACK, Color.RED);
        regret.add(KEY_TEXT_COLOR, Color.RED, Color.YELLOW);
        regret.add(KEY_BACKGROUND_COLOR, Color.WHITE, Color.GREEN);
        regret.add(KEY_BACKGROUND_COLOR, Color.GREEN, Color.BLACK);
    }


    @Test
    public void testCanUndo() {
        Assert.assertTrue(regret.canUndo());
        Assert.assertTrue(regret.canUndo());
    }

    @Test
    public void testCanRedo() {
        regret.undo();
        regret.undo();
        Assert.assertTrue(regret.canRedo());
        Assert.assertTrue(regret.canRedo());
    }

    @Test
    public void testGetCurrent() {
        Action action = regret.getCurrent();
        Assert.assertEquals(Color.BLACK, action.value);
    }

    @Test
    public void testUndo() {
        isUndoing = true;
        regret.undo();
        isUndoing = false;
    }

    @Test
    public void testRedo() {
        regret.undo();
        isRedoing = true;
        regret.redo();
        isRedoing = false;
    }

    @Test
    public void testClear() {
        isClearing = true;
        regret.clear();
        isClearing = false;
        Assert.assertTrue(regret.isEmpty());
        Assert.assertFalse(regret.canUndo());
        Assert.assertFalse(regret.canRedo());
        Assert.assertEquals(0, regret.getSize());
    }


    @Override
    public void onDo(String key, Object value) {
        if (isUndoing) {
            if (key.equals(KEY_BACKGROUND_COLOR)) {
                Assert.assertEquals(Color.GREEN, value);
            }
        } else if (isRedoing) {
            if (key.equals(KEY_BACKGROUND_COLOR)) {
                Assert.assertEquals(Color.BLACK, value);
            }
        }
    }

    @Override
    public void onCanDo(boolean canUndo, boolean canRedo) {
        if (isClearing) {
            Assert.assertFalse(canUndo);
            Assert.assertFalse(canRedo);
        } else if (isUndoing) {
            Assert.assertTrue(canUndo);
            Assert.assertTrue(canRedo);
        } else if (isRedoing) {
            Assert.assertTrue(canUndo);
            Assert.assertFalse(canRedo);
        }
    }
}
