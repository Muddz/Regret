package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import org.apache.commons.lang3.SerializationUtils;


class Storage {

    private static final String KEY_DEFAULT_REGRET_INSTANCE = "KEY_DEFAULT_REGRET_INSTANCE";
    private SharedPreferences prefs;

    public Storage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void saveHistory(DoublyLinkedList<Record> history) {
        String value = serialize(history);
        prefs.edit().putString(KEY_DEFAULT_REGRET_INSTANCE, value).apply();
    }

    public DoublyLinkedList<Record> loadHistory() {
        String value = prefs.getString(KEY_DEFAULT_REGRET_INSTANCE, null);
        DoublyLinkedList<Record> list = deserialize(value);
        return list;
    }

    public boolean hasHistory() {
        return prefs.contains(KEY_DEFAULT_REGRET_INSTANCE);
    }

    public void clear() {
        prefs.edit().remove(KEY_DEFAULT_REGRET_INSTANCE).apply();
    }

    private String serialize(DoublyLinkedList<Record> doublyLinkedList) {
        byte[] bytes = SerializationUtils.serialize(doublyLinkedList);
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private DoublyLinkedList<Record> deserialize(String string) {
        byte[] bytes = Base64.decode(string, Base64.DEFAULT);
        return SerializationUtils.deserialize(bytes);
    }


}
