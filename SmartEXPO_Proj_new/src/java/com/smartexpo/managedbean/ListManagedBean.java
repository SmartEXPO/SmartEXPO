/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Author;
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
public class ListManagedBean {

    private static final Logger LOG = Logger.getLogger(ListManagedBean.class.getName());
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    private List<Item> items;
    private List<ItemInfo> itemInfos;

    /**
     * Creates a new instance of ListManagedBean
     */
    public ListManagedBean() {
        itemInfos = new ArrayList<ItemInfo>();
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
        items = gi.getAllItems();
        for (Item item : items) {
            int itemID = item.getItemId();
            String imgURL = item.getImageurl();
            String itemName = item.getItemName();
            LOG.log(Level.WARNING,"itemID: "+itemID);
            
            String desContent = gi.getDescriptionByItemID(itemID).get(0).getContent();
            
            LOG.log(Level.WARNING, "itemid = {0}, description = {1}", new Object[]{itemID, desContent});
            String allAuthors = "";
            List<Author> authors = gi.getAuthorsByItemID(itemID);
            for (int i = 0; i < authors.size(); ++i) {
                Author tmpAuthor = authors.get(i);
                if (i != authors.size() - 1) {
                    allAuthors += tmpAuthor.getName() + " & ";
                } else {
                    allAuthors += tmpAuthor.getName();
                }
//                LOG.log(Level.WARNING, "authornum: {0}, name: {1}", new Object[]{i, tmpAuthor.getName()});
            }
            getItemInfos().add(new ItemInfo(itemID, imgURL, itemName, desContent, allAuthors));
        }
    }

    /**
     * @return the itemInfos
     */
    public List<ItemInfo> getItemInfos() {
        return itemInfos;
    }

    /**
     * @param itemInfos the itemInfos to set
     */
    public void setItemInfos(List<ItemInfo> itemInfos) {
        this.itemInfos = itemInfos;
    }

    public class ItemInfo {

        private int itemID;
        private String imgURL;
        private String itemName;
        private String desContent;
        private String authors;

        public ItemInfo(int itemID, String imgURL, String itemName, String desContent, String authors) {
            this.itemID = itemID;
            this.imgURL = imgURL;
            this.itemName = itemName;
            this.desContent = desContent;
            this.authors = authors;
        }

        /**
         * @return the itemID
         */
        public int getItemID() {
            return itemID;
        }

        /**
         * @param itemID the itemID to set
         */
        public void setItemID(int itemID) {
            this.itemID = itemID;
        }

        /**
         * @return the imgURL
         */
        public String getImgURL() {
            return imgURL;
        }

        /**
         * @param imgURL the imgURL to set
         */
        public void setImgURL(String imgURL) {
            this.imgURL = imgURL;
        }

        /**
         * @return the itemName
         */
        public String getItemName() {
            return itemName;
        }

        /**
         * @param itemName the itemName to set
         */
        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        /**
         * @return the desContent
         */
        public String getDesContent() {
            return desContent;
        }

        /**
         * @param desContent the desContent to set
         */
        public void setDesContent(String desContent) {
            this.desContent = desContent;
        }

        /**
         * @return the authors
         */
        public String getAuthors() {
            return authors;
        }

        /**
         * @param authors the authors to set
         */
        public void setAuthors(String authors) {
            this.authors = authors;
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
}
