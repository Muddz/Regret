package com.muddzdev.regret;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.LinkedList;

public interface StorageContract {

    interface Storage {
        Session getSession();
        void saveSession(@NonNull Session session);
        void clearSession();

    }

}
