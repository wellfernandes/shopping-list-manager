package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.constants.Constants;

import java.util.ArrayList;

public class ListRegistrationActivity extends AppCompatActivity {

    private EditText editTextNewList;
    private Spinner spinnerLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_registration);

        setTitle("Cadastrar Nova Lista");

        editTextNewList = findViewById(R.id.editTextNewList);
        spinnerLists = findViewById(R.id.spinnerLists);

        populateSpinnerPriorities();
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
        }else if(item.getItemId() == R.id.menuItemCancel) {
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

        if (!listName.isEmpty()) {
            Intent intent = new Intent();
            intent.putExtra(Constants.EXTRA_NEW_LIST_NAME, listName);
            intent.putExtra(Constants.EXTRA_LIST_PRIORITY, listPriority);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Toast.makeText(this, R.string.messageInsertListName, Toast.LENGTH_SHORT).show();
        }
    }

    public void clearText(View view) {
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