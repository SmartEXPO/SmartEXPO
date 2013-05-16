/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
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
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;



/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class ItemManagedBean {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    UserTransaction utx;

    /**
     * Creates a new instance of ItemManagedBean
     */
    public ItemManagedBean() {
        authors = new ArrayList<Author>();
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(em, utx);
        Logger logger = Logger.getLogger(ItemManagedBean.class.getName());

        itemId = 3;
//        logger.log(Level.WARNING, "Proceeding...");

//        FacesContext context = FacesContext.getCurrentInstance();
//        ExternalContext ec = context.getExternalContext();
//        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
//        itemId = Integer.parseInt(request.getParameter("id"));

//        logger.log(Level.WARNING, "itemID: {0}", itemId);

        item = gi.getItemByID(itemId);
        authors = gi.getAuthorsByItemID(itemId);
        if (item == null) {
//            logger.log(Level.WARNING, "item: {0}", item);
        }
        itemName = item.getItemName();
        authorNames = "";
        for (Author author : authors) {
            authorNames += author.getName() + " ";
        }
        videoURL = gi.getVideoByItemID(itemId).get(0).getUrl();
        if (itemId == 3) {
            pic = "images/item_item.png";
        } else if (itemId == 4) {
            pic = "images/item2_480.png";
        }
        
        setDescription(gi.getDescriptionByItemID(itemId).get(0).getContent());
//        logger.log(Level.WARNING, "des : {0}", videoURL);

//        logger.log(Level.WARNING, "ItemID:{0}  ItemName:{1}", new Object[]{itemId, itemName});
    }
    private GetInfo gi;
    private int itemId;
    private String itemName;
    private String authorNames;
    private String description;
    private String videoURL;
    private Item item;
    private List<Author> authors;
    private List<Audio> audios;
    private String pic;

    /**
     * @return the itemId
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
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
     * @return the authorNames
     */
    public String getAuthorNames() {
        return authorNames;
    }

    /**
     * @param authorNames the authorNames to set
     */
    public void setAuthorNames(String authorNames) {
        this.authorNames = authorNames;
    }

    /**
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(Item item) {
        this.item = item;
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

    /**
     * @return the videoURL
     */
    public String getVideoURL() {
        return videoURL;
    }

    /**
     * @param videoURL the videoURL to set
     */
    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    /**
     * @return the pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic the pic to set
     */
    public void setPic(String pic) {
        this.pic = pic;
    }
}
