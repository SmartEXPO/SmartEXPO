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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "MANAGER_PERMISSION")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ManagerPermission.findAll", query = "SELECT m FROM ManagerPermission m"),
    @NamedQuery(name = "ManagerPermission.findByManagerPermissionId", query = "SELECT m FROM ManagerPermission m WHERE m.managerPermissionId = :managerPermissionId"),
    @NamedQuery(name = "ManagerPermission.findByManagerId", query = "SELECT m FROM ManagerPermission m WHERE m.managerId = :managerId")
})
public class ManagerPermission implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MANAGER_PERMISSION_ID")
    private Integer managerPermissionId;
    
    
    
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "MANAGER_ID")
    @OneToOne(optional = false)
    private Manager managerId;
    
    
    @JoinColumn(name = "PERMISSION_ID", referencedColumnName = "PERMISSION_ID")
    @OneToOne(optional = false)
    private Permission permissionId;
    
    /*
     * 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_AUDIO_ID")
    private Integer itemAudioId;
    @JoinColumn(name = "AUDIO_ID", referencedColumnName = "AUDIO_ID")
    @OneToOne(optional = false)
    private Audio audioId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;
     * 
     */
     

    public ManagerPermission() {
    }

    public ManagerPermission(Integer managerPermissionId) {
        this.managerPermissionId = managerPermissionId;
    }

    public Integer getManagerPermissionId() {
        return managerPermissionId;
    }

    public void setManagerPermissionId(Integer managerPermissionId) {
        this.managerPermissionId = managerPermissionId;
    }

    public Manager getManagerId() {
        return managerId;
    }

    public void setManagerId(Manager managerId) {
        this.managerId = managerId;
    }

    public Permission getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Permission permissionId) {
        this.permissionId = permissionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (managerPermissionId != null ? managerPermissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ManagerPermission)) {
            return false;
        }
        ManagerPermission other = (ManagerPermission) object;
        if ((this.managerPermissionId == null && other.managerPermissionId != null) || (this.managerPermissionId != null && !this.managerPermissionId.equals(other.managerPermissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ManagerPermission[ managerPermissionId=" + managerPermissionId + " ]";
    }
    
}
