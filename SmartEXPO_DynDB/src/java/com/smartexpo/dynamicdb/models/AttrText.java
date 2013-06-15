/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "ATTR_TEXT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttrText.findAll", query = "SELECT a FROM AttrText a"),
    @NamedQuery(name = "AttrText.findByAttrTextId", query = "SELECT a FROM AttrText a WHERE a.attrTextId = :attrTextId")})
public class AttrText implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_TEXT_ID")
    private Integer attrTextId;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "ATTR_TEXT_VALUE")
    private String attrTextValue;
    @JoinColumn(name = "ATTR_TEXT_REF", referencedColumnName = "ATTR_ID")
    @OneToOne(optional = false)
    private Attr attrTextRef;

    public AttrText() {
    }

    public AttrText(Integer attrTextId) {
        this.attrTextId = attrTextId;
    }

    public AttrText(Integer attrTextId, String attrTextValue) {
        this.attrTextId = attrTextId;
        this.attrTextValue = attrTextValue;
    }

    public Integer getAttrTextId() {
        return attrTextId;
    }

    public void setAttrTextId(Integer attrTextId) {
        this.attrTextId = attrTextId;
    }

    public String getAttrTextValue() {
        return attrTextValue;
    }

    public void setAttrTextValue(String attrTextValue) {
        this.attrTextValue = attrTextValue;
    }

    public Attr getAttrTextRef() {
        return attrTextRef;
    }

    public void setAttrTextRef(Attr attrTextRef) {
        this.attrTextRef = attrTextRef;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrTextId != null ? attrTextId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttrText)) {
            return false;
        }
        AttrText other = (AttrText) object;
        if ((this.attrTextId == null && other.attrTextId != null) || (this.attrTextId != null && !this.attrTextId.equals(other.attrTextId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AttrText[ attrTextId=" + attrTextId + " ]";
    }
    
}
