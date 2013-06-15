/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.jpacontrollers;

import com.smartexpo.dynamicdb.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.RollbackFailureException;
import com.smartexpo.dynamicdb.models.AgAg;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dynamicdb.models.AttrGroup;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AgAgJpaController implements Serializable {

    public AgAgJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AgAg agAg) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        AttrGroup agChildIdOrphanCheck = agAg.getAgChildId();
        if (agChildIdOrphanCheck != null) {
            AgAg oldAgAg_toParentOfAgChildId = agChildIdOrphanCheck.getAgAg_toParent();
            if (oldAgAg_toParentOfAgChildId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The AttrGroup " + agChildIdOrphanCheck + " already has an item of type AgAg whose agChildId column cannot be null. Please make another selection for the agChildId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrGroup agChildId = agAg.getAgChildId();
            if (agChildId != null) {
                agChildId = em.getReference(agChildId.getClass(), agChildId.getAttrGroupId());
                agAg.setAgChildId(agChildId);
            }
            em.persist(agAg);
            if (agChildId != null) {
                agChildId.setAgAg_toParent(agAg);
                agChildId = em.merge(agChildId);
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

    public void edit(AgAg agAg) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AgAg persistentAgAg = em.find(AgAg.class, agAg.getAgAgId());
            AttrGroup agChildIdOld = persistentAgAg.getAgChildId();
            AttrGroup agChildIdNew = agAg.getAgChildId();
            List<String> illegalOrphanMessages = null;
            if (agChildIdNew != null && !agChildIdNew.equals(agChildIdOld)) {
                AgAg oldAgAg_toParentOfAgChildId = agChildIdNew.getAgAg_toParent();
                if (oldAgAg_toParentOfAgChildId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The AttrGroup " + agChildIdNew + " already has an item of type AgAg whose agChildId column cannot be null. Please make another selection for the agChildId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (agChildIdNew != null) {
                agChildIdNew = em.getReference(agChildIdNew.getClass(), agChildIdNew.getAttrGroupId());
                agAg.setAgChildId(agChildIdNew);
            }
            agAg = em.merge(agAg);
            if (agChildIdOld != null && !agChildIdOld.equals(agChildIdNew)) {
                agChildIdOld.setAgAg_toParent(null);
                agChildIdOld = em.merge(agChildIdOld);
            }
            if (agChildIdNew != null && !agChildIdNew.equals(agChildIdOld)) {
                agChildIdNew.setAgAg_toParent(agAg);
                agChildIdNew = em.merge(agChildIdNew);
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
                Integer id = agAg.getAgAgId();
                if (findAgAg(id) == null) {
                    throw new NonexistentEntityException("The agAg with id " + id + " no longer exists.");
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
            AgAg agAg;
            try {
                agAg = em.getReference(AgAg.class, id);
                agAg.getAgAgId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agAg with id " + id + " no longer exists.", enfe);
            }
            AttrGroup agChildId = agAg.getAgChildId();
            if (agChildId != null) {
                agChildId.setAgAg_toParent(null);
                agChildId = em.merge(agChildId);
            }
            em.remove(agAg);
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

    public List<AgAg> findAgAgEntities() {
        return findAgAgEntities(true, -1, -1);
    }

    public List<AgAg> findAgAgEntities(int maxResults, int firstResult) {
        return findAgAgEntities(false, maxResults, firstResult);
    }

    private List<AgAg> findAgAgEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AgAg.class));
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

    public AgAg findAgAg(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AgAg.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgAgCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AgAg> rt = cq.from(AgAg.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
