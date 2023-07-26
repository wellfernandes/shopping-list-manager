package com.github.wellfernandes.shoppinglistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNewList = findViewById(R.id.editTextNewList);
    }

    public void addNewList(View view){
        ShoppingList newList = new ShoppingList();

        newList.setName(String.valueOf(editTextNewList.getText()));

        System.out.println("Created list name:: " + newList.getName());
    }
}