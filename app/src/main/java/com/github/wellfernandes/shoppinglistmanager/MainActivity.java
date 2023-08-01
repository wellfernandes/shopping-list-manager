package com.github.wellfernandes.shoppinglistmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNewList;
    private CheckBox checkBoxLists;
    private RadioGroup radioGroupListFilter;
    private RadioButton radioButtonOpenLists;
    private RadioButton radioButtonCompletedLists;
    private RadioButton radioButtonAllLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNewList = findViewById(R.id.editTextNewList);
        checkBoxLists = findViewById(R.id.checkBoxLists);

        radioGroupListFilter = findViewById(R.id.radioGroupListFilter);

        radioButtonOpenLists = findViewById(R.id.radioButtonOpenLists);
        radioButtonCompletedLists = findViewById(R.id.radioButtonCompletedLists);
        radioButtonAllLists = findViewById(R.id.radioButtonAllLists);

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
                radioGroupListFilter.clearCheck();
                radioGroupListFilter.setVisibility(View.VISIBLE);
                checkBoxLists.setText(R.string.hideLists);
                Toast.makeText(this, R.string.showingLists, Toast.LENGTH_SHORT).show();
                radioGroupListener();
            } else {
                radioGroupListFilter.clearCheck();
                radioGroupListFilter.setVisibility(View.INVISIBLE);
                checkBoxLists.setText(R.string.showLists);
            }
        });
    }

    private void radioGroupListener() {
        radioGroupListFilter.setOnCheckedChangeListener(
                (group, checkedId) -> {
            if (checkedId == R.id.radioButtonOpenLists && checkBoxLists.isChecked()) {
                Toast.makeText(this, "Abertas", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.radioButtonCompletedLists && checkBoxLists.isChecked()) {
                Toast.makeText(this, "Concluídas", Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.radioButtonAllLists && checkBoxLists.isChecked()) {
                Toast.makeText(this, "Todas", Toast.LENGTH_SHORT).show();
            }
        });
    }
}