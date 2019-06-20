package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;
import org.apache.commons.lang3.SerializationUtils;


class PersistentStorage {

    private static final String KEY_DEFAULT_REGRET_INSTANCE = "KEY_DEFAULT_REGRET_INSTANCE";
    private SharedPreferences prefs;

    public PersistentStorage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveList(UndoRedoList<Record> undoRedoList) {
        String value = serialize(undoRedoList);
        prefs.edit().putString(KEY_DEFAULT_REGRET_INSTANCE, value).apply();
    }

    public UndoRedoList<Record> loadList() {
        String value = prefs.getString(KEY_DEFAULT_REGRET_INSTANCE, null);
        return deserialize(value);
    }

    public boolean hasList() {
        return prefs.contains(KEY_DEFAULT_REGRET_INSTANCE);
    }

    public void clear() {
        prefs.edit().remove(KEY_DEFAULT_REGRET_INSTANCE).apply();
    }

    private String serialize(UndoRedoList<Record> undoRedoList) {
        byte[] bytes = SerializationUtils.serialize(undoRedoList);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private UndoRedoList<Record> deserialize(String string) {
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return SerializationUtils.deserialize(bytes);
    }


}
