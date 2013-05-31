/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.DisplayColumn;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class DisplayColumnJpaController implements Serializable {

    public DisplayColumnJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DisplayColumn displayColumn) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemDisplayColumn itemDisplayColumn = displayColumn.getItemDisplayColumn();
            if (itemDisplayColumn != null) {
                itemDisplayColumn = em.getReference(itemDisplayColumn.getClass(), itemDisplayColumn.getItemDisplayColumnId());
                displayColumn.setItemDisplayColumn(itemDisplayColumn);
            }
            em.persist(displayColumn);
            if (itemDisplayColumn != null) {
                DisplayColumn oldDisplayColumnIdOfItemDisplayColumn = itemDisplayColumn.getDisplayColumnId();
                if (oldDisplayColumnIdOfItemDisplayColumn != null) {
                    oldDisplayColumnIdOfItemDisplayColumn.setItemDisplayColumn(null);
                    oldDisplayColumnIdOfItemDisplayColumn = em.merge(oldDisplayColumnIdOfItemDisplayColumn);
                }
                itemDisplayColumn.setDisplayColumnId(displayColumn);
                itemDisplayColumn = em.merge(itemDisplayColumn);
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

    public void edit(DisplayColumn displayColumn) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DisplayColumn persistentDisplayColumn = em.find(DisplayColumn.class, displayColumn.getDisplayColumnId());
            ItemDisplayColumn itemDisplayColumnOld = persistentDisplayColumn.getItemDisplayColumn();
            ItemDisplayColumn itemDisplayColumnNew = displayColumn.getItemDisplayColumn();
            List<String> illegalOrphanMessages = null;
            if (itemDisplayColumnOld != null && !itemDisplayColumnOld.equals(itemDisplayColumnNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemDisplayColumn " + itemDisplayColumnOld + " since its displayColumnId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemDisplayColumnNew != null) {
                itemDisplayColumnNew = em.getReference(itemDisplayColumnNew.getClass(), itemDisplayColumnNew.getItemDisplayColumnId());
                displayColumn.setItemDisplayColumn(itemDisplayColumnNew);
            }
            displayColumn = em.merge(displayColumn);
            if (itemDisplayColumnNew != null && !itemDisplayColumnNew.equals(itemDisplayColumnOld)) {
                DisplayColumn oldDisplayColumnIdOfItemDisplayColumn = itemDisplayColumnNew.getDisplayColumnId();
                if (oldDisplayColumnIdOfItemDisplayColumn != null) {
                    oldDisplayColumnIdOfItemDisplayColumn.setItemDisplayColumn(null);
                    oldDisplayColumnIdOfItemDisplayColumn = em.merge(oldDisplayColumnIdOfItemDisplayColumn);
                }
                itemDisplayColumnNew.setDisplayColumnId(displayColumn);
                itemDisplayColumnNew = em.merge(itemDisplayColumnNew);
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
                Integer id = displayColumn.getDisplayColumnId();
                if (findDisplayColumn(id) == null) {
                    throw new NonexistentEntityException("The displayColumn with id " + id + " no longer exists.");
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
            DisplayColumn displayColumn;
            try {
                displayColumn = em.getReference(DisplayColumn.class, id);
                displayColumn.getDisplayColumnId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The displayColumn with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ItemDisplayColumn itemDisplayColumnOrphanCheck = displayColumn.getItemDisplayColumn();
            if (itemDisplayColumnOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This DisplayColumn (" + displayColumn + ") cannot be destroyed since the ItemDisplayColumn " + itemDisplayColumnOrphanCheck + " in its itemDisplayColumn field has a non-nullable displayColumnId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(displayColumn);
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

    public List<DisplayColumn> findDisplayColumnEntities() {
        return findDisplayColumnEntities(true, -1, -1);
    }

    public List<DisplayColumn> findDisplayColumnEntities(int maxResults, int firstResult) {
        return findDisplayColumnEntities(false, maxResults, firstResult);
    }

    private List<DisplayColumn> findDisplayColumnEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DisplayColumn.class));
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

    public DisplayColumn findDisplayColumn(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DisplayColumn.class, id);
        } finally {
            em.close();
        }
    }

    public int getDisplayColumnCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DisplayColumn> rt = cq.from(DisplayColumn.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
