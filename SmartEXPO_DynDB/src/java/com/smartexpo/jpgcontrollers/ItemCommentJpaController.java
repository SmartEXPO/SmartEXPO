/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.models.Item;
import com.smartexpo.models.Comment;
import com.smartexpo.models.ItemComment;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ItemCommentJpaController implements Serializable {

    public ItemCommentJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemComment itemComment) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Item itemIdOrphanCheck = itemComment.getItemId();
        if (itemIdOrphanCheck != null) {
            ItemComment oldItemCommentOfItemId = itemIdOrphanCheck.getItemComment();
            if (oldItemCommentOfItemId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Item " + itemIdOrphanCheck + " already has an item of type ItemComment whose itemId column cannot be null. Please make another selection for the itemId field.");
            }
        }
        Comment commentIdOrphanCheck = itemComment.getCommentId();
        if (commentIdOrphanCheck != null) {
            ItemComment oldItemCommentOfCommentId = commentIdOrphanCheck.getItemComment();
            if (oldItemCommentOfCommentId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Comment " + commentIdOrphanCheck + " already has an item of type ItemComment whose commentId column cannot be null. Please make another selection for the commentId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item itemId = itemComment.getItemId();
            if (itemId != null) {
                itemId = em.getReference(itemId.getClass(), itemId.getItemId());
                itemComment.setItemId(itemId);
            }
            Comment commentId = itemComment.getCommentId();
            if (commentId != null) {
                commentId = em.getReference(commentId.getClass(), commentId.getCommentId());
                itemComment.setCommentId(commentId);
            }
            em.persist(itemComment);
            if (itemId != null) {
                itemId.setItemComment(itemComment);
                itemId = em.merge(itemId);
            }
            if (commentId != null) {
                commentId.setItemComment(itemComment);
                commentId = em.merge(commentId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ItemComment itemComment) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemComment persistentItemComment = em.find(ItemComment.class, itemComment.getItemCommentId());
            Item itemIdOld = persistentItemComment.getItemId();
            Item itemIdNew = itemComment.getItemId();
            Comment commentIdOld = persistentItemComment.getCommentId();
            Comment commentIdNew = itemComment.getCommentId();
            List<String> illegalOrphanMessages = null;
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                ItemComment oldItemCommentOfItemId = itemIdNew.getItemComment();
                if (oldItemCommentOfItemId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Item " + itemIdNew + " already has an item of type ItemComment whose itemId column cannot be null. Please make another selection for the itemId field.");
                }
            }
            if (commentIdNew != null && !commentIdNew.equals(commentIdOld)) {
                ItemComment oldItemCommentOfCommentId = commentIdNew.getItemComment();
                if (oldItemCommentOfCommentId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Comment " + commentIdNew + " already has an item of type ItemComment whose commentId column cannot be null. Please make another selection for the commentId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemIdNew != null) {
                itemIdNew = em.getReference(itemIdNew.getClass(), itemIdNew.getItemId());
                itemComment.setItemId(itemIdNew);
            }
            if (commentIdNew != null) {
                commentIdNew = em.getReference(commentIdNew.getClass(), commentIdNew.getCommentId());
                itemComment.setCommentId(commentIdNew);
            }
            itemComment = em.merge(itemComment);
            if (itemIdOld != null && !itemIdOld.equals(itemIdNew)) {
                itemIdOld.setItemComment(null);
                itemIdOld = em.merge(itemIdOld);
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                itemIdNew.setItemComment(itemComment);
                itemIdNew = em.merge(itemIdNew);
            }
            if (commentIdOld != null && !commentIdOld.equals(commentIdNew)) {
                commentIdOld.setItemComment(null);
                commentIdOld = em.merge(commentIdOld);
            }
            if (commentIdNew != null && !commentIdNew.equals(commentIdOld)) {
                commentIdNew.setItemComment(itemComment);
                commentIdNew = em.merge(commentIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = itemComment.getItemCommentId();
                if (findItemComment(id) == null) {
                    throw new NonexistentEntityException("The itemComment with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemComment itemComment;
            try {
                itemComment = em.getReference(ItemComment.class, id);
                itemComment.getItemCommentId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemComment with id " + id + " no longer exists.", enfe);
            }
            Item itemId = itemComment.getItemId();
            if (itemId != null) {
                itemId.setItemComment(null);
                itemId = em.merge(itemId);
            }
            Comment commentId = itemComment.getCommentId();
            if (commentId != null) {
                commentId.setItemComment(null);
                commentId = em.merge(commentId);
            }
            em.remove(itemComment);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ItemComment> findItemCommentEntities() {
        return findItemCommentEntities(true, -1, -1);
    }

    public List<ItemComment> findItemCommentEntities(int maxResults, int firstResult) {
        return findItemCommentEntities(false, maxResults, firstResult);
    }

    private List<ItemComment> findItemCommentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemComment.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ItemComment findItemComment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemComment.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCommentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemComment> rt = cq.from(ItemComment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    
    
}
