package com.github.wellfernandes.shoppinglistmanager.repository;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.github.wellfernandes.shoppinglistmanager.database.DatabaseConnection;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingListDAO;

import java.util.List;

public class ShoppingListRepository {
    private final ShoppingListDAO shoppingListDao;
    private final LiveData<List<ShoppingList>> allShoppingLists;

    public ShoppingListRepository(Context context) {
        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(context);
        shoppingListDao = databaseConnection.shoppingListDAO();
        allShoppingLists = shoppingListDao.getAllShoppingLists();
    }

    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }
    public void insert(ShoppingList shoppingList) {
        shoppingListDao.insert(shoppingList);
    }
    public void update(ShoppingList shoppingList) {
        shoppingListDao.update(shoppingList);
    }
    public void delete(ShoppingList shoppingList) {
        shoppingListDao.delete(shoppingList);
    }
    public LiveData<ShoppingList> getShoppingListById(int id) {
       return shoppingListDao.getShoppingListById(id);
    }
}