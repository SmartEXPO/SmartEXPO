/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Audio;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class AudioJpaController1 implements Serializable {

    public AudioJpaController1(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Audio audio) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemAudio itemAudio = audio.getItemAudio();
            if (itemAudio != null) {
                itemAudio = em.getReference(itemAudio.getClass(), itemAudio.getItemAudioId());
                audio.setItemAudio(itemAudio);
            }
            em.persist(audio);
            if (itemAudio != null) {
                Audio oldAudioIdOfItemAudio = itemAudio.getAudioId();
                if (oldAudioIdOfItemAudio != null) {
                    oldAudioIdOfItemAudio.setItemAudio(null);
                    oldAudioIdOfItemAudio = em.merge(oldAudioIdOfItemAudio);
                }
                itemAudio.setAudioId(audio);
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

    public void edit(Audio audio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Audio persistentAudio = em.find(Audio.class, audio.getAudioId());
            ItemAudio itemAudioOld = persistentAudio.getItemAudio();
            ItemAudio itemAudioNew = audio.getItemAudio();
            List<String> illegalOrphanMessages = null;
            if (itemAudioOld != null && !itemAudioOld.equals(itemAudioNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemAudio " + itemAudioOld + " since its audioId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemAudioNew != null) {
                itemAudioNew = em.getReference(itemAudioNew.getClass(), itemAudioNew.getItemAudioId());
                audio.setItemAudio(itemAudioNew);
            }
            audio = em.merge(audio);
            if (itemAudioNew != null && !itemAudioNew.equals(itemAudioOld)) {
                Audio oldAudioIdOfItemAudio = itemAudioNew.getAudioId();
                if (oldAudioIdOfItemAudio != null) {
                    oldAudioIdOfItemAudio.setItemAudio(null);
                    oldAudioIdOfItemAudio = em.merge(oldAudioIdOfItemAudio);
                }
                itemAudioNew.setAudioId(audio);
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
                Integer id = audio.getAudioId();
                if (findAudio(id) == null) {
                    throw new NonexistentEntityException("The audio with id " + id + " no longer exists.");
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
            Audio audio;
            try {
                audio = em.getReference(Audio.class, id);
                audio.getAudioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The audio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ItemAudio itemAudioOrphanCheck = audio.getItemAudio();
            if (itemAudioOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Audio (" + audio + ") cannot be destroyed since the ItemAudio " + itemAudioOrphanCheck + " in its itemAudio field has a non-nullable audioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(audio);
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

    public List<Audio> findAudioEntities() {
        return findAudioEntities(true, -1, -1);
    }

    public List<Audio> findAudioEntities(int maxResults, int firstResult) {
        return findAudioEntities(false, maxResults, firstResult);
    }

    private List<Audio> findAudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Audio.class));
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

    public Audio findAudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Audio.class, id);
        } finally {
            em.close();
        }
    }

    public int getAudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Audio> rt = cq.from(Audio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
