/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dbproto;

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
@Table(name = "ITEM")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Item.findAll", query = "SELECT i FROM Item i"),
    @NamedQuery(name = "Item.findByItemId", query = "SELECT i FROM Item i WHERE i.itemId = :itemId"),
    @NamedQuery(name = "Item.findByItemName", query = "SELECT i FROM Item i WHERE i.itemName = :itemName")})
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_ID")
    private Integer itemId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ITEM_NAME")
    private String itemName;
    @JoinColumn(name = "ITEM_ATTR_GROUP_ID", referencedColumnName = "ATTR_GROUP_ID")
    @OneToOne(optional = false)
    private AttrGroup itemAttrGroupId;

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

    public AttrGroup getItemAttrGroupId() {
        return itemAttrGroupId;
    }

    public void setItemAttrGroupId(AttrGroup itemAttrGroupId) {
        this.itemAttrGroupId = itemAttrGroupId;
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
    
}
