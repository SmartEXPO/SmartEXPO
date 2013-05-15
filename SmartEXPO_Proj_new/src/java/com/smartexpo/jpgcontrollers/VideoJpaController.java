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
public class VideoJpaController implements Serializable {

    public VideoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Video video) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemVideo itemVideo = video.getItemVideo();
            if (itemVideo != null) {
                itemVideo = em.getReference(itemVideo.getClass(), itemVideo.getItemVideoId());
                video.setItemVideo(itemVideo);
            }
            em.persist(video);
            if (itemVideo != null) {
                Video oldVideoIdOfItemVideo = itemVideo.getVideoId();
                if (oldVideoIdOfItemVideo != null) {
                    oldVideoIdOfItemVideo.setItemVideo(null);
                    oldVideoIdOfItemVideo = em.merge(oldVideoIdOfItemVideo);
                }
                itemVideo.setVideoId(video);
                itemVideo = em.merge(itemVideo);
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

    public void edit(Video video) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Video persistentVideo = em.find(Video.class, video.getVideoId());
            ItemVideo itemVideoOld = persistentVideo.getItemVideo();
            ItemVideo itemVideoNew = video.getItemVideo();
            List<String> illegalOrphanMessages = null;
            if (itemVideoOld != null && !itemVideoOld.equals(itemVideoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemVideo " + itemVideoOld + " since its videoId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemVideoNew != null) {
                itemVideoNew = em.getReference(itemVideoNew.getClass(), itemVideoNew.getItemVideoId());
                video.setItemVideo(itemVideoNew);
            }
            video = em.merge(video);
            if (itemVideoNew != null && !itemVideoNew.equals(itemVideoOld)) {
                Video oldVideoIdOfItemVideo = itemVideoNew.getVideoId();
                if (oldVideoIdOfItemVideo != null) {
                    oldVideoIdOfItemVideo.setItemVideo(null);
                    oldVideoIdOfItemVideo = em.merge(oldVideoIdOfItemVideo);
                }
                itemVideoNew.setVideoId(video);
                itemVideoNew = em.merge(itemVideoNew);
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
                Integer id = video.getVideoId();
                if (findVideo(id) == null) {
                    throw new NonexistentEntityException("The video with id " + id + " no longer exists.");
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
            Video video;
            try {
                video = em.getReference(Video.class, id);
                video.getVideoId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The video with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ItemVideo itemVideoOrphanCheck = video.getItemVideo();
            if (itemVideoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Video (" + video + ") cannot be destroyed since the ItemVideo " + itemVideoOrphanCheck + " in its itemVideo field has a non-nullable videoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(video);
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

    public List<Video> findVideoEntities() {
        return findVideoEntities(true, -1, -1);
    }

    public List<Video> findVideoEntities(int maxResults, int firstResult) {
        return findVideoEntities(false, maxResults, firstResult);
    }

    private List<Video> findVideoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Video.class));
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

    public Video findVideo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Video.class, id);
        } finally {
            em.close();
        }
    }

    public int getVideoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Video> rt = cq.from(Video.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
