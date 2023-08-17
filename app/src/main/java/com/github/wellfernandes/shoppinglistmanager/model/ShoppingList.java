package com.github.wellfernandes.shoppinglistmanager.model;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

// Shopping list entity
public class ShoppingList {
    private int id;
    private String name;
    private Date createdAt;
    private List<Item> itens;

    public ShoppingList(){}
    public ShoppingList(int id, String name, Date createdAt, List itens) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.itens = new ArrayList<>();
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

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}