/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.CommentJpaController;
import com.smartexpo.jpgcontrollers.ItemCommentJpaController;
import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Comment;
import com.smartexpo.models.ItemComment;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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
@SessionScoped
public class CommentViewManagedBean implements Serializable {
    
    
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    
    @Resource
    private UserTransaction utx;
    
    private GetInfo gi;
    

    private static final Logger LOG = Logger.getLogger(CommentViewManagedBean.class.getName());
    private List<Comment> comments;
    private Comment selectedComment;
    
    
    /**
     * Creates a new instance of CommentViewManagedBean
     */
    public CommentViewManagedBean() {
        
        
        
        
    }

    /**
     * @return the comments
     */
    public List<Comment> getComments() {
        if(comments==null){
            gi=new GetInfo(emf, utx);
            CommentJpaController cjc=new CommentJpaController(utx, emf);
            comments=cjc.findCommentEntities();
            //comments= gi.getCommentByItemID(itemId);
            
        }
        LOG.log(Level.WARNING,"comment num:"+comments.size());
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

    public void destroyComment() {
        try {
            
            gi=new GetInfo(emf, utx);
            //ItemComment ic=gi.getItemComment(itemId, selectedComment.getCommentId());
            Comment c= selectedComment;
            
            List<ItemComment> itemComments= gi.getItemCommentsByCommentID(selectedComment.getCommentId());
            ItemCommentJpaController icjc=new ItemCommentJpaController(utx, emf);
            for(int i=0;i<itemComments.size();i++){
                icjc.destroy(itemComments.get(i).getItemCommentId());
            }
            comments.remove(c);
            
            //icjc.destroy(ic.getItemCommentId());
            CommentJpaController cjc=new CommentJpaController(utx, emf);
            cjc.destroy(c.getCommentId());
            
            
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CommentViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
            
        
      
    }
}
