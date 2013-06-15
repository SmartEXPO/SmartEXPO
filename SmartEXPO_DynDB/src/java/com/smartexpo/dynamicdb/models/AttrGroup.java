/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.models;

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
@Table(name = "ATTR_GROUP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttrGroup.findAll", query = "SELECT a FROM AttrGroup a"),
    @NamedQuery(name = "AttrGroup.findByAttrGroupId", query = "SELECT a FROM AttrGroup a WHERE a.attrGroupId = :attrGroupId"),
    @NamedQuery(name = "AttrGroup.findByAttrGroupName", query = "SELECT a FROM AttrGroup a WHERE a.attrGroupName = :attrGroupName")})
public class AttrGroup implements Serializable {
    @OneToMany(mappedBy = "itemAttrGroupId")
    private Collection<DBItem> itemCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "agChildId")
    private AgAg agAg_toParent;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_GROUP_ID")
    private Integer attrGroupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ATTR_GROUP_NAME")
    private String attrGroupName;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemAttrGroupId")
    private DBItem item;

    public AttrGroup() {
    }

    public AttrGroup(Integer attrGroupId) {
        this.attrGroupId = attrGroupId;
    }

    public AttrGroup(Integer attrGroupId, String attrGroupName) {
        this.attrGroupId = attrGroupId;
        this.attrGroupName = attrGroupName;
    }

    public Integer getAttrGroupId() {
        return attrGroupId;
    }

    public void setAttrGroupId(Integer attrGroupId) {
        this.attrGroupId = attrGroupId;
    }

    public String getAttrGroupName() {
        return attrGroupName;
    }

    public void setAttrGroupName(String attrGroupName) {
        this.attrGroupName = attrGroupName;
    }

   

    public DBItem getItem() {
        return item;
    }

    public void setItem(DBItem item) {
        this.item = item;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrGroupId != null ? attrGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttrGroup)) {
            return false;
        }
        AttrGroup other = (AttrGroup) object;
        if ((this.attrGroupId == null && other.attrGroupId != null) || (this.attrGroupId != null && !this.attrGroupId.equals(other.attrGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AttrGroup[ attrGroupId=" + attrGroupId + " ]";
    }

    public AgAg getAgAg_toParent() {
        return agAg_toParent;
    }

    public void setAgAg_toParent(AgAg agAg_toParent) {
        this.agAg_toParent = agAg_toParent;
    }

    

    @XmlTransient
    public Collection<DBItem> getItemCollection() {
        return itemCollection;
    }

    public void setItemCollection(Collection<DBItem> itemCollection) {
        this.itemCollection = itemCollection;
    }
    
}
