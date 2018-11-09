package com.muddzdev.regret;

import android.content.Context;
import android.support.annotation.NonNull;

import io.paperdb.Book;
import io.paperdb.Paper;

import static com.muddzdev.regret.Session.KEY_PAPER_SESSION;


class Database {

    private Book book;
    private Context context;

    static Database getDatabaseManager() {
        return new Database();
    }

    private Database() {
        Paper.init(context);
        this.book = Paper.book(KEY_PAPER_SESSION);
    }

    Session getSession() {
        return book.read(KEY_PAPER_SESSION);
    }

    void saveSession(@NonNull Session session) {
        book.write(KEY_PAPER_SESSION, session);
    }

    void clearSession() {
        book.destroy();
    }

}
