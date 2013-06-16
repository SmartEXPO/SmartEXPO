/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.concretemodel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tornado718
 */
public class ItemModel extends AttrGroupModel{
    
    protected ArrayList<CommentModel> comments;
    protected ArrayList<AuthorModel> authors;
    protected ArrayList<AudioModel> audios;
    protected ArrayList<VideoModel> videos;
    protected ArrayList<DescriptionModel> descriptions;
    protected String HTML;
    protected String itemName;
    protected String imageURL;
    protected String area;

    public ItemModel(){
        super();
        this.comments=new ArrayList<CommentModel>();
        this.authors=new ArrayList<AuthorModel>();
        this.audios=new ArrayList<AudioModel>();
        this.videos=new ArrayList<VideoModel>();
        this.descriptions=new ArrayList<DescriptionModel>();
    }
    
    public ArrayList<CommentModel> getComments() {
        return comments;
    }

 

    public ArrayList<AuthorModel> getAuthors() {
        return authors;
    }

    

    public void setComments(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    

    public void setAuthors(ArrayList<AuthorModel> authors) {
        this.authors = authors;
    }

    public ArrayList<AudioModel> getAudios() {
        return audios;
    }

    public void setAudios(ArrayList<AudioModel> audios) {
        this.audios = audios;
    }

    public ArrayList<VideoModel> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<VideoModel> videos) {
        this.videos = videos;
    }

    

    public String getHTML() {
        return HTML;
    }

    public void setHTML(String HTML) {
        this.HTML = HTML;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    
    public void addAuthor(AuthorModel author){
        this.authors.add(author);
    }
    
    public void addVideo(VideoModel video){
        this.videos.add(video);
    }
    
    public void addAudio(AudioModel audio){
        this.audios.add(audio);
    }

    public void addDescription(DescriptionModel dm){
        this.descriptions.add(dm);
    }
    
    public void addComment(CommentModel commentModel){
        this.comments.add(commentModel);
    }
    
    public ArrayList<DescriptionModel> getDescription() {
        return descriptions;
    }

    public void setDescription(ArrayList<DescriptionModel> descriptionModels) {
        this.descriptions = descriptionModels;
    }
    
    
}
