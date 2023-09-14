package com.github.wellfernandes.shoppinglistmanager.service;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.github.wellfernandes.shoppinglistmanager.database.DatabaseConnection;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
import com.github.wellfernandes.shoppinglistmanager.repository.ShoppingListRepository;

import java.util.List;

public class ShoppingListService {
    private final ShoppingListRepository shoppingListRepository;
    private final LiveData<List<ShoppingList>> allShoppingLists;

    public ShoppingListService(Context context) {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(context);
        shoppingListRepository = databaseConnection.shoppingListRepository();
        allShoppingLists = shoppingListRepository.getAllShoppingLists();
    }

    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }

    public LiveData<ShoppingList> getShoppingListById(int id) {
       return shoppingListRepository.getShoppingListById(id);
    }
}