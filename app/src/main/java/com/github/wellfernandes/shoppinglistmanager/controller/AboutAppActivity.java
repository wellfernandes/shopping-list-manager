package com.github.wellfernandes.shoppinglistmanager.controller;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.widget.ImageView;

import com.github.wellfernandes.shoppinglistmanager.R;

public class AboutAppActivity extends AppCompatActivity {
    private ImageView imageViewUtfprLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        setTitle(getString(R.string.view_name_about));

        imageViewUtfprLogo = findViewById(R.id.imageViewUtfprLogo);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setUtfprLogo();
    }

    public void setUtfprLogo() {
        int currentTheme = AppCompatDelegate.getDefaultNightMode();

        if (currentTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            imageViewUtfprLogo.setImageResource(R.drawable.utfpr_white);
        } else {
            imageViewUtfprLogo.setImageResource(R.drawable.utfpr);
        }
    }
}