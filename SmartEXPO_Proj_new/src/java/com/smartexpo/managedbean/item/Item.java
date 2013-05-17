/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class Item {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    UserTransaction utx;
    private GetInfo gi;
    private int id = 3;
    private String name;

    /**
     * Creates a new instance of Item
     */
    public Item() {
    }

    @PostConstruct
    public void postContruct() {
        gi = new GetInfo(em, utx);

        name = gi.getItemByID(id).getItemName();
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
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
}
