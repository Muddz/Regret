package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;


class Storage {

    private static final String KEY_REGRET_HISTORY = "KEY_REGRET_HISTORY";
    private SharedPreferences prefs;
    private Type classType;

    public Storage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        classType = new TypeToken<DoublyLinkedList<Record>>() {
        }.getType();
    }

    public void saveHistory(DoublyLinkedList<Record> history) {
        String json = new Gson().toJson(history, classType);
        prefs.edit().putString(KEY_REGRET_HISTORY, json).apply();
    }

    public DoublyLinkedList<Record> loadHistory() {
        String json = prefs.getString(KEY_REGRET_HISTORY, null);
        return new Gson().fromJson(json, classType);
    }

    public boolean hasHistory() {
        return prefs.contains(KEY_REGRET_HISTORY);
    }

    public void clear() {
        prefs.edit().remove(KEY_REGRET_HISTORY).apply();
    }

}
