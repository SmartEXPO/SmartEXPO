/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean.item;

import com.smartexpo.controls.GetInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@ViewScoped
public class Comment implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // Comment fields
    private List<com.smartexpo.models.Comment> comments;
    private List<Integer> ids;
    private List<String> contents;
    private List<Date> times;
    private List<String> usernames;

    /**
     * Creates a new instance of Comment
     */
    public Comment() {
        ids = new ArrayList<Integer>();
        contents = new ArrayList<String>();
        times = new ArrayList<Date>();
        usernames = new ArrayList<String>();
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(emf, utx);
        }
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();

        int itemID = Integer.parseInt((String) request.getAttribute("id"));
        comments = gi.getCommentByItemID(itemID);

        setAllCommentsInfo();
    }

    /**
     * @return the ids
     */
    public List<Integer> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    /**
     * @return the contents
     */
    public List<String> getContents() {
        return contents;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(List<String> contents) {
        this.contents = contents;
    }

    /**
     * @return the times
     */
    public List<Date> getTimes() {
        return times;
    }

    /**
     * @param times the times to set
     */
    public void setTimes(List<Date> times) {
        this.times = times;
    }

    /**
     * @return the usernames
     */
    public List<String> getUsernames() {
        return usernames;
    }

    /**
     * @param usernames the usernames to set
     */
    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }

    private void setAllCommentsInfo() {
        for (com.smartexpo.models.Comment comment : comments) {
            addCommentID(comment);
            addCommentContent(comment);
            addCommentTime(comment);
            addCommentUsername(comment);
        }
    }

    private void addCommentID(com.smartexpo.models.Comment comment) {
        ids.add(comment.getCommentId());
    }

    private void addCommentContent(com.smartexpo.models.Comment comment) {
        contents.add(comment.getContent());
    }

    private void addCommentTime(com.smartexpo.models.Comment comment) {
        times.add(comment.getTime());
    }

    private void addCommentUsername(com.smartexpo.models.Comment comment) {
        usernames.add(comment.getUsername());
    }
}
