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
import javax.persistence.ManyToOne;
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
@Table(name = "ITEM_DISPLAY_COLUMN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemDisplayColumn.findAll", query = "SELECT i FROM ItemDisplayColumn i"),
    @NamedQuery(name = "ItemDisplayColumn.findByItemDisplayColumnId", query = "SELECT i FROM ItemDisplayColumn i WHERE i.itemDisplayColumnId = :itemDisplayColumnId"),
    @NamedQuery(name = "ItemDisplayColumn.fineByItemId",query = "SELECT i FROM ItemDisplayColumn i WHERE i.itemId =:itemId")
})
public class ItemDisplayColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_DISPLAY_COLUMN_ID")
    private Integer itemDisplayColumnId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "DISPLAY_COLUMN_ID", referencedColumnName = "DISPLAY_COLUMN_ID")
    @OneToOne(optional = false)
    private DisplayColumn displayColumnId;

    public ItemDisplayColumn() {
    }

    public ItemDisplayColumn(Integer itemDisplayColumnId) {
        this.itemDisplayColumnId = itemDisplayColumnId;
    }

    public Integer getItemDisplayColumnId() {
        return itemDisplayColumnId;
    }

    public void setItemDisplayColumnId(Integer itemDisplayColumnId) {
        this.itemDisplayColumnId = itemDisplayColumnId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public DisplayColumn getDisplayColumnId() {
        return displayColumnId;
    }

    public void setDisplayColumnId(DisplayColumn displayColumnId) {
        this.displayColumnId = displayColumnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemDisplayColumnId != null ? itemDisplayColumnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemDisplayColumn)) {
            return false;
        }
        ItemDisplayColumn other = (ItemDisplayColumn) object;
        if ((this.itemDisplayColumnId == null && other.itemDisplayColumnId != null) || (this.itemDisplayColumnId != null && !this.itemDisplayColumnId.equals(other.itemDisplayColumnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ItemDisplayColumn[ itemDisplayColumnId=" + itemDisplayColumnId + " ]";
    }
    
}
