package com.smartexpo.controller;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Boy
 * @version 1.0
 */
@ManagedBean
@RequestScoped
public class ItemManagedBean {

    /**
     * Creates a new instance of ItemManagedBean
     */
    public ItemManagedBean() {
        name = "No Title";
        author = "Andy_Warhol";
        description = "this is the description of this item.1"
                + ""
                + "this is the description of this item.2\n"
                + "                            this is the description of this item.3\n"
                + "                            this is the description of this item.4\n"
                + "                            this is the description of this item.5\n"
                + "                            this is the description of this item.6\n"
                + "                            this is the description of this item.7\n"
                + "                            this is the description of this item.8\n"
                + "                            this is the description of this item.9\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.1\n"
                + "                            this is the description of this item.2\n"
                + "                            this is the description of this item.3\n"
                + "                            this is the description of this item.4\n"
                + "                            this is the description of this item.5\n"
                + "                            this is the description of this item.6\n"
                + "                            this is the description of this item.7\n"
                + "                            this is the description of this item.8\n"
                + "                            this is the description of this item.9\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.1\n"
                + "                            this is the description of this item.2\n"
                + "                            this is the description of this item.3\n"
                + "                            this is the description of this item.4\n"
                + "                            this is the description of this item.5\n"
                + "                            this is the description of this item.6\n"
                + "                            this is the description of this item.7\n"
                + "                            this is the description of this item.8\n"
                + "                            this is the description of this item.9\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.1\n"
                + "                            this is the description of this item.2\n"
                + "                            this is the description of this item.3\n"
                + "                            this is the description of this item.4\n"
                + "                            this is the description of this item.5\n"
                + "                            this is the description of this item.6\n"
                + "                            this is the description of this item.7\n"
                + "                            this is the description of this item.8\n"
                + "                            this is the description of this item.9\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.\n"
                + "                            this is the description of this item.";
    }
    private String itemID;
    private String name;
    private String author;
    private String description;

    /**
     * @return the itemID
     */
    public String getItemID() {
        return itemID;
    }

    /**
     * @param itemID the itemID to set
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
