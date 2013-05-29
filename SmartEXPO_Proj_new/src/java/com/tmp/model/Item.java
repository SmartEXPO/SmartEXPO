/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tmp.model;

/**
 *
 * @author Boy
 */
public class Item {

    private String name;
    private String picURL;
    private String author_name;
    private String description;
    private String audio_title;
    private String video_title;

    public Item(String name, String picURL, String author_name, String description, String audio_title, String video_title) {
        this.name = name;
        this.picURL = picURL;
        this.author_name = author_name;
        this.description = description;
        this.audio_title = audio_title;
        this.video_title = video_title;
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
     * @return the author_name
     */
    public String getAuthor_name() {
        return author_name;
    }

    /**
     * @param author_name the author_name to set
     */
    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
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
     * @return the audio_title
     */
    public String getAudio_title() {
        return audio_title;
    }

    /**
     * @param audio_title the audio_title to set
     */
    public void setAudio_title(String audio_title) {
        this.audio_title = audio_title;
    }

    /**
     * @return the video_title
     */
    public String getVideo_title() {
        return video_title;
    }

    /**
     * @param video_title the video_title to set
     */
    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }
}
