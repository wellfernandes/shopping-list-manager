package com.github.wellfernandes.shoppinglistmanager.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        listViewDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList clickedList = shoppingLists.get(position);
                String itemName = clickedList.getName();
                Toast.makeText(ShoppingListActivity.this, getString(R.string.itemClicked) + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void populate() {

        String[] listNames = getResources().getStringArray(R.array.list_name);
        String[] listCreatedAt = getResources().getStringArray(R.array.list_data);
        String[] listItemsQuantity = getResources().getStringArray(R.array.list_items_quantity);
        String[] listPriority = getResources().getStringArray(R.array.list_priority);

        shoppingLists = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        for (int i = 0; i < listNames.length; i++) {

            try {
                // Parse the string date into a Date object
                Date createdAt = dateFormat.parse(listCreatedAt[i]);

                // new shopping list
                ShoppingList shoppingList = new ShoppingList();

                shoppingList.setId(i+1);
                shoppingList.setName(listNames[i]);
                shoppingList.setCreatedAt(createdAt);
                shoppingList.setItemsQuantity(listItemsQuantity[i]);
                shoppingList.setPriority(listPriority[i]);

                // add shopping list to array shoppinglists
                shoppingLists.add(shoppingList);
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(this, shoppingLists);

        listViewDefault.setAdapter(shoppingListAdapter);
    }
}