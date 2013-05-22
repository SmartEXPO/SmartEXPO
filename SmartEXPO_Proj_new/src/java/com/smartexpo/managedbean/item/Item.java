/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@ViewScoped
public class Item implements Serializable {

    static final Logger logger = Logger.getLogger(Item.class.getName());
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Item fields
    private com.smartexpo.models.Item item;
    private int id;
    private String name;

    /**
     * Creates a new instance of Item
     */
    public Item() {
        logger.log(Level.WARNING, "ItemBean Construct");
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(em, utx);
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        if (request.getAttribute("itemid") == null) {
            request.setAttribute("itemid", "1");
            logger.log(Level.WARNING, "set attribute successfully");
        } else {
            logger.log(Level.WARNING, "attribute exist");
        }

        String tmpID = (String) request.getAttribute("itemid");
        id = Integer.parseInt(tmpID);
        item = gi.getItemByID(id);
        name = item.getItemName();
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
