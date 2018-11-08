package com.muddzdev.regret;

import io.paperdb.Book;
import io.paperdb.Paper;


 class Database implements StorageContract.Storage {

    private Book storage;

    static Database getDatabaseManager() {
        return new Database();
    }

     private Database() {
        this.storage = Paper.book();
    }

    @Override
    public Session getSession() {
        return storage.read(Session.KEY_PAPER_SESSION);
    }

    @Override
    public void saveSession(Session session) {
        storage.write(Session.KEY_PAPER_SESSION, session);
    }


    @Override
    public void clearSession() {
        storage.destroy();
    }

}
