/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Description;
import com.smartexpo.models.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
public class IndexManagedBean {

    private static final Logger LOG = Logger.getLogger(IndexManagedBean.class.getName());
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // IndexManagedBean Fields
    private List<Item> showItems;
    private List<Item> recommendItems;
    private List<Description> showItemsDescriptions;

    /**
     * Creates a new instance of IndexManagedBean
     */
    public IndexManagedBean() {
        showItems = new ArrayList<Item>();
        recommendItems = new ArrayList<Item>();
        showItemsDescriptions = new ArrayList<Description>();
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);

        List<Item> allItems = gi.getAllItems();

        // 主页用于切换的Items
        for (int i = 0; i < 3; i++) {
            int randomNum = (int) (Math.random() * allItems.size());
            LOG.log(Level.WARNING, "Item Number = {0}", randomNum);
            Item tmpItem = allItems.get(randomNum);
            int itemID = tmpItem.getItemId();
            Description des = gi.getDescriptionByItemID(itemID).get(0);

            getShowItems().add(tmpItem);
            getShowItemsDescriptions().add(des);
        }

        // Recommended Items
        for (int i = 0; i < 4; i++) {
            int randomNum = (int) (Math.random() * allItems.size());
            LOG.log(Level.WARNING, "Recommend Item Number = {0}", randomNum);
            Item tmpItem = allItems.get(randomNum);
            int itemID = tmpItem.getItemId();

            getRecommendItems().add(tmpItem);
        }
    }

    /**
     * @return the showItems
     */
    public List<Item> getShowItems() {
        return showItems;
    }

    /**
     * @return the recommendItems
     */
    public List<Item> getRecommendItems() {
        return recommendItems;
    }

    /**
     * @return the showItemsDescriptions
     */
    public List<Description> getShowItemsDescriptions() {
        return showItemsDescriptions;
    }
}
