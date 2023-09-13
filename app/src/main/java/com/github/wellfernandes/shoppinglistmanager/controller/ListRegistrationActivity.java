package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.constants.AppConstants;
import com.github.wellfernandes.shoppinglistmanager.constants.ErrorConstants;
import com.github.wellfernandes.shoppinglistmanager.controller.viewmodel.ShoppingListViewModel;
import com.github.wellfernandes.shoppinglistmanager.database.DatabaseConnection;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.ArrayList;
import java.util.Date;

public class ListRegistrationActivity extends AppCompatActivity {
    private EditText editTextNewList;
    private Spinner spinnerLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registration);

        setTitle(getString(R.string.view_name_new_list));

        editTextNewList = findViewById(R.id.editTextNewList);
        spinnerLists = findViewById(R.id.spinnerLists);

        populateSpinnerPriorities();

        Intent intent = getIntent();
        if(intent.hasExtra(AppConstants.EXTRA_LIST_NAME)) {
            setTitle(getString(R.string.view_name_edit_list));
            String listName = intent.getStringExtra(AppConstants.EXTRA_LIST_NAME);
            editTextNewList.setText(listName);

            editTextNewList.setSelection(listName.length());

            String listPriority = intent.getStringExtra(AppConstants.EXTRA_LIST_PRIORITY);
            ArrayAdapter<CharSequence> adapter = (ArrayAdapter<CharSequence>) spinnerLists.getAdapter();
            if (adapter != null) {
                spinnerLists.setSelection(adapter.getPosition(listPriority));
            }
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list_registration_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuItemSave) {
            saveNewList();
        }else if(item.getItemId() == R.id.menuItemClear) {
            clearText();
        }else if(item.getItemId() == android.R.id.home) {
            cancelNewList();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    public void saveNewList() {
        String listName = editTextNewList.getText().toString();
        String listPriority = spinnerLists.getSelectedItem().toString();

        if (!listName.isEmpty() && getIntent().getIntExtra(AppConstants.EXTRA_LIST_ID, -1) == -1) {

            ShoppingList newList = new ShoppingList(listName, new Date(), listPriority);

            DatabaseConnection databaseConnection = DatabaseConnection.getInstance(this);
            databaseConnection.shoppingListDAO().insert(newList);
            
            setResult(RESULT_OK);
            finish();
        } else if (getIntent().getIntExtra(AppConstants.EXTRA_LIST_ID, -1) != -1) {
            int listToEdit = getIntent().getIntExtra(AppConstants.EXTRA_LIST_ID, -1);
            
            ShoppingListViewModel viewModel = new ViewModelProvider(this).
                    get(ShoppingListViewModel.class);

            LiveData<ShoppingList> shoppingListLiveData = viewModel.getShoppingListById(listToEdit);
            shoppingListLiveData.observe(this, new Observer<ShoppingList>() {
                @Override
                public void onChanged(ShoppingList shoppingList) {
                    if (shoppingList != null) {
                        shoppingList.setName(listName);
                        shoppingList.setPriority(listPriority);

                        DatabaseConnection databaseConnection = DatabaseConnection.getInstance(ListRegistrationActivity.this);
                        databaseConnection.shoppingListDAO().update(shoppingList);

                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(ListRegistrationActivity.this, ErrorConstants.ERROR_UPDATE_LIST, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, R.string.messageInsertListName, Toast.LENGTH_SHORT).show();
        }
    }

    public void clearText() {
        editTextNewList.setText("");
        Toast.makeText(this, R.string.messageCleanFields, Toast.LENGTH_SHORT).show();
    }

    private void populateSpinnerPriorities() {
        String[] xmlPriorities = getResources().getStringArray(R.array.list_priority);
        ArrayList<String> priorityList = new ArrayList<>();

        for (String priority : xmlPriorities) {
            priorityList.add(priority);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, priorityList);
        spinnerLists.setAdapter(adapter);
    }

    public void cancelNewList() {
        setResult(RESULT_CANCELED);
        finish();
    }
}