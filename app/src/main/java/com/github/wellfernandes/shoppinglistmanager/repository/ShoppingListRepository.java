package com.github.wellfernandes.shoppinglistmanager.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.github.wellfernandes.shoppinglistmanager.constants.DatabaseConstants;

public class ShoppingListRepository extends SQLiteOpenHelper {

        public ShoppingListRepository(@Nullable Context context) {
        super(context, DatabaseConstants.DATABASE_NAME, null, DatabaseConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}