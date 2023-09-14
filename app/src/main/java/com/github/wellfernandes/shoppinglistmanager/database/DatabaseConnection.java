package com.github.wellfernandes.shoppinglistmanager.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.github.wellfernandes.shoppinglistmanager.constants.DatabaseConstants;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
import com.github.wellfernandes.shoppinglistmanager.repository.ShoppingListRepository;
@Database(entities = {ShoppingList.class}, version = 1, exportSchema = false)
public abstract class DatabaseConnection extends RoomDatabase {
    private static DatabaseConnection databaseConnection;
    public abstract ShoppingListRepository shoppingListRepository();
    public static synchronized DatabaseConnection getInstance(Context context) {
        if (databaseConnection == null) {
            databaseConnection = Room.databaseBuilder(
                            context.getApplicationContext(),
                            DatabaseConnection.class,
                            DatabaseConstants.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return databaseConnection;
    }
}