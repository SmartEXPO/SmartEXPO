/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
    @NamedQuery(name = "Sessioninfo.findBySessionid", query = "SELECT s FROM Sessioninfo s WHERE s.sessioninfoPK.sessionid = :sessionid"),
    @NamedQuery(name = "Sessioninfo.findByUsername", query = "SELECT s FROM Sessioninfo s WHERE s.sessioninfoPK.username = :username")})
public class Sessioninfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SessioninfoPK sessioninfoPK;

    public Sessioninfo() {
    }

    public Sessioninfo(SessioninfoPK sessioninfoPK) {
        this.sessioninfoPK = sessioninfoPK;
    }

    public Sessioninfo(String sessionid, String username) {
        this.sessioninfoPK = new SessioninfoPK(sessionid, username);
    }

    public SessioninfoPK getSessioninfoPK() {
        return sessioninfoPK;
    }

    public void setSessioninfoPK(SessioninfoPK sessioninfoPK) {
        this.sessioninfoPK = sessioninfoPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessioninfoPK != null ? sessioninfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sessioninfo)) {
            return false;
        }
        Sessioninfo other = (Sessioninfo) object;
        if ((this.sessioninfoPK == null && other.sessioninfoPK != null) || (this.sessioninfoPK != null && !this.sessioninfoPK.equals(other.sessioninfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Sessioninfo[ sessioninfoPK=" + sessioninfoPK + " ]";
    }
    
}
