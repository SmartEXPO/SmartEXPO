/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByItemId", query = "SELECT i FROM Item i WHERE i.itemId = :itemId"),
    @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName"),
    @NamedQuery(name = "Item.findByItemArea", query = "SELECT i FROM Item i WHERE i.area = :itemArea")
})
public class Item implements Serializable {

    @OneToOne(mappedBy = "itemId")
    private ItemComment itemComment;

    public ItemComment getItemComment() {
        return itemComment;
    }

    public void setItemComment(ItemComment itemComment) {
        this.itemComment = itemComment;
    }
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private ItemVideo itemVideo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private ItemAudio itemAudio;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private ItemAuthor itemAuthor;
    

    public ItemAuthor getItemAuthor() {
        return itemAuthor;
    }

    public void setItemAuthor(ItemAuthor itemAuthor) {
        this.itemAuthor = itemAuthor;
    }
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Description description;
    
    @Size(min=1, max = 200)
    @Column(name="ITEM_AREA")
    private String area;
    
    @Size(min=1,max =300)
    @Column(name="IMAGE_URL")
    private String imageurl;
    

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
    

    public Item() {
    }

    public Item(Integer itemId) {
        this.itemId = itemId;
    }

    public Item(Integer itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Description getDescription() {
        return description;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemId != null ? itemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Item)) {
            return false;
        }
        Item other = (Item) object;
        if ((this.itemId == null && other.itemId != null) || (this.itemId != null && !this.itemId.equals(other.itemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Item[ itemId=" + itemId + " ]";
    }

    public ItemVideo getItemVideo() {
        return itemVideo;
    }

    public void setItemVideo(ItemVideo itemVideo) {
        this.itemVideo = itemVideo;
    }

    public ItemAudio getItemAudio() {
        return itemAudio;
    }

    public void setItemAudio(ItemAudio itemAudio) {
        this.itemAudio = itemAudio;
    }
}
