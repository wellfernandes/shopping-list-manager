package com.github.wellfernandes.shoppinglistmanager.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.github.wellfernandes.shoppinglistmanager.R;
import com.github.wellfernandes.shoppinglistmanager.model.ShoppingList;

import java.util.List;

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

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ShoppingList shoppingList = shoppingLists.get(position);

        ShoppingListHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.shopping_lists_line, viewGroup, false);

            holder = new ShoppingListHolder();
            holder.textViewIdValue = view.findViewById(R.id.textViewId);
            holder.textViewNameValue = view.findViewById(R.id.textViewName);
            holder.textViewDateValue = view.findViewById(R.id.textViewDate);
            holder.textViewPriorityValue = view.findViewById(R.id.textViewPriority);


            view.setTag(holder);
        } else {
            holder = (ShoppingListHolder) view.getTag();
        }

        holder.textViewIdValue.setText("ID: " + shoppingList.getId());
        holder.textViewNameValue.setText("Nome: " + shoppingList.getName());
        holder.textViewDateValue.setText("Data: " + shoppingList.getFormattedCreatedAt());
        holder.textViewPriorityValue.setText("Prioridade: "+ shoppingList.getPriority());

        return view;
    }

    static class ShoppingListHolder {
        TextView textViewIdValue;
        TextView textViewNameValue;
        TextView textViewDateValue;
        TextView textViewQntValue;
        TextView textViewPriorityValue;
    }
}