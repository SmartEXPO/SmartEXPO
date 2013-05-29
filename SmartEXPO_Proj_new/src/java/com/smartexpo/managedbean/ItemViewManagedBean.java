/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.tmp.model.Item;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class ItemViewManagedBean {

    private List<Item> items;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
        items = new ArrayList<Item>();
        for (int i = 0; i < 9; i++) {
            Item item = new Item("item " + i, "pic Url " + i, "author " + i, "des " + i, "audio " + i, "video " + i);
            items.add(item);
        }
    }

    public List<Item> getItems() {
        return items;
    }
}
