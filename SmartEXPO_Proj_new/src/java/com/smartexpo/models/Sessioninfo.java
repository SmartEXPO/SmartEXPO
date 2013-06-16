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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "SESSIONINFO")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sessioninfo.findAll", query = "SELECT s FROM Sessioninfo s"),
    @NamedQuery(name = "Sessioninfo.findByUsername", query = "SELECT s FROM Sessioninfo s WHERE s.username = :username"),
    @NamedQuery(name = "Sessioninfo.findBySessionid", query = "SELECT s FROM Sessioninfo s WHERE s.sessionid = :sessionid"),
    @NamedQuery(name = "Sessioninfo.findBySessioninfoId", query = "SELECT s FROM Sessioninfo s WHERE s.sessioninfoId = :sessioninfoId")})
public class Sessioninfo implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SESSIONID")
    private String sessionid;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SESSIONINFO_ID")
    private Integer sessioninfoId;

    public Sessioninfo() {
    }

    public Sessioninfo(Integer sessioninfoId) {
        this.sessioninfoId = sessioninfoId;
    }

    public Sessioninfo(Integer sessioninfoId, String username, String sessionid) {
        this.sessioninfoId = sessioninfoId;
        this.username = username;
        this.sessionid = sessionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSessioninfoId() {
        return sessioninfoId;
    }

    public void setSessioninfoId(Integer sessioninfoId) {
        this.sessioninfoId = sessioninfoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessioninfoId != null ? sessioninfoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sessioninfo)) {
            return false;
        }
        Sessioninfo other = (Sessioninfo) object;
        if ((this.sessioninfoId == null && other.sessioninfoId != null) || (this.sessioninfoId != null && !this.sessioninfoId.equals(other.sessioninfoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Sessioninfo[ sessioninfoId=" + sessioninfoId + " ]";
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }
    
}
