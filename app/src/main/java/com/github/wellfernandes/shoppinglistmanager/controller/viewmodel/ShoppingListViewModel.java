package com.github.wellfernandes.shoppinglistmanager.controller.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;
import com.github.wellfernandes.shoppinglistmanager.repository.ShoppingListRepository;
import java.util.List;
public class ShoppingListViewModel extends AndroidViewModel {
    private ShoppingListRepository shoppingListRepository;
    private LiveData<List<ShoppingList>> allShoppingLists;
    public ShoppingListViewModel(Application application) {
        super(application);
        shoppingListRepository = new ShoppingListRepository(application);
        allShoppingLists = shoppingListRepository.getAllShoppingLists();
    }
    public LiveData<List<ShoppingList>> getAllShoppingLists() {
        return allShoppingLists;
    }
    public void delete(ShoppingList shoppingList) {
        shoppingListRepository.delete(shoppingList);
    }
    public LiveData<ShoppingList> getShoppingListById(int id) {
        return shoppingListRepository.getShoppingListById(id);
    }
}