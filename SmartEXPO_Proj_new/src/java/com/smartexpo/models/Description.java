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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "DESCRIPTION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Description.findAll", query = "SELECT d FROM Description d"),
    @NamedQuery(name = "Description.findByDescriptionId", query = "SELECT d FROM Description d WHERE d.descriptionId = :descriptionId"),
    @NamedQuery(name = "Description.findByTitle", query = "SELECT d FROM Description d WHERE d.title = :title"),
    @NamedQuery(name = "Description.findByContent", query = "SELECT d FROM Description d WHERE d.content = :content"),
    @NamedQuery(name = "Description.findByItemId", query = "SELECT d FROM Description d WHERE d.itemId = :itemId")
})
public class Description implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DESCRIPTION_ID")
    private Integer descriptionId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "TITLE")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "CONTENT")
    private String content;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;

    public Description() {
    }

    public Description(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public Description(Integer descriptionId, String title, String content) {
        this.descriptionId = descriptionId;
        this.title = title;
        this.content = content;
    }

    public Integer getDescriptionId() {
        return descriptionId;
    }

    public void setDescriptionId(Integer descriptionId) {
        this.descriptionId = descriptionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        hash += (descriptionId != null ? descriptionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Description)) {
            return false;
        }
        Description other = (Description) object;
        if ((this.descriptionId == null && other.descriptionId != null) || (this.descriptionId != null && !this.descriptionId.equals(other.descriptionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Description[ descriptionId=" + descriptionId + " ]";
    }
}
