/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.models;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "ATTR_DATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttrDate.findAll", query = "SELECT a FROM AttrDate a"),
    @NamedQuery(name = "AttrDate.findByAttrDateId", query = "SELECT a FROM AttrDate a WHERE a.attrDateId = :attrDateId"),
    @NamedQuery(name = "AttrDate.findByAttrDateValue", query = "SELECT a FROM AttrDate a WHERE a.attrDateValue = :attrDateValue")})
public class AttrDate implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_DATE_ID")
    private Integer attrDateId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ATTR_DATE_VALUE")
    @Temporal(TemporalType.DATE)
    private Date attrDateValue;
    @JoinColumn(name = "ATTR_DATE_REF", referencedColumnName = "ATTR_ID")
    @OneToOne(optional = false)
    private Attr attrDateRef;

    public AttrDate() {
    }

    public AttrDate(Integer attrDateId) {
        this.attrDateId = attrDateId;
    }

    public AttrDate(Integer attrDateId, Date attrDateValue) {
        this.attrDateId = attrDateId;
        this.attrDateValue = attrDateValue;
    }

    public Integer getAttrDateId() {
        return attrDateId;
    }

    public void setAttrDateId(Integer attrDateId) {
        this.attrDateId = attrDateId;
    }

    public Date getAttrDateValue() {
        return attrDateValue;
    }

    public void setAttrDateValue(Date attrDateValue) {
        this.attrDateValue = attrDateValue;
    }

    public Attr getAttrDateRef() {
        return attrDateRef;
    }

    public void setAttrDateRef(Attr attrDateRef) {
        this.attrDateRef = attrDateRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrDateId != null ? attrDateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttrDate)) {
            return false;
        }
        AttrDate other = (AttrDate) object;
        if ((this.attrDateId == null && other.attrDateId != null) || (this.attrDateId != null && !this.attrDateId.equals(other.attrDateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AttrDate[ attrDateId=" + attrDateId + " ]";
    }
    
}
