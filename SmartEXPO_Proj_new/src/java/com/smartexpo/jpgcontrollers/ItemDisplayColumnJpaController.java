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
import com.smartexpo.models.DisplayColumn;
import com.smartexpo.models.ItemDisplayColumn;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ItemDisplayColumnJpaController implements Serializable {

    public ItemDisplayColumnJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemDisplayColumn itemDisplayColumn) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        DisplayColumn displayColumnIdOrphanCheck = itemDisplayColumn.getDisplayColumnId();
        if (displayColumnIdOrphanCheck != null) {
            ItemDisplayColumn oldItemDisplayColumnOfDisplayColumnId = displayColumnIdOrphanCheck.getItemDisplayColumn();
            if (oldItemDisplayColumnOfDisplayColumnId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The DisplayColumn " + displayColumnIdOrphanCheck + " already has an item of type ItemDisplayColumn whose displayColumnId column cannot be null. Please make another selection for the displayColumnId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DisplayColumn displayColumnId = itemDisplayColumn.getDisplayColumnId();
            if (displayColumnId != null) {
                displayColumnId = em.getReference(displayColumnId.getClass(), displayColumnId.getDisplayColumnId());
                itemDisplayColumn.setDisplayColumnId(displayColumnId);
            }
            em.persist(itemDisplayColumn);
            if (displayColumnId != null) {
                displayColumnId.setItemDisplayColumn(itemDisplayColumn);
                displayColumnId = em.merge(displayColumnId);
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

    public void edit(ItemDisplayColumn itemDisplayColumn) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemDisplayColumn persistentItemDisplayColumn = em.find(ItemDisplayColumn.class, itemDisplayColumn.getItemDisplayColumnId());
            DisplayColumn displayColumnIdOld = persistentItemDisplayColumn.getDisplayColumnId();
            DisplayColumn displayColumnIdNew = itemDisplayColumn.getDisplayColumnId();
            List<String> illegalOrphanMessages = null;
            if (displayColumnIdNew != null && !displayColumnIdNew.equals(displayColumnIdOld)) {
                ItemDisplayColumn oldItemDisplayColumnOfDisplayColumnId = displayColumnIdNew.getItemDisplayColumn();
                if (oldItemDisplayColumnOfDisplayColumnId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The DisplayColumn " + displayColumnIdNew + " already has an item of type ItemDisplayColumn whose displayColumnId column cannot be null. Please make another selection for the displayColumnId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (displayColumnIdNew != null) {
                displayColumnIdNew = em.getReference(displayColumnIdNew.getClass(), displayColumnIdNew.getDisplayColumnId());
                itemDisplayColumn.setDisplayColumnId(displayColumnIdNew);
            }
            itemDisplayColumn = em.merge(itemDisplayColumn);
            if (displayColumnIdOld != null && !displayColumnIdOld.equals(displayColumnIdNew)) {
                displayColumnIdOld.setItemDisplayColumn(null);
                displayColumnIdOld = em.merge(displayColumnIdOld);
            }
            if (displayColumnIdNew != null && !displayColumnIdNew.equals(displayColumnIdOld)) {
                displayColumnIdNew.setItemDisplayColumn(itemDisplayColumn);
                displayColumnIdNew = em.merge(displayColumnIdNew);
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
                Integer id = itemDisplayColumn.getItemDisplayColumnId();
                if (findItemDisplayColumn(id) == null) {
                    throw new NonexistentEntityException("The itemDisplayColumn with id " + id + " no longer exists.");
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
            ItemDisplayColumn itemDisplayColumn;
            try {
                itemDisplayColumn = em.getReference(ItemDisplayColumn.class, id);
                itemDisplayColumn.getItemDisplayColumnId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemDisplayColumn with id " + id + " no longer exists.", enfe);
            }
            DisplayColumn displayColumnId = itemDisplayColumn.getDisplayColumnId();
            if (displayColumnId != null) {
                displayColumnId.setItemDisplayColumn(null);
                displayColumnId = em.merge(displayColumnId);
            }
            em.remove(itemDisplayColumn);
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

    public List<ItemDisplayColumn> findItemDisplayColumnEntities() {
        return findItemDisplayColumnEntities(true, -1, -1);
    }

    public List<ItemDisplayColumn> findItemDisplayColumnEntities(int maxResults, int firstResult) {
        return findItemDisplayColumnEntities(false, maxResults, firstResult);
    }

    private List<ItemDisplayColumn> findItemDisplayColumnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemDisplayColumn.class));
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

    public ItemDisplayColumn findItemDisplayColumn(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemDisplayColumn.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemDisplayColumnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemDisplayColumn> rt = cq.from(ItemDisplayColumn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
