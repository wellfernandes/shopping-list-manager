package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingList> listShoppingList;
    private NumberFormat numberFormat;

    public ShoppingListAdapter(Context context, List<ShoppingList> listShoppingList){

        this.context = context;
        this.listShoppingList = listShoppingList;
        numberFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    }

    public static class ShoppingListHolder{
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // view = inflater.inflate(R.layout.list_shopping_lists, viewGroup, false);



        return null;
    }
}
