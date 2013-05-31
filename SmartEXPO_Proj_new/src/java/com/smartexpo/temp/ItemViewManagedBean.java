/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class ItemViewManagedBean implements Serializable {

    private List<Item> items;
    private Item selectedItem;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
        items = new ArrayList<Item>();
        for (int i = 0; i < 88; i++) {
            items.add(new Item("name " + i, "pic " + i, "author name " + i, "199" + i, "200" + i, "author introduction " + i, "description title + i", "description content " + i, "audio title " + i, "video title " + i));
        }
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the selectedItem
     */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }

    public int getCount() {
        return getItems().size();
    }

    public String destroyItem() {
        items.remove(selectedItem);
        return null;
    }
}
