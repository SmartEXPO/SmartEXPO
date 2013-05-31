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
@Table(name = "ITEM_COMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ItemComment.findAll", query = "SELECT i FROM ItemComment i"),
    @NamedQuery(name = "ItemComment.findByItemCommentId", query = "SELECT i FROM ItemComment i WHERE i.itemCommentId = :itemCommentId"),
    @NamedQuery(name = "ItemComment.findByItemId", query = "SELECT i FROM ItemComment i WHERE i.itemId = :itemId"),
    @NamedQuery(name = "ItemComment.findByCommentId", query = "SELECT i FROM ItemComment i WHERE i.commentId = :commentId")
})
public class ItemComment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ITEM_COMMENT_ID")
    private Integer itemCommentId;
    @JoinColumn(name = "ITEM_ID", referencedColumnName = "ITEM_ID")
    @OneToOne(optional = false)
    private Item itemId;
    @JoinColumn(name = "COMMENT_ID", referencedColumnName = "COMMENT_ID")
    @OneToOne(optional = false)
    private Comment commentId;

    public ItemComment() {
    }

    public ItemComment(Integer itemCommentId) {
        this.itemCommentId = itemCommentId;
    }

    public Integer getItemCommentId() {
        return itemCommentId;
    }

    public void setItemCommentId(Integer itemCommentId) {
        this.itemCommentId = itemCommentId;
    }

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Comment getCommentId() {
        return commentId;
    }

    public void setCommentId(Comment commentId) {
        this.commentId = commentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemCommentId != null ? itemCommentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemComment)) {
            return false;
        }
        ItemComment other = (ItemComment) object;
        if ((this.itemCommentId == null && other.itemCommentId != null) || (this.itemCommentId != null && !this.itemCommentId.equals(other.itemCommentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.smartexpo.models.ItemComment[ itemCommentId=" + itemCommentId + " ]";
    }
}
