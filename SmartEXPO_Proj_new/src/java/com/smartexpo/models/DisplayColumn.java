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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "DISPLAY_COLUMN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DisplayColumn.findAll", query = "SELECT d FROM DisplayColumn d"),
    @NamedQuery(name = "DisplayColumn.findByDisplayColumnId", query = "SELECT d FROM DisplayColumn d WHERE d.displayColumnId = :displayColumnId"),
    @NamedQuery(name = "DisplayColumn.findByDisplayContent", query = "SELECT d FROM DisplayColumn d WHERE d.displayContent = :displayContent")})
public class DisplayColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "DISPLAY_COLUMN_ID")
    private Integer displayColumnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3000)
    @Column(name = "DISPLAY_CONTENT")
    private String displayContent;
    @OneToOne(cascade = CascadeType.ALL,mappedBy = "displayColumnId")
    private ItemDisplayColumn itemDisplayColumn;

    public DisplayColumn() {
    }

    public DisplayColumn(Integer displayColumnId) {
        this.displayColumnId = displayColumnId;
    }

    public DisplayColumn(Integer displayColumnId, String displayContent) {
        this.displayColumnId = displayColumnId;
        this.displayContent = displayContent;
    }

    public Integer getDisplayColumnId() {
        return displayColumnId;
    }

    public void setDisplayColumnId(Integer displayColumnId) {
        this.displayColumnId = displayColumnId;
    }

    public String getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    public ItemDisplayColumn getItemDisplayColumn() {
        return itemDisplayColumn;
    }

    public void setItemDisplayColumn(ItemDisplayColumn itemDisplayColumn) {
        this.itemDisplayColumn = itemDisplayColumn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (displayColumnId != null ? displayColumnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisplayColumn)) {
            return false;
        }
        DisplayColumn other = (DisplayColumn) object;
        if ((this.displayColumnId == null && other.displayColumnId != null) || (this.displayColumnId != null && !this.displayColumnId.equals(other.displayColumnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.DisplayColumn[ displayColumnId=" + displayColumnId + " ]";
    }
    
}
