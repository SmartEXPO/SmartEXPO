/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class Audio {
    
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Audio fields
    private List<com.smartexpo.models.Audio> audios;
    private List<Integer> ids;
    private List<String> titles;
    private List<String> URLs;
    private List<String> descriptions;

    /**
     * Creates a new instance of Audio
     */
    public Audio() {
        ids = new ArrayList<Integer>();
        titles = new ArrayList<String>();
        URLs = new ArrayList<String>();
        descriptions = new ArrayList<String>();
    }
    
    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(em, utx);
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        
        int itemID = Integer.parseInt(request.getParameter("id"));
        audios = gi.getAudioByItemID(itemID);
        
        setAllAudioInfo();
    }

    /**
     * @return the ids
     */
    public List<Integer> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * @return the titles
     */
    public List<String> getTitles() {
        return titles;
    }

    /**
     * @param titles the titles to set
     */
    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    /**
     * @return the URLs
     */
    public List<String> getURLs() {
        return URLs;
    }

    /**
     * @param URLs the URLs to set
     */
    public void setURLs(List<String> URLs) {
        this.URLs = URLs;
    }

    /**
     * @return the descriptions
     */
    public List<String> getDescriptions() {
        return descriptions;
    }

    /**
     * @param descriptions the descriptions to set
     */
    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }
    
    private void setAllAudioInfo() {
        for (com.smartexpo.models.Audio audio : audios) {
            addAudioID(audio);
            addAudioTitle(audio);
            addAudioURL(audio);
            addAudioDescription(audio);
        }
    }
    
    private void addAudioID(com.smartexpo.models.Audio audio) {
        ids.add(audio.getAudioId());
    }
    
    private void addAudioURL(com.smartexpo.models.Audio audio) {
        URLs.add(audio.getUrl());
    }
    
    private void addAudioTitle(com.smartexpo.models.Audio audio) {
        titles.add(audio.getTitle());
    }
    
    private void addAudioDescription(com.smartexpo.models.Audio audio) {
        descriptions.add(audio.getDescription());
    }
}
