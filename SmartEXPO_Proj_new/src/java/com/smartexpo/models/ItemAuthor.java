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
@Table(name = "ITEM_AUTHOR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemAuthor.findAll", query = "SELECT i FROM ItemAuthor i"),
    @NamedQuery(name = "ItemAuthor.findByItemAuthorId", query = "SELECT i FROM ItemAuthor i WHERE i.itemAuthorId = :itemAuthorId"),
    @NamedQuery(name = "ItemAuthor.findByItemId", query = "SELECT i FROM ItemAuthor i WHERE i.itemId = :itemId")})//WHERE i.itemId.itemId = :itemId
public class ItemAuthor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_AUTHOR_ID")
    private Integer itemAuthorId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "AUTHOR_ID", referencedColumnName = "AUTHOR_ID")
    @OneToOne(optional = false)
    private Author authorId;

    public ItemAuthor() {
    }

    public ItemAuthor(Integer itemAuthorId) {
        this.itemAuthorId = itemAuthorId;
    }

    public Integer getItemAuthorId() {
        return itemAuthorId;
    }

    public void setItemAuthorId(Integer itemAuthorId) {
        this.itemAuthorId = itemAuthorId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemAuthorId != null ? itemAuthorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemAuthor)) {
            return false;
        }
        ItemAuthor other = (ItemAuthor) object;
        if ((this.itemAuthorId == null && other.itemAuthorId != null) || (this.itemAuthorId != null && !this.itemAuthorId.equals(other.itemAuthorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ItemAuthor[ itemAuthorId=" + itemAuthorId + " ]";
    }
}
