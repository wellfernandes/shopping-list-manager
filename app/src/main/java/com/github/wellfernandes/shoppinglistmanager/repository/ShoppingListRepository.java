package com.github.wellfernandes.shoppinglistmanager.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.List;
@Dao
public interface ShoppingListDAO {
    @Insert
    void insert(ShoppingList shoppingList);
    @Update
    void update(ShoppingList shoppingList);
    @Delete
    void delete(ShoppingList shoppingList);
    @Query("SELECT * FROM shoppinglist WHERE id = :id")
    LiveData<ShoppingList> getShoppingListById(int id);
    @Query("SELECT * FROM shoppinglist ORDER BY createdAt ASC")
    LiveData<List<ShoppingList>> getAllShoppingLists();
}