package com.github.wellfernandes.shoppinglistmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<ShoppingList> allLists = new ArrayList<>();
    private EditText editTextNewList;
    private CheckBox checkBoxLists;
    private RadioGroup radioGroupListFilter;
    private RadioButton radioButtonOpenLists;
    private RadioButton radioButtonCompletedLists;
    private RadioButton radioButtonAllLists;

    private Spinner spinnerLists;

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

        spinnerLists = findViewById(R.id.spinnerLists);

        checkBoxListener();
    }

    public void addNewList(View view){
        // check all fields
        if (checkFields(view)){
            ShoppingList newList = new ShoppingList();
            newList.setName(String.valueOf(editTextNewList.getText()));

            // add new shopping list
            allLists.add(newList);

            // show the newly added list
            showListName(view);
            fillSpinnerList();
        }

    }

    public boolean checkFields(View view){

        if (editTextNewList.getText().toString().equals("")){
            editTextNewList.requestFocus();
            Toast.makeText(this, R.string.messageInsertListName, Toast.LENGTH_SHORT).show();
            return false;
        }else if (!checkBoxLists.isChecked() && radioGroupListFilter.getCheckedRadioButtonId() == -1){
            checkBoxLists.requestFocus();
            Toast.makeText(this, R.string.messageSelectShowLists, Toast.LENGTH_SHORT).show();
            return false;
        }else if(checkBoxLists.isChecked() && radioGroupListFilter.getCheckedRadioButtonId() == -1){
            checkBoxLists.requestFocus();
            Toast.makeText(this, R.string.messageSelectOption, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void clearText(View view){

        editTextNewList.setText("");
        checkBoxLists.setChecked(false);
        radioGroupListFilter.clearCheck();
        Toast.makeText(this, R.string.messageCleanFields, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, R.string.openListStatus, Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.radioButtonCompletedLists && checkBoxLists.isChecked()) {
                Toast.makeText(this, R.string.completedListstatus, Toast.LENGTH_SHORT).show();
            } else if (checkedId == R.id.radioButtonAllLists && checkBoxLists.isChecked()) {
                Toast.makeText(this, R.string.allListStatus, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void fillSpinnerList(){
        ArrayList<String> listName = new ArrayList<>();

        for (int i = 0; i < allLists.size(); i++) {
            listName.add(allLists.get(i).getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listName);
        spinnerLists.setAdapter(adapter);
    }

    public void showSelectedItem(View view){
        String selectedItem = (String) spinnerLists.getSelectedItem();
        String message;

        if (selectedItem != null){
            message = selectedItem + getString(R.string.messageSelectedItem);
        }else{
            spinnerLists.requestFocus();
            message = getString(R.string.messageNoSelectedList);
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}