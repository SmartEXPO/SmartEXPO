/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpacontrollers;

import com.smartexpo.dbproto.AgAttr;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dbproto.Attr;
import com.smartexpo.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpacontrollers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AgAttrJpaController implements Serializable {

    public AgAttrJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AgAttr agAttr) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Attr attrIdOrphanCheck = agAttr.getAttrId();
        if (attrIdOrphanCheck != null) {
            AgAttr oldAgAttrOfAttrId = attrIdOrphanCheck.getAgAttr();
            if (oldAgAttrOfAttrId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attr " + attrIdOrphanCheck + " already has an item of type AgAttr whose attrId column cannot be null. Please make another selection for the attrId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attrId = agAttr.getAttrId();
            if (attrId != null) {
                attrId = em.getReference(attrId.getClass(), attrId.getAttrId());
                agAttr.setAttrId(attrId);
            }
            em.persist(agAttr);
            if (attrId != null) {
                attrId.setAgAttr(agAttr);
                attrId = em.merge(attrId);
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

    public void edit(AgAttr agAttr) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AgAttr persistentAgAttr = em.find(AgAttr.class, agAttr.getAgAttrId());
            Attr attrIdOld = persistentAgAttr.getAttrId();
            Attr attrIdNew = agAttr.getAttrId();
            List<String> illegalOrphanMessages = null;
            if (attrIdNew != null && !attrIdNew.equals(attrIdOld)) {
                AgAttr oldAgAttrOfAttrId = attrIdNew.getAgAttr();
                if (oldAgAttrOfAttrId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attr " + attrIdNew + " already has an item of type AgAttr whose attrId column cannot be null. Please make another selection for the attrId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrIdNew != null) {
                attrIdNew = em.getReference(attrIdNew.getClass(), attrIdNew.getAttrId());
                agAttr.setAttrId(attrIdNew);
            }
            agAttr = em.merge(agAttr);
            if (attrIdOld != null && !attrIdOld.equals(attrIdNew)) {
                attrIdOld.setAgAttr(null);
                attrIdOld = em.merge(attrIdOld);
            }
            if (attrIdNew != null && !attrIdNew.equals(attrIdOld)) {
                attrIdNew.setAgAttr(agAttr);
                attrIdNew = em.merge(attrIdNew);
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
                Integer id = agAttr.getAgAttrId();
                if (findAgAttr(id) == null) {
                    throw new NonexistentEntityException("The agAttr with id " + id + " no longer exists.");
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
            AgAttr agAttr;
            try {
                agAttr = em.getReference(AgAttr.class, id);
                agAttr.getAgAttrId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The agAttr with id " + id + " no longer exists.", enfe);
            }
            Attr attrId = agAttr.getAttrId();
            if (attrId != null) {
                attrId.setAgAttr(null);
                attrId = em.merge(attrId);
            }
            em.remove(agAttr);
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

    public List<AgAttr> findAgAttrEntities() {
        return findAgAttrEntities(true, -1, -1);
    }

    public List<AgAttr> findAgAttrEntities(int maxResults, int firstResult) {
        return findAgAttrEntities(false, maxResults, firstResult);
    }

    private List<AgAttr> findAgAttrEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AgAttr.class));
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

    public AgAttr findAgAttr(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AgAttr.class, id);
        } finally {
            em.close();
        }
    }

    public int getAgAttrCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AgAttr> rt = cq.from(AgAttr.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
