/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class ItemViewManagedBean implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(ItemViewManagedBean.class.getName());
    private List<Item> items;
    private Item selectedItem;
    // 不知道为什么直接使用selectedItem时会出问题，只好先加一些冗余的数据。
    private String id;
    private String name = "Name";
    private String picURL;
    private String authorName = "Author";
    private String authorBirthday;
    private String authorDeathDate;
    private String authorIntro;
    private String desTitle;
    private String desContent;
    private String audioTitle;
    private String videoTitle;
    private int num;

    /**
     * Creates a new instance of ItemViewManagedBean
     */
    public ItemViewManagedBean() {
        items = new ArrayList<Item>();
        for (int i = 0; i < 88; i++) {
            items.add(new Item("name " + i, "pic " + i, "author name " + i, "199" + i, "200" + i, "author introduction " + i, "description title + i", "description content " + i, "audio title " + i, "video title " + i));
        }
    }

    /**
     * @return the items
     */
    public List<Item> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @return the selectedItem
     */
    public Item getSelectedItem() {
        return selectedItem;
    }

    /**
     * @param selectedItem the selectedItem to set
     */
    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
    
    public int getCount() {
        return getItems().size();
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

    /**
     * @return the num
     */
    public int getNum() {
        return num;
    }

    /**
     * @param num the num to set
     */
    public void setNum(int num) {
        this.num = num;
    }
    
    public void dataToSelectedItem() {
        LOG.log(Level.WARNING, "aaa {0}{1}", new Object[]{id, name});
        // 记录要修改的item位置
        num = items.indexOf(selectedItem);
        // 更新selectedItem
        id = selectedItem.getId();
        name = selectedItem.getName();
        picURL = selectedItem.getPicURL();
        authorName = selectedItem.getAuthorName();
        authorBirthday = selectedItem.getAuthorBirthday();
        authorDeathDate = selectedItem.getAuthorDeathDate();
        authorIntro = selectedItem.getAuthorIntro();
        desTitle = selectedItem.getDesTitle();
        desContent = selectedItem.getDesContent();
        audioTitle = selectedItem.getAudioTitle();
        videoTitle = selectedItem.getVideoTitle();
    }
    
    public void storeEditedData(ActionEvent event) {
        LOG.log(Level.WARNING, "Sajflksjafjpsajfsopafjopsa");
        // 根据记录获得对应item instance
        Item toBeStoredItem = items.get(num);
        toBeStoredItem.setId(selectedItem.getId());
        toBeStoredItem.setName(selectedItem.getName());
        RequestContext.getCurrentInstance()
                    .execute("vanishLogin();void(0);");

        // Store in Database
    }
    
    public void destroyItem() {
        LOG.log(Level.WARNING, "removiiiiiiiiing");
        getItems().remove(getSelectedItem());
    }
}
