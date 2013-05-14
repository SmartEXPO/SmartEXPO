/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName")})
public class Item implements Serializable {
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private ItemVideo itemVideo;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
    private ItemAudio itemAudio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId")
    private Collection<ItemAuthor> itemAuthorCollection;
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

    @XmlTransient
    public Collection<ItemAuthor> getItemAuthorCollection() {
        return itemAuthorCollection;
    }

    public void setItemAuthorCollection(Collection<ItemAuthor> itemAuthorCollection) {
        this.itemAuthorCollection = itemAuthorCollection;
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
