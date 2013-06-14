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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "ATTR_INT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttrInt.findAll", query = "SELECT a FROM AttrInt a"),
    @NamedQuery(name = "AttrInt.findByAttrIntId", query = "SELECT a FROM AttrInt a WHERE a.attrIntId = :attrIntId"),
    @NamedQuery(name = "AttrInt.findByAttrIntValue", query = "SELECT a FROM AttrInt a WHERE a.attrIntValue = :attrIntValue")})
public class AttrInt implements Serializable {
    @Column(name = "ATTR_INT_VALUE")
    private Integer attrIntValue;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_INT_ID")
    private Integer attrIntId;
    @JoinColumn(name = "ATTR_INT_REF", referencedColumnName = "ATTR_ID")
    @OneToOne(optional = false)
    private Attr attrIntRef;

    public AttrInt() {
    }

    public AttrInt(Integer attrIntId) {
        this.attrIntId = attrIntId;
    }

    public AttrInt(Integer attrIntId, int attrIntValue) {
        this.attrIntId = attrIntId;
        this.attrIntValue = attrIntValue;
    }

    public Integer getAttrIntId() {
        return attrIntId;
    }

    public void setAttrIntId(Integer attrIntId) {
        this.attrIntId = attrIntId;
    }

    public int getAttrIntValue() {
        return attrIntValue;
    }

    public void setAttrIntValue(int attrIntValue) {
        this.attrIntValue = attrIntValue;
    }

    public Attr getAttrIntRef() {
        return attrIntRef;
    }

    public void setAttrIntRef(Attr attrIntRef) {
        this.attrIntRef = attrIntRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrIntId != null ? attrIntId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttrInt)) {
            return false;
        }
        AttrInt other = (AttrInt) object;
        if ((this.attrIntId == null && other.attrIntId != null) || (this.attrIntId != null && !this.attrIntId.equals(other.attrIntId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AttrInt[ attrIntId=" + attrIntId + " ]";
    }


    
}
