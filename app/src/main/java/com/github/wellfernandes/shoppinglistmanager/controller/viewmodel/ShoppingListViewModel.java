package com.github.wellfernandes.shoppinglistmanager.controller.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
import com.github.wellfernandes.shoppinglistmanager.service.ShoppingListService;
import java.util.List;
public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListService shoppingListService;
    private LiveData<List<ShoppingList>> allShoppingLists;
    public ShoppingListViewModel(Application application) {
        super(application);
        shoppingListService = new ShoppingListService(application);
        allShoppingLists = shoppingListService.getAllShoppingLists();
    }
    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }

    public LiveData<ShoppingList> getShoppingListById(int id) {
        return shoppingListService.getShoppingListById(id);
    }
}