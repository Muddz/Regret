package com.muddzdev.regret;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SerializationTest {

    private int[] testElements = {1, 2, 3, 4, 5};
    private UndoRedoList<Integer> undoRedo;
    private Gson gson = new Gson();

    @Before
    public void setUp() {
        undoRedo = new UndoRedoList<>();
        for (Integer i : testElements) {
            undoRedo.add(i);
        }
    }

    @Test
    public void serializeWithGsonTest() {
        String json = gson.toJson(undoRedo);
        Assert.assertFalse(json.isEmpty());
        System.out.println("toJson() result: \n" + json);

        UndoRedoList<Integer> undoRedo = gson.fromJson(json, new TypeToken<UndoRedoList<Integer>>(){}.getType());
        Assert.assertEquals(testElements.length, undoRedo.size());
        System.out.println("fromJson() result: \n" + undoRedo.toString());
    }


}
