package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.constants.Constants;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShoppingListActivity extends AppCompatActivity {
    private int nextItemId = 1;
    private ListView listViewDefault;
    private static List<ShoppingList> shoppingLists = new ArrayList<>();
    private ShoppingListAdapter shoppingListAdapter;
    private ActionMode actionMode;
    private View selectedView;

    private ActionMode.Callback actionModeCallback = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.main_view_context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (Constants.SELECTED_POSITION == -1) {
                return ShoppingListActivity.super.onContextItemSelected(item);
            }

            if (item.getItemId() == R.id.menuItemEdit) {
                editList(Constants.SELECTED_POSITION);
                mode.finish();
                return true;
            } else if (item.getItemId() == R.id.menuItemDelete) {
                deleteList(Constants.SELECTED_POSITION);
                mode.finish();
                return true;
            }

            Constants.SELECTED_POSITION = -1;
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            if(selectedView != null) {
                selectedView.setBackgroundColor(Color.TRANSPARENT);
            }

            actionMode = null;
            selectedView = null;

            listViewDefault.setEnabled(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.default_shopping_lists);

        listViewDefault = findViewById(R.id.listViewDefaultShoppingLists);

        setTitle(getString(R.string.view_name_shopping_lists));

        populateListViewShoppingLists();
        listViewDefault.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList clickedList = shoppingLists.get(position);
                String itemName = clickedList.getName();

                Constants.SELECTED_POSITION = position;
                view.setSelected(true);

                Toast.makeText(ShoppingListActivity.this, getString(R.string.itemClicked) + itemName, Toast.LENGTH_SHORT).show();
            }
        });

        registerForContextMenu(listViewDefault);

        listViewDefault.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listViewDefault.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                if(actionMode != null) {
                    return false;
                }

                Constants.SELECTED_POSITION = position;
                selectedView = view;
                selectedView.setBackgroundColor(Color.LTGRAY);
                listViewDefault.setEnabled(false);

                actionMode = startSupportActionMode(actionModeCallback);

                return true;
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
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.main_view_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(item.getItemId() == R.id.menuItemEdit) {
            editList(menuInfo.position);
            Toast.makeText(this, R.string.toast_message_edit, Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.menuItemDelete) {
            deleteList(menuInfo.position);
            Toast.makeText(this, R.string.toast_message_deleted, Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Constants.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String listName = data.getStringExtra(Constants.EXTRA_NEW_LIST_NAME);
            String listPriority = data.getStringExtra(Constants.EXTRA_LIST_PRIORITY);

            ShoppingList newList = new ShoppingList(nextItemId, listName, new Date(), listPriority);
            shoppingLists.add(newList);

            nextItemId++;
        }

        shoppingListAdapter.notifyDataSetChanged();
    }

    private void populateListViewShoppingLists() {
        shoppingListAdapter = new ShoppingListAdapter(this, shoppingLists);
        listViewDefault.setAdapter(shoppingListAdapter);
    }

    private void editList(int position) {
        Intent intent = new Intent(this, ListRegistrationActivity.class);
        intent.putExtra(Constants.EXTRA_LIST_ID, shoppingLists.get(position).getId());
        intent.putExtra(Constants.EXTRA_LIST_NAME, shoppingLists.get(position).getName());
        intent.putExtra(Constants.EXTRA_LIST_PRIORITY, shoppingLists.get(position).getPriority());
        intent.putExtra(Constants.EXTRA_EDITED_POSITION, position);
        startActivityForResult(intent, Constants.REQUEST_CODE);
    }

    private void deleteList(int position) {
        shoppingLists.remove(position);
        shoppingListAdapter.notifyDataSetChanged();
    }

    public static List<ShoppingList> getShoppingLists() {
        return shoppingLists;
    }

    public static void setShoppingLists(List<ShoppingList> shoppingLists) {
        ShoppingListActivity.shoppingLists = shoppingLists;
    }
}