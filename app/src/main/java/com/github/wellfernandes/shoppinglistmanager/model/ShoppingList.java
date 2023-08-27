package com.github.wellfernandes.shoppinglistmanager.model;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Date;
import java.util.Locale;

// Shopping list entity
public class ShoppingList implements Serializable {
    private int id;
    private String name;
    private Date createdAt;
    private String priority;
//    private List<Item> items;

    // items quantity temporary
//    private String ItemsQuantity;

    public ShoppingList(){}

    public ShoppingList(int id, String name, Date createdAt, String priority) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

//    public List<Item> getItems() {
//        return items;
//    }
//
//    public void setItems(List<Item> items) {
//        this.items = items;
//    }
//
//    public String getItemsQuantity() {
//        return ItemsQuantity;
//    }
//
//    public void setItemsQuantity(String itemsQuantity) {
//        ItemsQuantity = itemsQuantity;
//    }

    public String getFormattedCreatedAt() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return dateFormat.format(createdAt);
    }
}