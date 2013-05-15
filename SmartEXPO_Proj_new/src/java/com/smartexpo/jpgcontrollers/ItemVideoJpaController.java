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
import com.smartexpo.models.ItemVideo;
import com.smartexpo.models.Video;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ItemVideoJpaController implements Serializable {

    public ItemVideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemVideo itemVideo) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Item itemIdOrphanCheck = itemVideo.getItemId();
        if (itemIdOrphanCheck != null) {
            ItemVideo oldItemVideoOfItemId = itemIdOrphanCheck.getItemVideo();
            if (oldItemVideoOfItemId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Item " + itemIdOrphanCheck + " already has an item of type ItemVideo whose itemId column cannot be null. Please make another selection for the itemId field.");
            }
        }
        Video videoIdOrphanCheck = itemVideo.getVideoId();
        if (videoIdOrphanCheck != null) {
            ItemVideo oldItemVideoOfVideoId = videoIdOrphanCheck.getItemVideo();
            if (oldItemVideoOfVideoId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Video " + videoIdOrphanCheck + " already has an item of type ItemVideo whose videoId column cannot be null. Please make another selection for the videoId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item itemId = itemVideo.getItemId();
            if (itemId != null) {
                itemId = em.getReference(itemId.getClass(), itemId.getItemId());
                itemVideo.setItemId(itemId);
            }
            Video videoId = itemVideo.getVideoId();
            if (videoId != null) {
                videoId = em.getReference(videoId.getClass(), videoId.getVideoId());
                itemVideo.setVideoId(videoId);
            }
            em.persist(itemVideo);
            if (itemId != null) {
                itemId.setItemVideo(itemVideo);
                itemId = em.merge(itemId);
            }
            if (videoId != null) {
                videoId.setItemVideo(itemVideo);
                videoId = em.merge(videoId);
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

    public void edit(ItemVideo itemVideo) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemVideo persistentItemVideo = em.find(ItemVideo.class, itemVideo.getItemVideoId());
            Item itemIdOld = persistentItemVideo.getItemId();
            Item itemIdNew = itemVideo.getItemId();
            Video videoIdOld = persistentItemVideo.getVideoId();
            Video videoIdNew = itemVideo.getVideoId();
            List<String> illegalOrphanMessages = null;
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                ItemVideo oldItemVideoOfItemId = itemIdNew.getItemVideo();
                if (oldItemVideoOfItemId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Item " + itemIdNew + " already has an item of type ItemVideo whose itemId column cannot be null. Please make another selection for the itemId field.");
                }
            }
            if (videoIdNew != null && !videoIdNew.equals(videoIdOld)) {
                ItemVideo oldItemVideoOfVideoId = videoIdNew.getItemVideo();
                if (oldItemVideoOfVideoId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Video " + videoIdNew + " already has an item of type ItemVideo whose videoId column cannot be null. Please make another selection for the videoId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemIdNew != null) {
                itemIdNew = em.getReference(itemIdNew.getClass(), itemIdNew.getItemId());
                itemVideo.setItemId(itemIdNew);
            }
            if (videoIdNew != null) {
                videoIdNew = em.getReference(videoIdNew.getClass(), videoIdNew.getVideoId());
                itemVideo.setVideoId(videoIdNew);
            }
            itemVideo = em.merge(itemVideo);
            if (itemIdOld != null && !itemIdOld.equals(itemIdNew)) {
                itemIdOld.setItemVideo(null);
                itemIdOld = em.merge(itemIdOld);
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                itemIdNew.setItemVideo(itemVideo);
                itemIdNew = em.merge(itemIdNew);
            }
            if (videoIdOld != null && !videoIdOld.equals(videoIdNew)) {
                videoIdOld.setItemVideo(null);
                videoIdOld = em.merge(videoIdOld);
            }
            if (videoIdNew != null && !videoIdNew.equals(videoIdOld)) {
                videoIdNew.setItemVideo(itemVideo);
                videoIdNew = em.merge(videoIdNew);
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
                Integer id = itemVideo.getItemVideoId();
                if (findItemVideo(id) == null) {
                    throw new NonexistentEntityException("The itemVideo with id " + id + " no longer exists.");
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
            ItemVideo itemVideo;
            try {
                itemVideo = em.getReference(ItemVideo.class, id);
                itemVideo.getItemVideoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemVideo with id " + id + " no longer exists.", enfe);
            }
            Item itemId = itemVideo.getItemId();
            if (itemId != null) {
                itemId.setItemVideo(null);
                itemId = em.merge(itemId);
            }
            Video videoId = itemVideo.getVideoId();
            if (videoId != null) {
                videoId.setItemVideo(null);
                videoId = em.merge(videoId);
            }
            em.remove(itemVideo);
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

    public List<ItemVideo> findItemVideoEntities() {
        return findItemVideoEntities(true, -1, -1);
    }

    public List<ItemVideo> findItemVideoEntities(int maxResults, int firstResult) {
        return findItemVideoEntities(false, maxResults, firstResult);
    }

    private List<ItemVideo> findItemVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemVideo.class));
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

    public ItemVideo findItemVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemVideo.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemVideo> rt = cq.from(ItemVideo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
