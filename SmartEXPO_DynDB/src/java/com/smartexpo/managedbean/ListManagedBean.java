/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Author;
import com.smartexpo.models.Item;
import java.util.ArrayList;
import java.util.List;
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

        initItemInfoList();

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

    private void initItemInfoList() {
        items = gi.getAllItems();
        for (Item item : items) {
            int itemID = item.getItemId();
            String imgURL = item.getImageurl();
            String itemName = item.getItemName();
            String desContent = gi.getDescriptionByItemID(itemID).get(0).getContent();

            List<Author> authors = gi.getAuthorsByItemID(itemID);
            String allAuthors = initAuthors(authors);

            getItemInfos().add(new ItemInfo(itemID, imgURL, itemName, desContent, allAuthors));
        }
    }

    private String initAuthors(List<Author> authors) {
        String result = "";
        for (int i = 0; i < authors.size(); ++i) {
            Author tmpAuthor = authors.get(i);
            if (i != authors.size() - 1) {
                result += tmpAuthor.getName() + " & ";
            } else {
                result += tmpAuthor.getName();
            }
        }
        return result;
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
}
