/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.managedbean.item.Audio;
import com.smartexpo.managedbean.item.Author;
import com.smartexpo.managedbean.item.Description;
import com.smartexpo.managedbean.item.Item;
import com.smartexpo.managedbean.item.Video;
import com.smartexpo.managedbean.item.Comment;
import com.smartexpo.models.ItemComment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@ViewScoped
public class ItemController implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // ItemController Fields
    static final Logger logger = Logger.getLogger(ItemController.class.getName());
    @ManagedProperty(value = "#{item}")
    private Item itemBean;
    private int itemID;
    private String itemName;
    @ManagedProperty(value = "#{description}")
    private Description descriptionBean;
    private int descriptionID;
    private String descriptionTitle;
    private String descriptionContent;
    @ManagedProperty(value = "#{author}")
    private Author authorBean;
    private List<Integer> authorIDs;
    private List<String> authorNames;
    private List<Date> authorBirthdays;
    private List<Date> authorDeathDates;
    private List<String> authorIntroductions;
    @ManagedProperty(value = "#{audio}")
    private Audio audioBean;
    private List<Integer> audioIDs;
    private List<String> audioTitles;
    private List<String> audioURLs;
    private List<String> audioDescriptions;
    @ManagedProperty(value = "#{video}")
    private Video videoBean;
    private List<Integer> videoIDs;
    private List<String> videoTitles;
    private List<String> videoURLs;
    private List<String> videoDescriptions;
    @ManagedProperty(value = "#{comment}")
    private Comment commentBean;
    private List<Integer> commentIDs;
    private List<String> commentContents;
    private List<Date> commentTimes;
    private List<String> commentUsernames;
    private List<String> commentShowUsernameAndContent;
    private String commentuser;
    private String commentcontent;

    /**
     * Creates a new instance of ItemController
     */
    public ItemController() {
        commentIDs = new ArrayList<Integer>();
        commentContents = new ArrayList<String>();
        commentTimes = new ArrayList<Date>();
        commentUsernames = new ArrayList<String>();
        commentShowUsernameAndContent = new ArrayList<String>();
        logger.log(Level.WARNING, "Item Controller Construct");
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(em, utx);
        }
        initialCommentsList();
    }

    /**
     * @param itemBean the itemBean to set
     */
    public void setItemBean(Item itemBean) {
        this.itemBean = itemBean;
    }

    /**
     * @return the itemID
     */
    public int getItemID() {
        setItemID(itemBean.getId());
        return itemID;
    }

    /**
     * @return the itemName
     */
    public String getItemName() {
        setItemName(itemBean.getName());
        return itemName;
    }

    /**
     * @param descriptionBean the descriptionBean to set
     */
    public void setDescriptionBean(Description descriptionBean) {
        this.descriptionBean = descriptionBean;
    }

    /**
     * @return the descriptionID
     */
    public int getDescriptionID() {
        setDescriptionID(descriptionBean.getId());
        return descriptionID;
    }

    /**
     * @return the descriptionTitle
     */
    public String getDescriptionTitle() {
        setDescriptionTitle(descriptionBean.getTitle());
        return descriptionTitle;
    }

    /**
     * @return the descriptionContent
     */
    public String getDescriptionContent() {
        setDescriptionContent(descriptionBean.getContent());
        return descriptionContent;
    }

    /**
     * @param authorBean the authorBean to set
     */
    public void setAuthorBean(Author authorBean) {
        this.authorBean = authorBean;
    }

    /**
     * @return the authorIDs
     */
    public List<Integer> getAuthorIDs() {
        setAuthorIDs(authorBean.getIds());
        return authorIDs;
    }

    /**
     * @return the authorNames
     */
    public List<String> getAuthorNames() {
        setAuthorNames(authorBean.getNames());
        return authorNames;
    }

    /**
     * @return the authorBirthdays
     */
    public List<Date> getAuthorBirthdays() {
        setAuthorBirthdays(authorBean.getBirthdays());
        return authorBirthdays;
    }

    /**
     * @return the authorDeathDates
     */
    public List<Date> getAuthorDeathDates() {
        setAuthorDeathDates(authorBean.getDeathDates());
        return authorDeathDates;
    }

    /**
     * @return the authorIntroductions
     */
    public List<String> getAuthorIntroductions() {
        setAuthorIntroductions(authorBean.getIntroductions());
        return authorIntroductions;
    }

    /**
     * @param audioBean the audioBean to set
     */
    public void setAudioBean(Audio audioBean) {
        this.audioBean = audioBean;
    }

    /**
     * @return the audioIDs
     */
    public List<Integer> getAudioIDs() {
        setAudioIDs(audioBean.getIds());
        return audioIDs;
    }

    /**
     * @return the audioTitles
     */
    public List<String> getAudioTitles() {
        setAudioTitles(audioBean.getTitles());
        return audioTitles;
    }

    /**
     * @return the audioURLs
     */
    public List<String> getAudioURLs() {
        setAudioURLs(audioBean.getURLs());
        return audioURLs;
    }

    /**
     * @return the audioDescriptions
     */
    public List<String> getAudioDescriptions() {
        setAudioDescriptions(audioBean.getDescriptions());
        return audioDescriptions;
    }

    /**
     * @param videoBean the videoBean to set
     */
    public void setVideoBean(Video videoBean) {
        this.videoBean = videoBean;
    }

    /**
     * @return the videoIDs
     */
    public List<Integer> getVideoIDs() {
        setVideoIDs(videoBean.getIds());
        return videoIDs;
    }

    /**
     * @return the videoTitles
     */
    public List<String> getVideoTitles() {
        setVideoTitles(videoBean.getTitles());
        return videoTitles;
    }

    /**
     * @return the videoURLs
     */
    public List<String> getVideoURLs() {
        setVideoURLs(videoBean.getURLs());
        return videoURLs;
    }

    /**
     * @return the videoDescriptions
     */
    public List<String> getVideoDescriptions() {
        setVideoDescriptions(videoBean.getDescriptions());
        return videoDescriptions;
    }

    /**
     * @param commentBean the commentBean to set
     */
    public void setCommentBean(Comment commentBean) {
        this.commentBean = commentBean;
    }

    /**
     * @return the commentIDs
     */
    public List<Integer> getCommentIDs() {
        setCommentIDs(commentBean.getIds());
        return commentIDs;
    }

    /**
     * @return the commentContents
     */
    public List<String> getCommentContents() {
        setCommentContents(commentBean.getContents());
        return commentContents;
    }

    /**
     * @return the commentTimes
     */
    public List<Date> getCommentTimes() {
        setCommentTimes(commentBean.getTimes());
        return commentTimes;
    }

    /**
     * @return the commentUsernames
     */
    public List<String> getCommentUsernames() {
        setCommentUsernames(commentBean.getUsernames());
        return commentUsernames;
    }

    /**
     * @return the commentShowUsernameAndContent
     */
    public List<String> getCommentShowUsernameAndContent() {
        return commentShowUsernameAndContent;
    }

    /**
     * @param commentShowUsernameAndContent the commentShowUsernameAndContent to
     * set
     */
    public void setCommentShowUsernameAndContent(List<String> commentShowUsernameAndContent) {
        this.commentShowUsernameAndContent = commentShowUsernameAndContent;
    }

    /**
     * @return the commentuser
     */
    public String getCommentuser() {
        return commentuser;
    }

    /**
     * @param commentuser the commentuser to set
     */
    public void setCommentuser(String commentuser) {
        this.commentuser = commentuser;
    }

    /**
     * @return the commentcontent
     */
    public String getCommentcontent() {
        return commentcontent;
    }

    /**
     * @param commentcontent the commentcontent to set
     */
    public void setCommentcontent(String commentcontent) {
        this.commentcontent = commentcontent;
    }

    public void addComment(AjaxBehaviorEvent event) {
        storeComment();

        setCommentuser("");
        setCommentcontent("");
        logger.log(Level.WARNING, "Add Comment");
    }

    private void storeComment() {
        try {
            com.smartexpo.models.Comment newComment = new com.smartexpo.models.Comment();
            newComment.setUsername(commentuser);
            newComment.setContent(commentcontent);
            newComment.setTime(new Date());

            ItemComment newIC = new ItemComment();
            newIC.setItemId(itemBean.getItem());
            newIC.setCommentId(newComment);

            addCommentID(newComment);
            addCommentUsername(newComment);
            addCommentTime(newComment);
            addCommentContent(newComment);
            addCommentShowUsernameAndContent(newComment);

            utx.begin();
            em.persist(newIC);
            em.persist(newComment);
            utx.commit();

        } catch (RollbackException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotSupportedException ex) {
            Logger.getLogger(ItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initialCommentsList() {
        // 获得attribute
        if (FacesContext.getCurrentInstance().getAttributes().get("itemid") == null) {
            logger.log(Level.WARNING, "itemid is null!!!!");
            setItemID(1);
        }
        List<com.smartexpo.models.Comment> allComments = gi.getCommentByItemID(itemID);
        logger.log(Level.WARNING, "itemId = {0}", itemID);
        for (com.smartexpo.models.Comment com : allComments) {
            addCommentID(com);
            addCommentUsername(com);
            addCommentContent(com);
            addCommentTime(com);
            addCommentShowUsernameAndContent(com);
        }
    }

    private void addCommentID(com.smartexpo.models.Comment com) {
        commentIDs.add(com.getCommentId());
    }

    private void addCommentUsername(com.smartexpo.models.Comment com) {
        commentUsernames.add(com.getUsername());
    }

    private void addCommentContent(com.smartexpo.models.Comment com) {
        commentContents.add(com.getContent());
    }

    private void addCommentTime(com.smartexpo.models.Comment com) {
        commentTimes.add(com.getTime());
    }

    private void addCommentShowUsernameAndContent(com.smartexpo.models.Comment com) {
        getCommentShowUsernameAndContent().add(com.getUsername() + ": " + com.getContent());
    }
}