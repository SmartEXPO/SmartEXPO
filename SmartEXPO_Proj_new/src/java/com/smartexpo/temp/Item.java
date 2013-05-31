/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.io.Serializable;

/**
 *
 * @author Boy
 */
public class Item implements Serializable {

    private String id;
    private String name;
    private String picURL;
    private String authorName;
    private String authorBirthday;
    private String authorDeathDate;
    private String authorIntro;
    private String desTitle;
    private String desContent;
    private String audioTitle;
    private String videoTitle;
    private static int num = 0;

    public Item(String name, String picURL, String authorName, String authorBirthday, String authorDeathDate, String authorIntro, String desTitle, String desContent, String audioTitle, String videoTitle) {
        id = "NO." + num++;
        this.name = name;
        this.picURL = picURL;
        this.authorName = authorName;
        this.authorBirthday = authorBirthday;
        this.authorDeathDate = authorDeathDate;
        this.authorIntro = authorIntro;
        this.desTitle = desTitle;
        this.desContent = desContent;
        this.audioTitle = audioTitle;
        this.videoTitle = videoTitle;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
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
     * @return the authorName
     */
    public String getAuthorName() {
        return authorName;
    }

    /**
     * @param authorName the authorName to set
     */
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    /**
     * @return the authorBirthday
     */
    public String getAuthorBirthday() {
        return authorBirthday;
    }

    /**
     * @param authorBirthday the authorBirthday to set
     */
    public void setAuthorBirthday(String authorBirthday) {
        this.authorBirthday = authorBirthday;
    }

    /**
     * @return the authorDeathDate
     */
    public String getAuthorDeathDate() {
        return authorDeathDate;
    }

    /**
     * @param authorDeathDate the authorDeathDate to set
     */
    public void setAuthorDeathDate(String authorDeathDate) {
        this.authorDeathDate = authorDeathDate;
    }

    /**
     * @return the authorIntro
     */
    public String getAuthorIntro() {
        return authorIntro;
    }

    /**
     * @param authorIntro the authorIntro to set
     */
    public void setAuthorIntro(String authorIntro) {
        this.authorIntro = authorIntro;
    }

    /**
     * @return the desTitle
     */
    public String getDesTitle() {
        return desTitle;
    }

    /**
     * @param desTitle the desTitle to set
     */
    public void setDesTitle(String desTitle) {
        this.desTitle = desTitle;
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
     * @return the audioTitle
     */
    public String getAudioTitle() {
        return audioTitle;
    }

    /**
     * @param audioTitle the audioTitle to set
     */
    public void setAudioTitle(String audioTitle) {
        this.audioTitle = audioTitle;
    }

    /**
     * @return the videoTitle
     */
    public String getVideoTitle() {
        return videoTitle;
    }

    /**
     * @param videoTitle the videoTitle to set
     */
    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }
}