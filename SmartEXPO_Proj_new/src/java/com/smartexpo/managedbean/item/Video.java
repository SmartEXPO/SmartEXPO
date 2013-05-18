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
public class Video {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Video fields
    private List<com.smartexpo.models.Video> videos;
    private List<Integer> ids;
    private List<String> titles;
    private List<String> URLs;
    private List<String> descriptions;

    /**
     * Creates a new instance of Video
     */
    public Video() {
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

        int itemID = Integer.parseInt(request.getParameter("itemid"));
        videos = gi.getVideoByItemID(itemID);

        setAllVideosInfo();
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

    private void setAllVideosInfo() {
        for (com.smartexpo.models.Video video : videos) {
            addVideoID(video);
            addVideoTitle(video);
            addVideoURL(video);
            addVideoDescription(video);
        }
    }

    private void addVideoID(com.smartexpo.models.Video video) {
        ids.add(video.getVideoId());
    }

    private void addVideoURL(com.smartexpo.models.Video video) {
        URLs.add(video.getUrl());
    }

    private void addVideoTitle(com.smartexpo.models.Video video) {
        titles.add(video.getTitle());
    }

    private void addVideoDescription(com.smartexpo.models.Video video) {
        descriptions.add(video.getDescription());
    }
}
