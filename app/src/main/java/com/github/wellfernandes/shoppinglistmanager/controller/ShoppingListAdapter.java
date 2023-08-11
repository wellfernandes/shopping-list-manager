package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.Item;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShoppingListAdapter extends BaseAdapter {

    private Context context;
    private List<Item> shoppingListItem;
    private NumberFormat numberFormat;


    public ShoppingListAdapter(Context context, List<Item> shoppingListItem){

        this.context = context;
        this.shoppingListItem = shoppingListItem;
        numberFormat = NumberFormat.getNumberInstance(new Locale("pt", "BR"));
    }

    static class ShoppingListHolder {
        TextView textViewNameValue;
        TextView textViewQntValue;

        TextView textViewPriceValue;
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

        Item item  = shoppingListItem.get(position);

        if (convertView == null) {

            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.shopping_lists_line, parent, false);

            ShoppingListHolder holder = new ShoppingListHolder();
            holder.textViewNameValue = convertView.findViewById(R.id.textViewNameValue);
            holder.textViewQntValue = convertView.findViewById(R.id.textViewQntValue);
            holder.textViewPriceValue = convertView.findViewById(R.id.textViewPriceValue);

            convertView.setTag(holder);
        }

        ShoppingListHolder holder = (ShoppingListHolder) convertView.getTag();
        holder.textViewNameValue.setText(item.getName());
        holder.textViewQntValue.setText(item.getName());
        holder.textViewPriceValue.setText(item.getName());

        return convertView;
    }
}
