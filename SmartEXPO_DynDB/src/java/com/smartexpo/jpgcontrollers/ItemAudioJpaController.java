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
import com.smartexpo.models.Audio;
import com.smartexpo.models.Item;
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
public class ItemAudioJpaController implements Serializable {

    public ItemAudioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemAudio itemAudio) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Audio audioIdOrphanCheck = itemAudio.getAudioId();
        if (audioIdOrphanCheck != null) {
            ItemAudio oldItemAudioOfAudioId = audioIdOrphanCheck.getItemAudio();
            if (oldItemAudioOfAudioId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Audio " + audioIdOrphanCheck + " already has an item of type ItemAudio whose audioId column cannot be null. Please make another selection for the audioId field.");
            }
        }
        Item itemIdOrphanCheck = itemAudio.getItemId();
        if (itemIdOrphanCheck != null) {
            ItemAudio oldItemAudioOfItemId = itemIdOrphanCheck.getItemAudio();
            if (oldItemAudioOfItemId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Item " + itemIdOrphanCheck + " already has an item of type ItemAudio whose itemId column cannot be null. Please make another selection for the itemId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Audio audioId = itemAudio.getAudioId();
            if (audioId != null) {
                audioId = em.getReference(audioId.getClass(), audioId.getAudioId());
                itemAudio.setAudioId(audioId);
            }
            Item itemId = itemAudio.getItemId();
            if (itemId != null) {
                itemId = em.getReference(itemId.getClass(), itemId.getItemId());
                itemAudio.setItemId(itemId);
            }
            em.persist(itemAudio);
            if (audioId != null) {
                audioId.setItemAudio(itemAudio);
                audioId = em.merge(audioId);
            }
            if (itemId != null) {
                itemId.setItemAudio(itemAudio);
                itemId = em.merge(itemId);
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

    public void edit(ItemAudio itemAudio) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemAudio persistentItemAudio = em.find(ItemAudio.class, itemAudio.getItemAudioId());
            Audio audioIdOld = persistentItemAudio.getAudioId();
            Audio audioIdNew = itemAudio.getAudioId();
            Item itemIdOld = persistentItemAudio.getItemId();
            Item itemIdNew = itemAudio.getItemId();
            List<String> illegalOrphanMessages = null;
            if (audioIdNew != null && !audioIdNew.equals(audioIdOld)) {
                ItemAudio oldItemAudioOfAudioId = audioIdNew.getItemAudio();
                if (oldItemAudioOfAudioId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Audio " + audioIdNew + " already has an item of type ItemAudio whose audioId column cannot be null. Please make another selection for the audioId field.");
                }
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                ItemAudio oldItemAudioOfItemId = itemIdNew.getItemAudio();
                if (oldItemAudioOfItemId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Item " + itemIdNew + " already has an item of type ItemAudio whose itemId column cannot be null. Please make another selection for the itemId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (audioIdNew != null) {
                audioIdNew = em.getReference(audioIdNew.getClass(), audioIdNew.getAudioId());
                itemAudio.setAudioId(audioIdNew);
            }
            if (itemIdNew != null) {
                itemIdNew = em.getReference(itemIdNew.getClass(), itemIdNew.getItemId());
                itemAudio.setItemId(itemIdNew);
            }
            itemAudio = em.merge(itemAudio);
            if (audioIdOld != null && !audioIdOld.equals(audioIdNew)) {
                audioIdOld.setItemAudio(null);
                audioIdOld = em.merge(audioIdOld);
            }
            if (audioIdNew != null && !audioIdNew.equals(audioIdOld)) {
                audioIdNew.setItemAudio(itemAudio);
                audioIdNew = em.merge(audioIdNew);
            }
            if (itemIdOld != null && !itemIdOld.equals(itemIdNew)) {
                itemIdOld.setItemAudio(null);
                itemIdOld = em.merge(itemIdOld);
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                itemIdNew.setItemAudio(itemAudio);
                itemIdNew = em.merge(itemIdNew);
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
                Integer id = itemAudio.getItemAudioId();
                if (findItemAudio(id) == null) {
                    throw new NonexistentEntityException("The itemAudio with id " + id + " no longer exists.");
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
            ItemAudio itemAudio;
            try {
                itemAudio = em.getReference(ItemAudio.class, id);
                itemAudio.getItemAudioId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemAudio with id " + id + " no longer exists.", enfe);
            }
            Audio audioId = itemAudio.getAudioId();
            if (audioId != null) {
                audioId.setItemAudio(null);
                audioId = em.merge(audioId);
            }
            Item itemId = itemAudio.getItemId();
            if (itemId != null) {
                itemId.setItemAudio(null);
                itemId = em.merge(itemId);
            }
            em.remove(itemAudio);
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

    public List<ItemAudio> findItemAudioEntities() {
        return findItemAudioEntities(true, -1, -1);
    }

    public List<ItemAudio> findItemAudioEntities(int maxResults, int firstResult) {
        return findItemAudioEntities(false, maxResults, firstResult);
    }

    private List<ItemAudio> findItemAudioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemAudio.class));
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

    public ItemAudio findItemAudio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemAudio.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemAudioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemAudio> rt = cq.from(ItemAudio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
