/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.Video;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author tornado718
 */
@ManagedBean
@RequestScoped
public class ItemInsertMB {
    
    
    private String itemName;
    private String desTitle;
    private String desContent;
    private List<Author> authors;
    private String authorName;
    private Date authorBirth;
    private Date authorDeath;
    private String authorIntro;
    
    private List<Audio> audios;
    
    private List<Video> videos;
    

    /**
     * Creates a new instance of ItemInsertMB
     */
    public ItemInsertMB() {
    }
}
