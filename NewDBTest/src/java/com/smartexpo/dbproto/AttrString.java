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
@Table(name = "ATTR_STRING")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttrString.findAll", query = "SELECT a FROM AttrString a"),
    @NamedQuery(name = "AttrString.findByAttrStringId", query = "SELECT a FROM AttrString a WHERE a.attrStringId = :attrStringId"),
    @NamedQuery(name = "AttrString.findByAttrStringValue", query = "SELECT a FROM AttrString a WHERE a.attrStringValue = :attrStringValue")})
public class AttrString implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_STRING_ID")
    private Integer attrStringId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ATTR_STRING_VALUE")
    private String attrStringValue;
    @JoinColumn(name = "ATTR_STRING_REF", referencedColumnName = "ATTR_ID")
    @OneToOne(optional = false)
    private Attr attrStringRef;

    public AttrString() {
    }

    public AttrString(Integer attrStringId) {
        this.attrStringId = attrStringId;
    }

    public AttrString(Integer attrStringId, String attrStringValue) {
        this.attrStringId = attrStringId;
        this.attrStringValue = attrStringValue;
    }

    public Integer getAttrStringId() {
        return attrStringId;
    }

    public void setAttrStringId(Integer attrStringId) {
        this.attrStringId = attrStringId;
    }

    public String getAttrStringValue() {
        return attrStringValue;
    }

    public void setAttrStringValue(String attrStringValue) {
        this.attrStringValue = attrStringValue;
    }

    public Attr getAttrStringRef() {
        return attrStringRef;
    }

    public void setAttrStringRef(Attr attrStringRef) {
        this.attrStringRef = attrStringRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrStringId != null ? attrStringId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttrString)) {
            return false;
        }
        AttrString other = (AttrString) object;
        if ((this.attrStringId == null && other.attrStringId != null) || (this.attrStringId != null && !this.attrStringId.equals(other.attrStringId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AttrString[ attrStringId=" + attrStringId + " ]";
    }
    
}
