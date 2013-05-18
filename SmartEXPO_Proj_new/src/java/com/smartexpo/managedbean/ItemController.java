/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.managedbean.item.Audio;
import com.smartexpo.managedbean.item.Author;
import com.smartexpo.managedbean.item.Description;
import com.smartexpo.managedbean.item.Item;
import com.smartexpo.managedbean.item.Video;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class ItemController {

    @ManagedProperty(value = "#{item}")
    private Item itemBean;
    private int itemID;
    private String itemName;
    @ManagedProperty(value = "#{description}")
    private Description descriptionBean;
    private int descriptionID;
    private String descriptionTitle;
    private String descriptionContent;
    @ManagedProperty(value = "#{author}")
    private Author authorBean;
    private List<Integer> authorIDs;
    private List<String> authorNames;
    private List<Date> authorBirthdays;
    private List<Date> authorDeathDates;
    private List<String> authorIntroductions;
    @ManagedProperty(value = "#{audio}")
    private Audio audioBean;
    private List<Integer> audioIDs;
    private List<String> audioTitles;
    private List<String> audioURLs;
    private List<String> audioDescriptions;
    @ManagedProperty(value = "#{video}")
    private Video videoBean;
    private List<Integer> videoIDs;
    private List<String> videoTitles;
    private List<String> videoURLs;
    private List<String> videoDescriptions;

    /**
     * Creates a new instance of ItemController
     */
    public ItemController() {
    }

    /**
     * @param itemBean the itemBean to set
     */
    public void setItemBean(Item itemBean) {
        this.itemBean = itemBean;
    }

    /**
     * @return the itemID
     */
    public int getItemID() {
        itemID = itemBean.getId();
        return itemID;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        itemName = itemBean.getName();
        return itemName;
    }

    /**
     * @param descriptionBean the descriptionBean to set
     */
    public void setDescriptionBean(Description descriptionBean) {
        this.descriptionBean = descriptionBean;
    }

    /**
     * @return the descriptionID
     */
    public int getDescriptionID() {
        descriptionID = descriptionBean.getId();
        return descriptionID;
    }

    /**
     * @return the descriptionTitle
     */
    public String getDescriptionTitle() {
        descriptionTitle = descriptionBean.getTitle();
        return descriptionTitle;
    }

    /**
     * @return the descriptionContent
     */
    public String getDescriptionContent() {
        descriptionContent = descriptionBean.getContent();
        return descriptionContent;
    }

    /**
     * @param authorBean the authorBean to set
     */
    public void setAuthorBean(Author authorBean) {
        this.authorBean = authorBean;
    }

    /**
     * @return the authorIDs
     */
    public List<Integer> getAuthorIDs() {
        authorIDs = authorBean.getIds();
        return authorIDs;
    }

    /**
     * @return the authorNames
     */
    public List<String> getAuthorNames() {
        authorNames = authorBean.getNames();
        return authorNames;
    }

    /**
     * @return the authorBirthdays
     */
    public List<Date> getAuthorBirthdays() {
        authorBirthdays = authorBean.getBirthdays();
        return authorBirthdays;
    }

    /**
     * @return the authorDeathDates
     */
    public List<Date> getAuthorDeathDates() {
        authorDeathDates = authorBean.getDeathDates();
        return authorDeathDates;
    }

    /**
     * @return the authorIntroductions
     */
    public List<String> getAuthorIntroductions() {
        authorIntroductions = authorBean.getIntroductions();
        return authorIntroductions;
    }

    /**
     * @param audioBean the audioBean to set
     */
    public void setAudioBean(Audio audioBean) {
        this.audioBean = audioBean;
    }

    /**
     * @return the audioIDs
     */
    public List<Integer> getAudioIDs() {
        audioIDs = audioBean.getIds();
        return audioIDs;
    }

    /**
     * @return the audioTitles
     */
    public List<String> getAudioTitles() {
        audioTitles = audioBean.getTitles();
        return audioTitles;
    }

    /**
     * @return the audioURLs
     */
    public List<String> getAudioURLs() {
        audioURLs = audioBean.getURLs();
        return audioURLs;
    }

    /**
     * @return the audioDescriptions
     */
    public List<String> getAudioDescriptions() {
        audioDescriptions = audioBean.getDescriptions();
        return audioDescriptions;
    }

    /**
     * @param videoBean the videoBean to set
     */
    public void setVideoBean(Video videoBean) {
        this.videoBean = videoBean;
    }

    /**
     * @return the videoIDs
     */
    public List<Integer> getVideoIDs() {
        videoIDs = videoBean.getIds();
        return videoIDs;
    }

    /**
     * @return the videoTitles
     */
    public List<String> getVideoTitles() {
        videoTitles = videoBean.getTitles();
        return videoTitles;
    }

    /**
     * @return the videoURLs
     */
    public List<String> getVideoURLs() {
        videoURLs = videoBean.getURLs();
        return videoURLs;
    }

    /**
     * @return the videoDescriptions
     */
    public List<String> getVideoDescriptions() {
        videoDescriptions = videoBean.getDescriptions();
        return videoDescriptions;
    }
}