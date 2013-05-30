/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class CommentViewManagedBean implements Serializable {

    private static final Logger LOG = Logger.getLogger(CommentViewManagedBean.class.getName());
    private List<Comment> comments;
    private Comment selectedComment;

    /**
     * Creates a new instance of CommentViewManagedBean
     */
    public CommentViewManagedBean() {
        comments = new ArrayList<Comment>();
        for (int i = 0; i < 50; i++) {
            comments.add(new Comment(new Integer(i).toString(), "Content " + i, new Date(), "Username " + i));
        }
    }

    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * @return the selectedComment
     */
    public Comment getSelectedComment() {
        return selectedComment;
    }

    /**
     * @param selectedComment the selectedComment to set
     */
    public void setSelectedComment(Comment selectedComment) {
        this.selectedComment = selectedComment;
    }

    public int getCount() {
        return getComments().size();
    }

    public String destroyComment() {
        LOG.log(Level.WARNING, "Removeeeeeeeee {0}", selectedComment);
        comments.remove(selectedComment);
        // 从数据库中删除
        return null;
    }
}
