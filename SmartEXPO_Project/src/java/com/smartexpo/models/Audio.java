/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "AUDIO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Audio.findAll", query = "SELECT a FROM Audio a"),
    @NamedQuery(name = "Audio.findByAudioId", query = "SELECT a FROM Audio a WHERE a.audioId = :audioId"),
    @NamedQuery(name = "Audio.findByTitle", query = "SELECT a FROM Audio a WHERE a.title = :title"),
    @NamedQuery(name = "Audio.findByUrl", query = "SELECT a FROM Audio a WHERE a.url = :url"),
    @NamedQuery(name = "Audio.findByDescription", query = "SELECT a FROM Audio a WHERE a.description = :description")})
public class Audio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AUDIO_ID")
    private Integer audioId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "URL")
    private String url;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "DESCRIPTION")
    private String description;
    @JoinTable(name = "ITEM_AUDIO", joinColumns = {
        @JoinColumn(name = "AUDIO_ID", referencedColumnName = "AUDIO_ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")})
    @ManyToMany
    private Collection<Item> itemCollection;

    public Audio() {
    }

    public Audio(Integer audioId) {
        this.audioId = audioId;
    }

    public Audio(Integer audioId, String title, String url, String description) {
        this.audioId = audioId;
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public Integer getAudioId() {
        return audioId;
    }

    public void setAudioId(Integer audioId) {
        this.audioId = audioId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public Collection<Item> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<Item> itemCollection) {
        this.itemCollection = itemCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (audioId != null ? audioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Audio)) {
            return false;
        }
        Audio other = (Audio) object;
        if ((this.audioId == null && other.audioId != null) || (this.audioId != null && !this.audioId.equals(other.audioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Audio[ audioId=" + audioId + " ]";
    }
    
}
