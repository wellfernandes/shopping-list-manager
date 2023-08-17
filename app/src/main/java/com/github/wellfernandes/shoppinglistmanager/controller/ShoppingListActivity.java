package com.github.wellfernandes.shoppinglistmanager.controller;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.Item;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView listViewDefault;
    private List<ShoppingList> shoppingLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_shopping_lists);

        listViewDefault = findViewById(R.id.listViewDefaultShoppingLists);

        populate();
    }

    public void populate() {

        String[] listNames = getResources().getStringArray(R.array.list_name);
        String[] itemNames = getResources().getStringArray(R.array.list_item_names);
        String[] qntItems = getResources().getStringArray(R.array.list_item_quantities);
        String[] priceItems = getResources().getStringArray(R.array.list_item_prices);

        shoppingLists = new ArrayList<>();

        for (int i = 0; i < listNames.length; i++) {

            ShoppingList shoppingList = new ShoppingList();
            shoppingList.setName(listNames[i]);

            List<Item> items = new ArrayList<>();

            items.add(new Item(i, itemNames[i], Integer.parseInt(qntItems[i]), Double.parseDouble(priceItems[i])));
            items.add(new Item(i, itemNames[i], Integer.parseInt(qntItems[i]), Double.parseDouble(priceItems[i])));
            items.add(new Item(i, itemNames[i], Integer.parseInt(qntItems[i]), Double.parseDouble(priceItems[i])));

            shoppingList.setItens(items);
            shoppingLists.add(shoppingList);
        }

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(this, shoppingLists);

        listViewDefault.setAdapter(shoppingListAdapter);
    }
}