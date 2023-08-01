package com.github.wellfernandes.shoppinglistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNewList;
    private CheckBox checkBoxLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNewList = findViewById(R.id.editTextNewList);
        checkBoxLists = findViewById(R.id.checkBoxLists);

        checkBoxListener();

    }

    public void addNewList(View view){
        ShoppingList newList = new ShoppingList();

        newList.setName(String.valueOf(editTextNewList.getText()));

        // show the newly added list
        showListName(view);

        System.out.println("Created list name: " + newList.getName());
    }

    public void showListName(View view){
        String listName = editTextNewList.getText().toString();
        Toast.makeText(this, listName, Toast.LENGTH_LONG).show();
    }

    private void checkBoxListener() {
        checkBoxLists.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxLists.setText(R.string.hideLists);
                Toast.makeText(this, R.string.showingLists, Toast.LENGTH_SHORT).show();
            } else {
                checkBoxLists.setText(R.string.showLists);
                Toast.makeText(this, R.string.hidingLists, Toast.LENGTH_SHORT).show();
            }
        });
    }
}