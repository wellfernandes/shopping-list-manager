package com.github.wellfernandes.shoppinglistmanager.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.Item;

import java.util.ArrayList;

public class ListShoppingList extends AppCompatActivity {

    private ListView listViewDefault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_shopping_lists);


        listViewDefault = findViewById(R.id.listViewDefaultShoppingLists);

        populate();
    }

    public void populate(){
        String[] listItems = getResources().getStringArray(R.array.list_items);
        String[] qntItems = getResources().getStringArray(R.array.qnt_items);
        String[] priceItems = getResources().getStringArray(R.array.price);


        ArrayList<Item> items = new ArrayList<>();

        for (int i = 0; i < listItems.length; i++) {
            Integer qnt = Integer.parseInt(priceItems[i]);
            Double price = Double.parseDouble(priceItems[i]);

            items.add(new Item(i,listItems[i], qnt, price));
        }

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(this, items);

        listViewDefault.setAdapter(shoppingListAdapter);
    }
}
