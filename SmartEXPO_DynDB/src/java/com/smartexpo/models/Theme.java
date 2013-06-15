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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author tornado718
 */
@Entity
@Table(name = "THEME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Theme.findAll", query = "SELECT t FROM Theme t"),
    @NamedQuery(name = "Theme.findByTheme", query = "SELECT t FROM Theme t WHERE t.theme = :theme"),
    @NamedQuery(name = "Theme.findByThemeId", query = "SELECT t FROM Theme t WHERE t.themeId = :themeId")})
public class Theme implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "THEME")
    private String theme;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "THEME_ID")
    private Integer themeId;

    public Theme() {
    }

    public Theme(Integer themeId) {
        this.themeId = themeId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Integer getThemeId() {
        return themeId;
    }

    public void setThemeId(Integer themeId) {
        this.themeId = themeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (themeId != null ? themeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Theme)) {
            return false;
        }
        Theme other = (Theme) object;
        if ((this.themeId == null && other.themeId != null) || (this.themeId != null && !this.themeId.equals(other.themeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.Theme[ themeId=" + themeId + " ]";
    }
    
}
