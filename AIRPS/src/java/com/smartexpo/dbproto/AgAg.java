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
@Table(name = "AG_AG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AgAg.findAll", query = "SELECT a FROM AgAg a"),
    @NamedQuery(name = "AgAg.findByAgAgId", query = "SELECT a FROM AgAg a WHERE a.agAgId = :agAgId"),
    @NamedQuery(name = "AgAg.findByAgParentId", query = "SELECT a FROM AgAg a WHERE a.agParentId = :agParentId"),
    @NamedQuery(name = "AgAg.findByAgChildId", query = " SELECT a FROM AgAg a WHERE a.agChildId = :agChildId")
})
public class AgAg implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "AG_AG_ID")
    private Integer agAgId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AG_PARENT_ID")
    private int agParentId;
    @JoinColumn(name = "AG_CHILD_ID", referencedColumnName = "ATTR_GROUP_ID")
    @OneToOne(optional = false)
    private AttrGroup agChildId;

    public AgAg() {
    }

    public AgAg(Integer agAgId) {
        this.agAgId = agAgId;
    }

    public AgAg(Integer agAgId, int agParentId) {
        this.agAgId = agAgId;
        this.agParentId = agParentId;
    }

    public Integer getAgAgId() {
        return agAgId;
    }

    public void setAgAgId(Integer agAgId) {
        this.agAgId = agAgId;
    }

    public int getAgParentId() {
        return agParentId;
    }

    public void setAgParentId(int agParentId) {
        this.agParentId = agParentId;
    }

    public AttrGroup getAgChildId() {
        return agChildId;
    }

    public void setAgChildId(AttrGroup agChildId) {
        this.agChildId = agChildId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agAgId != null ? agAgId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AgAg)) {
            return false;
        }
        AgAg other = (AgAg) object;
        if ((this.agAgId == null && other.agAgId != null) || (this.agAgId != null && !this.agAgId.equals(other.agAgId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.AgAg[ agAgId=" + agAgId + " ]";
    }
    
}
