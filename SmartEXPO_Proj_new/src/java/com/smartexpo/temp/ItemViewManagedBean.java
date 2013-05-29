/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

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
        for (int i = 0; i < 20; i++) {
            items.add(new Item("name " + i, "pic " + i, "author name " + i, "199" + i, "200" + i, "author introduction " + i, "description title + i", "description content " + i, "audio title " + i, "video title " + i));
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }
}
