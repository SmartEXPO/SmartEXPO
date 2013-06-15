/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "MANAGER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Manager.findAll", query = "SELECT m FROM Manager m"),
    @NamedQuery(name = "Manager.findByManagerId", query = "SELECT m FROM Manager m WHERE m.managerId = :managerId"),
    @NamedQuery(name = "Manager.findByUsername", query = "SELECT m FROM Manager m WHERE m.username = :username"),
    @NamedQuery(name = "Manager.findByPassword", query = "SELECT m FROM Manager m WHERE m.password = :password")})
public class Manager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MANAGER_ID")
    private Integer managerId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "USERNAME")
    private String username;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PASSWORD")
    private String password;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "managerId")
    private ManagerPermission managerPermission;

    /*
     * 
     * @OneToOne(cascade = CascadeType.ALL, mappedBy = "itemId")
     private ItemAudio itemAudio;
     * 
     */
    public ManagerPermission getManagerPermission() {
        return managerPermission;
    }

    public void setManagerPermission(ManagerPermission managerPermission) {
        this.managerPermission = managerPermission;
    }

    public Manager() {
    }

    public Manager(Integer managerId) {
        this.managerId = managerId;
    }

    public Manager(Integer managerId, String username, String password) {
        this.managerId = managerId;
        this.username = username;
        this.password = password;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (managerId != null ? managerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Manager)) {
            return false;
        }
        Manager other = (Manager) object;
        if ((this.managerId == null && other.managerId != null) || (this.managerId != null && !this.managerId.equals(other.managerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Manager[ managerId=" + managerId + " ]";
    }
    @NotNull
    @Column(name = "PERMISSION_1")
    private boolean permission1;

    public boolean isPermission1() {
        return permission1;
    }

    public void setPermission1(boolean permission1) {
        this.permission1 = permission1;
    }

    public boolean isPermission2() {
        return permission2;
    }

    public void setPermission2(boolean permission2) {
        this.permission2 = permission2;
    }

    public boolean isPermission3() {
        return permission3;
    }

    public void setPermission3(boolean permission3) {
        this.permission3 = permission3;
    }

    public boolean isPermission4() {
        return permission4;
    }

    public void setPermission4(boolean permission4) {
        this.permission4 = permission4;
    }

    public boolean isPermission5() {
        return permission5;
    }

    public void setPermission5(boolean permission5) {
        this.permission5 = permission5;
    }
    @NotNull
    @Column(name = "PERMISSION_2")
    private boolean permission2;
    @NotNull
    @Column(name = "PERMISSION_3")
    private boolean permission3;
    @NotNull
    @Column(name = "PERMISSION_4")
    private boolean permission4;
    @NotNull
    @Column(name = "PERMISSION_5")
    private boolean permission5;
}
