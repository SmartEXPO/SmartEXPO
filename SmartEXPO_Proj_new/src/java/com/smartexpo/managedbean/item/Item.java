/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@ViewScoped
public class Item implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Item fields
    private com.smartexpo.models.Item item;
    private int id;
    private String name;
    private String picURL;
    private String html;
    private boolean hasHtml;

    /**
     * Creates a new instance of Item
     */
    public Item() {
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(emf, utx);
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        String tmpID = (String) request.getParameter("id");
        request.setAttribute("id", tmpID);

        id = Integer.parseInt(tmpID);
        setItem(gi.getItemByID(id));
        name = getItem().getItemName();
        picURL = getItem().getImageurl();
        html = getItem().getHtml();
        if (html == null || html.equals("")) {
            setHasHtml(false);
        } else {
            setHasHtml(true);
        }
    }

    /**
     * @return the item
     */
    public com.smartexpo.models.Item getItem() {
        return item;
    }

    /**
     * @param item the item to set
     */
    public void setItem(com.smartexpo.models.Item item) {
        this.item = item;
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

    /**
     * @return the picURL
     */
    public String getPicURL() {
        return picURL;
    }

    /**
     * @param picURL the picURL to set
     */
    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }

    /**
     * @return the html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html the html to set
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * @return the hasHtml
     */
    public boolean isHasHtml() {
        return hasHtml;
    }

    /**
     * @param hasHtml the hasHtml to set
     */
    public void setHasHtml(boolean hasHtml) {
        this.hasHtml = hasHtml;
    }
}
