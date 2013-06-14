/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dbproto;

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
@Table(name = "ATTR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attr.findAll", query = "SELECT a FROM Attr a"),
    @NamedQuery(name = "Attr.findByAttrId", query = "SELECT a FROM Attr a WHERE a.attrId = :attrId"),
    @NamedQuery(name = "Attr.findByAttrName", query = "SELECT a FROM Attr a WHERE a.attrName = :attrName"),
    @NamedQuery(name = "Attr.findByAttrType", query = "SELECT a FROM Attr a WHERE a.attrType = :attrType")})
public class Attr implements Serializable {
    @Column(name = "ATTR_TYPE")
    private Integer attrType;
    @OneToMany(mappedBy = "attrStringRef")
    private Collection<AttrString> attrStringCollection;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attrId")
    private AgAttr agAttr;
    @OneToMany(mappedBy = "attrDateRef")
    private Collection<AttrDate> attrDateCollection;
    @OneToMany(mappedBy = "attrTextRef")
    private Collection<AttrText> attrTextCollection;
    @OneToMany(mappedBy = "attrIntRef")
    private Collection<AttrInt> attrIntCollection;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ATTR_ID")
    private Integer attrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ATTR_NAME")
    private String attrName;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attrStringRef")
    private AttrString attrString;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attrDateRef")
    private AttrDate attrDate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attrTextRef")
    private AttrText attrText;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "attrIntRef")
    private AttrInt attrInt;

    public Attr() {
    }

    public Attr(Integer attrId) {
        this.attrId = attrId;
    }

    public Attr(Integer attrId, String attrName, int attrType) {
        this.attrId = attrId;
        this.attrName = attrName;
        this.attrType = attrType;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }

    public int getAttrType() {
        return attrType;
    }

    public void setAttrType(int attrType) {
        this.attrType = attrType;
    }

    public AttrString getAttrString() {
        return attrString;
    }

    public void setAttrString(AttrString attrString) {
        this.attrString = attrString;
    }

    public AttrDate getAttrDate() {
        return attrDate;
    }

    public void setAttrDate(AttrDate attrDate) {
        this.attrDate = attrDate;
    }

    public AttrText getAttrText() {
        return attrText;
    }

    public void setAttrText(AttrText attrText) {
        this.attrText = attrText;
    }

    public AttrInt getAttrInt() {
        return attrInt;
    }

    public void setAttrInt(AttrInt attrInt) {
        this.attrInt = attrInt;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attrId != null ? attrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attr)) {
            return false;
        }
        Attr other = (Attr) object;
        if ((this.attrId == null && other.attrId != null) || (this.attrId != null && !this.attrId.equals(other.attrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Attr[ attrId=" + attrId + " ]";
    }

    

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    @XmlTransient
    public Collection<AttrString> getAttrStringCollection() {
        return attrStringCollection;
    }

    public void setAttrStringCollection(Collection<AttrString> attrStringCollection) {
        this.attrStringCollection = attrStringCollection;
    }

    public AgAttr getAgAttr() {
        return agAttr;
    }

    public void setAgAttr(AgAttr agAttr) {
        this.agAttr = agAttr;
    }

    @XmlTransient
    public Collection<AttrDate> getAttrDateCollection() {
        return attrDateCollection;
    }

    public void setAttrDateCollection(Collection<AttrDate> attrDateCollection) {
        this.attrDateCollection = attrDateCollection;
    }

    @XmlTransient
    public Collection<AttrText> getAttrTextCollection() {
        return attrTextCollection;
    }

    public void setAttrTextCollection(Collection<AttrText> attrTextCollection) {
        this.attrTextCollection = attrTextCollection;
    }

    @XmlTransient
    public Collection<AttrInt> getAttrIntCollection() {
        return attrIntCollection;
    }

    public void setAttrIntCollection(Collection<AttrInt> attrIntCollection) {
        this.attrIntCollection = attrIntCollection;
    }


    
}
