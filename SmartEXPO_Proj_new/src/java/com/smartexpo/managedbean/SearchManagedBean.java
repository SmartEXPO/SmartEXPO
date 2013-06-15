/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class SearchManagedBean {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // SearchManagedBean Fileds
    private String searchKey;
    private List<Item> searchedList;

    /**
     * Creates a new instance of SearchManagedBean
     */
    public SearchManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    /**
     * @return the searchKey
     */
    public String getSearchKey() {
        return searchKey;
    }

    /**
     * @param searchKey the searchKey to set
     */
    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    /**
     * @return the searchedList
     */
    public List<Item> getSearchedList() {
        return searchedList;
    }

    /**
     * @param searchedList the searchedList to set
     */
    public void setSearchedList(List<Item> searchedList) {
        this.searchedList = searchedList;
    }

    public void searchListener(AjaxBehaviorEvent event) {
        searchedList = gi.getItemsByItemName(searchKey);
        if (searchedList == null || searchedList.isEmpty()) {
            searchedList = gi.getItemsByItemNameSubStr(searchKey);
        } else {
            searchedList.addAll(gi.getItemsByItemNameSubStr(searchKey));
        }
    }
}
