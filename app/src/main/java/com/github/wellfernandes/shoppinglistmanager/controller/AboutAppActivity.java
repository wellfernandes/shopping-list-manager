package com.github.wellfernandes.shoppinglistmanager.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.wellfernandes.shoppinglistmanager.R;

public class AboutAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        setTitle("Sobre o Aplicativo");
    }
}