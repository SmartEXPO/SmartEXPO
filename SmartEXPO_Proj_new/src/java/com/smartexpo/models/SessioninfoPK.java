/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author tornado718
 */
@Embeddable
public class SessioninfoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SESSIONID")
    private String sessionid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username;

    public SessioninfoPK() {
    }

    public SessioninfoPK(String sessionid, String username) {
        this.sessionid = sessionid;
        this.username = username;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sessionid != null ? sessionid.hashCode() : 0);
        hash += (username != null ? username.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SessioninfoPK)) {
            return false;
        }
        SessioninfoPK other = (SessioninfoPK) object;
        if ((this.sessionid == null && other.sessionid != null) || (this.sessionid != null && !this.sessionid.equals(other.sessionid))) {
            return false;
        }
        if ((this.username == null && other.username != null) || (this.username != null && !this.username.equals(other.username))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.SessioninfoPK[ sessionid=" + sessionid + ", username=" + username + " ]";
    }
    
}
