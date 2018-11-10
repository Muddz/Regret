package com.muddzdev.regret;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import io.paperdb.Book;
import io.paperdb.Paper;

import static com.muddzdev.regret.Session.KEY_PAPER_SESSION;


class Database {

    private Book book;

    static Database getDatabaseManager(Context context) {
        return new Database(context);
    }

    private Database(final Context context) {


        Paper.init(context);
        this.book = Paper.book(KEY_PAPER_SESSION);
    }

    Session getSession() {
        return book.read(KEY_PAPER_SESSION);
    }

    void saveSession(@NonNull final Session session) {
        book.write(KEY_PAPER_SESSION, session);
    }

    void clearSession() {
        book.destroy();
    }

}
