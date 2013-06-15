/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Description;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.models.Item;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class DescriptionJpaController implements Serializable {

    public DescriptionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Description description) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Item itemIdOrphanCheck = description.getItemId();
        if (itemIdOrphanCheck != null) {
            Description oldDescriptionOfItemId = itemIdOrphanCheck.getDescription();
            if (oldDescriptionOfItemId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Item " + itemIdOrphanCheck + " already has an item of type Description whose itemId column cannot be null. Please make another selection for the itemId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item itemId = description.getItemId();
            if (itemId != null) {
                itemId = em.getReference(itemId.getClass(), itemId.getItemId());
                description.setItemId(itemId);
            }
            em.persist(description);
            if (itemId != null) {
                itemId.setDescription(description);
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

    public void edit(Description description) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Description persistentDescription = em.find(Description.class, description.getDescriptionId());
            Item itemIdOld = persistentDescription.getItemId();
            Item itemIdNew = description.getItemId();
            List<String> illegalOrphanMessages = null;
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                Description oldDescriptionOfItemId = itemIdNew.getDescription();
                if (oldDescriptionOfItemId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Item " + itemIdNew + " already has an item of type Description whose itemId column cannot be null. Please make another selection for the itemId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemIdNew != null) {
                itemIdNew = em.getReference(itemIdNew.getClass(), itemIdNew.getItemId());
                description.setItemId(itemIdNew);
            }
            description = em.merge(description);
            if (itemIdOld != null && !itemIdOld.equals(itemIdNew)) {
                itemIdOld.setDescription(null);
                itemIdOld = em.merge(itemIdOld);
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                itemIdNew.setDescription(description);
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
                Integer id = description.getDescriptionId();
                if (findDescription(id) == null) {
                    throw new NonexistentEntityException("The description with id " + id + " no longer exists.");
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
            Description description;
            try {
                description = em.getReference(Description.class, id);
                description.getDescriptionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The description with id " + id + " no longer exists.", enfe);
            }
            Item itemId = description.getItemId();
            if (itemId != null) {
                itemId.setDescription(null);
                itemId = em.merge(itemId);
            }
            em.remove(description);
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

    public List<Description> findDescriptionEntities() {
        return findDescriptionEntities(true, -1, -1);
    }

    public List<Description> findDescriptionEntities(int maxResults, int firstResult) {
        return findDescriptionEntities(false, maxResults, firstResult);
    }

    private List<Description> findDescriptionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Description.class));
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

    public Description findDescription(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Description.class, id);
        } finally {
            em.close();
        }
    }

    public int getDescriptionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Description> rt = cq.from(Description.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
