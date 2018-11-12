package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

class Storage {

    private static final String KEY_SESSION = "REGRET_SESSION_KEY";
    private DoublyLinkedList<Record> list;
    private SharedPreferences prefs;

    public Storage(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        list = new DoublyLinkedList<>();
    }

    public void setList(DoublyLinkedList<Record> list) {
        this.list = list;
        saveList(list);
    }

    public DoublyLinkedList<Record> getList() {

        return list;

//        if (list == null) {
//            return loadList();
//        } else {
//            return list;
//        }
    }

    private DoublyLinkedList<Record> loadList() {
        String json = prefs.getString(KEY_SESSION, null);
        if (json != null) {
            Type classType = new TypeToken<DoublyLinkedList<Record>>() {
            }.getType();
            return new Gson().fromJson(json, classType);
        } else {
            return new DoublyLinkedList<>();
        }
    }

    private void saveList(DoublyLinkedList<Record> list) {
//        Type classType = new TypeToken<DoublyLinkedList<Record>>() {
//        }.getType();
//        String json = new Gson().toJson(list, classType);
//        prefs.edit().putString(KEY_SESSION, json).apply();
    }

    public void clear() {
        prefs.edit().remove(KEY_SESSION).apply();
    }

}
