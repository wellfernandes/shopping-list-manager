package com.github.wellfernandes.shoppinglistmanager.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.constants.Constants;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {

    private ListView listViewDefault;
    private static List<ShoppingList> shoppingLists = new ArrayList<>();
    private ShoppingListAdapter shoppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_shopping_lists);

        listViewDefault = findViewById(R.id.listViewDefaultShoppingLists);

        setTitle("Listas de Compras");

        populateListViewShoppingLists();
        listViewDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList clickedList = shoppingLists.get(position);
                String itemName = clickedList.getName();
                Toast.makeText(ShoppingListActivity.this, getString(R.string.itemClicked) + itemName, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_view_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemAdd) {
            Intent intent = new Intent(this, ListRegistrationActivity.class);
            startActivityForResult(intent, Constants.REQUEST_CODE);
        }else if(item.getItemId() == R.id.menuItemAbout) {
            Intent intent = new Intent(this, AboutAppActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            String listName = data.getStringExtra(Constants.EXTRA_NEW_LIST_NAME);
            String listPriority = data.getStringExtra(Constants.EXTRA_LIST_PRIORITY);

            ShoppingList newList = new ShoppingList(shoppingLists.size() + 1, listName, new Date(), listPriority);
            shoppingLists.add(newList);

            shoppingListAdapter.notifyDataSetChanged();
        }
    }

    private void populateListViewShoppingLists() {

        shoppingListAdapter = new ShoppingListAdapter(this, shoppingLists);
        listViewDefault.setAdapter(shoppingListAdapter);
    }
}