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
@Table(name = "AG_ATTR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgAttr.findAll", query = "SELECT a FROM AgAttr a"),
    @NamedQuery(name = "AgAttr.findByAgAttrId", query = "SELECT a FROM AgAttr a WHERE a.agAttrId = :agAttrId"),
    @NamedQuery(name = "AgAttr.findByAgId", query = "SELECT a FROM AgAttr a WHERE a.agId = :agId")})
public class AgAttr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AG_ATTR_ID")
    private Integer agAttrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AG_ID")
    private int agId;
    @JoinColumn(name = "ATTR_ID", referencedColumnName = "ATTR_ID")
    @OneToOne(optional = false)
    private Attr attrId;

    public AgAttr() {
        
    }

    public AgAttr(Integer agAttrId) {
        this.agAttrId = agAttrId;
    }

    public AgAttr(Integer agAttrId, int agId) {
        this.agAttrId = agAttrId;
        this.agId = agId;
    }

    public Integer getAgAttrId() {
        return agAttrId;
    }

    public void setAgAttrId(Integer agAttrId) {
        this.agAttrId = agAttrId;
    }

    public int getAgId() {
        return agId;
    }

    public void setAgId(int agId) {
        this.agId = agId;
    }

    public Attr getAttrId() {
        return attrId;
    }

    public void setAttrId(Attr attrId) {
        this.attrId = attrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agAttrId != null ? agAttrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgAttr)) {
            return false;
        }
        AgAttr other = (AgAttr) object;
        if ((this.agAttrId == null && other.agAttrId != null) || (this.agAttrId != null && !this.agAttrId.equals(other.agAttrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AgAttr[ agAttrId=" + agAttrId + " ]";
    }
    
}
