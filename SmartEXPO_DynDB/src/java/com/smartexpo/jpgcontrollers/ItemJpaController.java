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
import com.smartexpo.models.ItemComment;
import com.smartexpo.models.ItemAuthor;
import com.smartexpo.models.Description;
import com.smartexpo.models.Item;
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.ItemAudio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ItemJpaController implements Serializable {

    public ItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemComment itemComment = item.getItemComment();
            if (itemComment != null) {
                itemComment = em.getReference(itemComment.getClass(), itemComment.getItemCommentId());
                item.setItemComment(itemComment);
            }
            ItemAuthor itemAuthor = item.getItemAuthor();
            if (itemAuthor != null) {
                itemAuthor = em.getReference(itemAuthor.getClass(), itemAuthor.getItemAuthorId());
                item.setItemAuthor(itemAuthor);
            }
            Description description = item.getDescription();
            if (description != null) {
                description = em.getReference(description.getClass(), description.getDescriptionId());
                item.setDescription(description);
            }
            ItemVideo itemVideo = item.getItemVideo();
            if (itemVideo != null) {
                itemVideo = em.getReference(itemVideo.getClass(), itemVideo.getItemVideoId());
                item.setItemVideo(itemVideo);
            }
            ItemAudio itemAudio = item.getItemAudio();
            if (itemAudio != null) {
                itemAudio = em.getReference(itemAudio.getClass(), itemAudio.getItemAudioId());
                item.setItemAudio(itemAudio);
            }
            em.persist(item);
            if (itemComment != null) {
                Item oldItemIdOfItemComment = itemComment.getItemId();
                if (oldItemIdOfItemComment != null) {
                    oldItemIdOfItemComment.setItemComment(null);
                    oldItemIdOfItemComment = em.merge(oldItemIdOfItemComment);
                }
                itemComment.setItemId(item);
                itemComment = em.merge(itemComment);
            }
            if (itemAuthor != null) {
                Item oldItemIdOfItemAuthor = itemAuthor.getItemId();
                if (oldItemIdOfItemAuthor != null) {
                    oldItemIdOfItemAuthor.setItemAuthor(null);
                    oldItemIdOfItemAuthor = em.merge(oldItemIdOfItemAuthor);
                }
                itemAuthor.setItemId(item);
                itemAuthor = em.merge(itemAuthor);
            }
            if (description != null) {
                Item oldItemIdOfDescription = description.getItemId();
                if (oldItemIdOfDescription != null) {
                    oldItemIdOfDescription.setDescription(null);
                    oldItemIdOfDescription = em.merge(oldItemIdOfDescription);
                }
                description.setItemId(item);
                description = em.merge(description);
            }
            if (itemVideo != null) {
                Item oldItemIdOfItemVideo = itemVideo.getItemId();
                if (oldItemIdOfItemVideo != null) {
                    oldItemIdOfItemVideo.setItemVideo(null);
                    oldItemIdOfItemVideo = em.merge(oldItemIdOfItemVideo);
                }
                itemVideo.setItemId(item);
                itemVideo = em.merge(itemVideo);
            }
            if (itemAudio != null) {
                Item oldItemIdOfItemAudio = itemAudio.getItemId();
                if (oldItemIdOfItemAudio != null) {
                    oldItemIdOfItemAudio.setItemAudio(null);
                    oldItemIdOfItemAudio = em.merge(oldItemIdOfItemAudio);
                }
                itemAudio.setItemId(item);
                itemAudio = em.merge(itemAudio);
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

    public void edit(Item item) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item persistentItem = em.find(Item.class, item.getItemId());
            ItemComment itemCommentOld = persistentItem.getItemComment();
            ItemComment itemCommentNew = item.getItemComment();
            ItemAuthor itemAuthorOld = persistentItem.getItemAuthor();
            ItemAuthor itemAuthorNew = item.getItemAuthor();
            Description descriptionOld = persistentItem.getDescription();
            Description descriptionNew = item.getDescription();
            ItemVideo itemVideoOld = persistentItem.getItemVideo();
            ItemVideo itemVideoNew = item.getItemVideo();
            ItemAudio itemAudioOld = persistentItem.getItemAudio();
            ItemAudio itemAudioNew = item.getItemAudio();
            List<String> illegalOrphanMessages = null;
            if (itemCommentOld != null && !itemCommentOld.equals(itemCommentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemComment " + itemCommentOld + " since its itemId field is not nullable.");
            }
            if (itemAuthorOld != null && !itemAuthorOld.equals(itemAuthorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemAuthor " + itemAuthorOld + " since its itemId field is not nullable.");
            }
            if (descriptionOld != null && !descriptionOld.equals(descriptionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Description " + descriptionOld + " since its itemId field is not nullable.");
            }
            if (itemVideoOld != null && !itemVideoOld.equals(itemVideoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemVideo " + itemVideoOld + " since its itemId field is not nullable.");
            }
            if (itemAudioOld != null && !itemAudioOld.equals(itemAudioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemAudio " + itemAudioOld + " since its itemId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemCommentNew != null) {
                itemCommentNew = em.getReference(itemCommentNew.getClass(), itemCommentNew.getItemCommentId());
                item.setItemComment(itemCommentNew);
            }
            if (itemAuthorNew != null) {
                itemAuthorNew = em.getReference(itemAuthorNew.getClass(), itemAuthorNew.getItemAuthorId());
                item.setItemAuthor(itemAuthorNew);
            }
            if (descriptionNew != null) {
                descriptionNew = em.getReference(descriptionNew.getClass(), descriptionNew.getDescriptionId());
                item.setDescription(descriptionNew);
            }
            if (itemVideoNew != null) {
                itemVideoNew = em.getReference(itemVideoNew.getClass(), itemVideoNew.getItemVideoId());
                item.setItemVideo(itemVideoNew);
            }
            if (itemAudioNew != null) {
                itemAudioNew = em.getReference(itemAudioNew.getClass(), itemAudioNew.getItemAudioId());
                item.setItemAudio(itemAudioNew);
            }
            item = em.merge(item);
            if (itemCommentNew != null && !itemCommentNew.equals(itemCommentOld)) {
                Item oldItemIdOfItemComment = itemCommentNew.getItemId();
                if (oldItemIdOfItemComment != null) {
                    oldItemIdOfItemComment.setItemComment(null);
                    oldItemIdOfItemComment = em.merge(oldItemIdOfItemComment);
                }
                itemCommentNew.setItemId(item);
                itemCommentNew = em.merge(itemCommentNew);
            }
            if (itemAuthorNew != null && !itemAuthorNew.equals(itemAuthorOld)) {
                Item oldItemIdOfItemAuthor = itemAuthorNew.getItemId();
                if (oldItemIdOfItemAuthor != null) {
                    oldItemIdOfItemAuthor.setItemAuthor(null);
                    oldItemIdOfItemAuthor = em.merge(oldItemIdOfItemAuthor);
                }
                itemAuthorNew.setItemId(item);
                itemAuthorNew = em.merge(itemAuthorNew);
            }
            if (descriptionNew != null && !descriptionNew.equals(descriptionOld)) {
                Item oldItemIdOfDescription = descriptionNew.getItemId();
                if (oldItemIdOfDescription != null) {
                    oldItemIdOfDescription.setDescription(null);
                    oldItemIdOfDescription = em.merge(oldItemIdOfDescription);
                }
                descriptionNew.setItemId(item);
                descriptionNew = em.merge(descriptionNew);
            }
            if (itemVideoNew != null && !itemVideoNew.equals(itemVideoOld)) {
                Item oldItemIdOfItemVideo = itemVideoNew.getItemId();
                if (oldItemIdOfItemVideo != null) {
                    oldItemIdOfItemVideo.setItemVideo(null);
                    oldItemIdOfItemVideo = em.merge(oldItemIdOfItemVideo);
                }
                itemVideoNew.setItemId(item);
                itemVideoNew = em.merge(itemVideoNew);
            }
            if (itemAudioNew != null && !itemAudioNew.equals(itemAudioOld)) {
                Item oldItemIdOfItemAudio = itemAudioNew.getItemId();
                if (oldItemIdOfItemAudio != null) {
                    oldItemIdOfItemAudio.setItemAudio(null);
                    oldItemIdOfItemAudio = em.merge(oldItemIdOfItemAudio);
                }
                itemAudioNew.setItemId(item);
                itemAudioNew = em.merge(itemAudioNew);
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
                Integer id = item.getItemId();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getItemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ItemComment itemCommentOrphanCheck = item.getItemComment();
            if (itemCommentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the ItemComment " + itemCommentOrphanCheck + " in its itemComment field has a non-nullable itemId field.");
            }
            ItemAuthor itemAuthorOrphanCheck = item.getItemAuthor();
            if (itemAuthorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the ItemAuthor " + itemAuthorOrphanCheck + " in its itemAuthor field has a non-nullable itemId field.");
            }
            Description descriptionOrphanCheck = item.getDescription();
            if (descriptionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the Description " + descriptionOrphanCheck + " in its description field has a non-nullable itemId field.");
            }
            ItemVideo itemVideoOrphanCheck = item.getItemVideo();
            if (itemVideoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the ItemVideo " + itemVideoOrphanCheck + " in its itemVideo field has a non-nullable itemId field.");
            }
            ItemAudio itemAudioOrphanCheck = item.getItemAudio();
            if (itemAudioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Item (" + item + ") cannot be destroyed since the ItemAudio " + itemAudioOrphanCheck + " in its itemAudio field has a non-nullable itemId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(item);
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

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
