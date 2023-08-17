package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.Item;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.List;
import java.util.Locale;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<ShoppingList> shoppingLists;

    public ShoppingListAdapter(Context context, List<ShoppingList> shoppingLists) {
        this.context = context;
        this.shoppingLists = shoppingLists;
    }

    @Override
    public int getCount() {
        return shoppingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return shoppingLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ShoppingList shoppingList = shoppingLists.get(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.shopping_lists_line, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.textViewNameValue);
        TextView itemCountTextView = convertView.findViewById(R.id.textViewQntValue);
        TextView totalPriceTextView = convertView.findViewById(R.id.textViewPriceValue);

        nameTextView.setText(shoppingList.getName());

        List<Item> items = shoppingList.getItens();
        int itemCount = 0;
        double totalPrice = 0;

        for (Item item : items) {
            itemCount += item.getQuantity();
            totalPrice += item.getPrice();
        }

        itemCountTextView.setText(String.valueOf(itemCount));
        totalPriceTextView.setText(String.format(Locale.getDefault(), "%.2f", totalPrice));

        return convertView;
    }
}