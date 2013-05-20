/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "ITEM_VIDEO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemVideo.findAll", query = "SELECT i FROM ItemVideo i"),
    @NamedQuery(name = "ItemVideo.findByItemVideoId", query = "SELECT i FROM ItemVideo i WHERE i.itemVideoId = :itemVideoId"),
    @NamedQuery(name = "ItemVideo.findByItemId", query = "SELECT i FROM ItemVideo i WHERE i.itemId = :itemId")})
public class ItemVideo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_VIDEO_ID")
    private Integer itemVideoId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "VIDEO_ID", referencedColumnName = "VIDEO_ID")
    @OneToOne(optional = false)
    private Video videoId;

    public ItemVideo() {
    }

    public ItemVideo(Integer itemVideoId) {
        this.itemVideoId = itemVideoId;
    }

    public Integer getItemVideoId() {
        return itemVideoId;
    }

    public void setItemVideoId(Integer itemVideoId) {
        this.itemVideoId = itemVideoId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Video getVideoId() {
        return videoId;
    }

    public void setVideoId(Video videoId) {
        this.videoId = videoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemVideoId != null ? itemVideoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemVideo)) {
            return false;
        }
        ItemVideo other = (ItemVideo) object;
        if ((this.itemVideoId == null && other.itemVideoId != null) || (this.itemVideoId != null && !this.itemVideoId.equals(other.itemVideoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ItemVideo[ itemVideoId=" + itemVideoId + " ]";
    }
}
