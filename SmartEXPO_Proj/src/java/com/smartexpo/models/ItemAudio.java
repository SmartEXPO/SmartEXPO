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
@Table(name = "ITEM_AUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemAudio.findAll", query = "SELECT i FROM ItemAudio i"),
    @NamedQuery(name = "ItemAudio.findByItemAudioId", query = "SELECT i FROM ItemAudio i WHERE i.itemAudioId = :itemAudioId"),
    @NamedQuery(name = "ItemAudio.findByItemId",query = "SELECT i FROM ItemAudio i WHERE i.itemId = :itemId")})
public class ItemAudio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_AUDIO_ID")
    private Integer itemAudioId;
    
    
    @JoinColumn(name = "AUDIO_ID", referencedColumnName = "AUDIO_ID")
    @OneToOne(optional = false)
    private Audio audioId;
    
    
    
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;

    public ItemAudio() {
    }

    public ItemAudio(Integer itemAudioId) {
        this.itemAudioId = itemAudioId;
    }

    public Integer getItemAudioId() {
        return itemAudioId;
    }

    public void setItemAudioId(Integer itemAudioId) {
        this.itemAudioId = itemAudioId;
    }

    public Audio getAudioId() {
        return audioId;
    }

    public void setAudioId(Audio audioId) {
        this.audioId = audioId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemAudioId != null ? itemAudioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemAudio)) {
            return false;
        }
        ItemAudio other = (ItemAudio) object;
        if ((this.itemAudioId == null && other.itemAudioId != null) || (this.itemAudioId != null && !this.itemAudioId.equals(other.itemAudioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ItemAudio[ itemAudioId=" + itemAudioId + " ]";
    }
    
}
