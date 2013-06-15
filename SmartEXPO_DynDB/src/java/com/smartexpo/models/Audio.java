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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
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
    @Size(max = 1000)
    @Column(name = "DESCRIPTION")
    @Lob
    private String description;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "audioId")
    private ItemAudio itemAudio;

    public Audio() {
    }

    public Audio(Integer audioId) {
        this.audioId = audioId;
    }

    public Audio(Integer audioId, String title, String url) {
        this.audioId = audioId;
        this.title = title;
        this.url = url;
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

    public ItemAudio getItemAudio() {
        return itemAudio;
    }

    public void setItemAudio(ItemAudio itemAudio) {
        this.itemAudio = itemAudio;
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
