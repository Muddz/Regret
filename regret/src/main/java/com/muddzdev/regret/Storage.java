package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


class Storage {

    private static final String KEY_DEFAULT_REGRET_INSTANCE = "KEY_DEFAULT_REGRET_INSTANCE";
    private SharedPreferences prefs;
    private Type classType;

    public Storage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        classType = new TypeToken<DoublyLinkedList<Record>>() {}.getType();
    }

    public void saveHistory(DoublyLinkedList<Record> history) {
        String json = new Gson().toJson(history, classType);
        prefs.edit().putString(KEY_DEFAULT_REGRET_INSTANCE, json).apply();
    }

    public DoublyLinkedList<Record> loadHistory() {
        String json = prefs.getString(KEY_DEFAULT_REGRET_INSTANCE, null);
        return new Gson().fromJson(json, classType);
    }

    public boolean hasHistory() {
        return prefs.contains(KEY_DEFAULT_REGRET_INSTANCE);
    }

    public void clear() {
        prefs.edit().remove(KEY_DEFAULT_REGRET_INSTANCE).apply();
    }

}
