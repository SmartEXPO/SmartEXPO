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
    private int authorID;
    private String authorName;
    private Date authorBirthday;
    private Date authorDeathDate;
    private String authorIntroduction;
    @ManagedProperty(value = "#{audio}")
    private Audio audioBean;
    private int audioID;
    private String audioTitle;
    private String audioURL;
    private String audioDescription;
    @ManagedProperty(value = "#{video}")
    private Video videoBean;
    private int videoID;
    private String videoTitle;
    private String videoURL;
    private String videoDescription;

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
     * @return the authorId
     */
    public int getAuthorId() {
        authorID = authorBean.getId();
        return authorID;
    }

    /**
     * @return the authorName
     */
    public String getAuthorName() {
        authorName = authorBean.getName();
        return authorName;
    }

    /**
     * @return the authorBirthday
     */
    public Date getAuthorBirthday() {
        authorBirthday = authorBean.getBirthday();
        return authorBirthday;
    }

    /**
     * @return the authorDeathDate
     */
    public Date getAuthorDeathDate() {
        authorDeathDate = authorBean.getDeathDate();
        return authorDeathDate;
    }

    /**
     * @return the authorIntroduction
     */
    public String getAuthorIntroduction() {
        authorIntroduction = authorBean.getIntroduction();
        return authorIntroduction;
    }

    /**
     * @param audioBean the audioBean to set
     */
    public void setAudioBean(Audio audioBean) {
        this.audioBean = audioBean;
    }

    /**
     * @return the audioID
     */
    public int getAudioID() {
        audioID = audioBean.getId();
        return audioID;
    }

    /**
     * @return the audioTitle
     */
    public String getAudioTitle() {
        audioTitle = audioBean.getTitle();
        return audioTitle;
    }

    /**
     * @return the audioURL
     */
    public String getAudioURL() {
        audioURL = audioBean.getUrl();
        return audioURL;
    }

    /**
     * @return the audioDescription
     */
    public String getAudioDescription() {
        audioDescription = audioBean.getDescription();
        return audioDescription;
    }

    /**
     * @param videoBean the videoBean to set
     */
    public void setVideoBean(Video videoBean) {
        this.videoBean = videoBean;
    }

    /**
     * @return the videoID
     */
    public int getVideoID() {
        videoID = videoBean.getId();
        return videoID;
    }

    /**
     * @return the videoTitle
     */
    public String getVideoTitle() {
        videoTitle = videoBean.getTitle();
        return videoTitle;
    }

    /**
     * @return the videoURL
     */
    public String getVideoURL() {
        videoURL = videoBean.getURL();
        return videoURL;
    }

    /**
     * @return the videoDescription
     */
    public String getVideoDescription() {
        videoDescription = videoBean.getDescription();
        return videoDescription;
    }
}
