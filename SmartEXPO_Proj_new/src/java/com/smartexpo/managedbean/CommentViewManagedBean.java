/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.CommentJpaController;
import com.smartexpo.jpgcontrollers.ItemCommentJpaController;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Audio;
import com.smartexpo.models.Author;
import com.smartexpo.models.Comment;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemComment;
import com.smartexpo.models.Video;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class CommentViewManagedBean implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    private List<Comment> allComments;
    private List<Comment> selectedItemComments;
    private Comment selectedComment;
    private Item selectedItem;

    /**
     * Creates a new instance of CommentViewManagedBean
     */
    public CommentViewManagedBean() {
        selectedItemComments = new ArrayList<Comment>();
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Item selectedItem) {
        this.selectedItem = selectedItem;
    }
    private String authorName;
    private Date authorBirthDate;
    private Date authorDeathDate;
    private String authorIntro;
    private String audioTitle;

    public String getAuthorName() {

        if (selectedItem == null) {
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors != null && authors.size() != 0) {
            this.authorName = authors.get(0).getName();
            return authorName;
        } else {
            return "";
        }
    }

    public void setAuthorName(String AuthorName) {
        this.authorName = AuthorName;
    }

    public Date getAuthorBirthDate() {
        if (selectedItem == null) {
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
            this.authorBirthDate = authors.get(0).getBirthday();
            return authorBirthDate;
        } else {
            return null;
        }
    }

    public void setAuthorBirthDate(Date AuthorBirthDate) {
        this.authorBirthDate = AuthorBirthDate;
    }

    public Date getAuthorDeathDate() {
        if (selectedItem == null) {
            return null;
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
            this.authorDeathDate = authors.get(0).getDeathDate();
            return authorDeathDate;
        } else {
            return null;
        }
    }

    public void setAuthorDeathDate(Date AuthorDeathDate) {
        this.authorDeathDate = AuthorDeathDate;
    }

    public String getAuthorIntro() {
        if (selectedItem == null) {
            return "";
        }
        List<Author> authors = gi.getAuthorsByItemID(selectedItem.getItemId());
        if (authors.size() != 0) {
            this.authorIntro = authors.get(0).getIntroduction();
            return authorIntro;
        } else {
            return "";
        }
    }

    public void setAuthorIntro(String AuthorIntro) {
        this.authorIntro = AuthorIntro;
    }

    public String getAudioTitle() {
        if (selectedItem == null) {
            return "";
        }
        List<Audio> audios = gi.getAudioByItemID(selectedItem.getItemId());
        if (audios.size() != 0) {
            this.audioTitle = audios.get(0).getTitle();
            return audioTitle;
        }

        return "";
    }

    public void setAudioTitle(String AudioTitle) {
        this.audioTitle = AudioTitle;
    }

    public String getVideoTitle() {
        if (selectedItem == null) {
            return "";
        }
        List<Video> videos = gi.getVideoByItemID(selectedItem.getItemId());
        if (videos.size() != 0) {
            this.VideoTitle = videos.get(0).getTitle();
            return VideoTitle;
        }
        return "";
    }

    public void setVideoTitle(String VideoTitle) {
        this.VideoTitle = VideoTitle;
    }
    private String VideoTitle;

    /**
     * @return the allComments
     */
    public List<Comment> getAllComments() {
        if (allComments == null) {
            CommentJpaController cjc = new CommentJpaController(utx, emf);
            allComments = cjc.findCommentEntities();
            /*if(selectedItem!=null){
             allComments= gi.getCommentByItemID(selectedItem.getItemId());
             }else{
             LOG.log(Level.WARNING, "selectedItem  = null");
             }*/

        }
        //LOG.log(Level.WARNING,"comment num:"+allComments.size());
        return allComments;
    }

    /**
     * @param allComments the allComments to set
     */
    public void setAllComments(List<Comment> allComments) {
        this.allComments = allComments;
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
        return getAllComments().size();
    }

    public void destroyComment() {
        try {
            //ItemComment ic=gi.getItemComment(itemId, selectedComment.getCommentId());
            Comment c = selectedComment;

            List<ItemComment> itemComments = gi.getItemCommentsByCommentID(selectedComment.getCommentId());
            ItemCommentJpaController icjc = new ItemCommentJpaController(utx, emf);
            for (int i = 0; i < itemComments.size(); i++) {
                icjc.destroy(itemComments.get(i).getItemCommentId());
            }
            allComments.remove(c);

            //icjc.destroy(ic.getItemCommentId());
            CommentJpaController cjc = new CommentJpaController(utx, emf);
            cjc.destroy(c.getCommentId());


        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // All Comments中的detail选项，根据selectdComment找到相应item并将其属性设置为selectedItem的属性
    public void showItemDetail() {
        Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.WARNING, "comment id = {0}, commment name = {1}", new Object[]{selectedComment.getCommentId(), selectedComment.getContent()});
        selectedItem = gi.getItemByID(gi.getItemCommentsByCommentID(selectedComment.getCommentId()).get(0).getItemId().getItemId());
        Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.WARNING, "item name = {0}", selectedItem.getItemName());
    }

    // All Items中的detail comments选项，根据selectedItem找到相应的comment，并加入到selectedItemComments这个list中
    public void showDetialComments() {
        List<Comment> comments = gi.getCommentByItemID(selectedItem.getItemId());
        this.selectedItemComments = comments;
    }

    public int getSelectedCommentCount() {
        return selectedItemComments.size();
    }

    /**
     * @return the selectedItemComments
     */
    public List<Comment> getSelectedItemComments() {
        return selectedItemComments;
    }

    /**
     * @param selectedItemComments the selectedItemComments to set
     */
    public void setSelectedItemComments(List<Comment> selectedItemComments) {
        this.selectedItemComments = selectedItemComments;
    }
}
