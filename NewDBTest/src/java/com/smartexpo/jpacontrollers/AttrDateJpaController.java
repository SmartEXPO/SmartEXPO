/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpacontrollers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dbproto.Attr;
import com.smartexpo.dbproto.AttrDate;
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
public class AttrDateJpaController implements Serializable {

    public AttrDateJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttrDate attrDate) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Attr attrDateRefOrphanCheck = attrDate.getAttrDateRef();
        if (attrDateRefOrphanCheck != null) {
            AttrDate oldAttrDateOfAttrDateRef = attrDateRefOrphanCheck.getAttrDate();
            if (oldAttrDateOfAttrDateRef != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attr " + attrDateRefOrphanCheck + " already has an item of type AttrDate whose attrDateRef column cannot be null. Please make another selection for the attrDateRef field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attrDateRef = attrDate.getAttrDateRef();
            if (attrDateRef != null) {
                attrDateRef = em.getReference(attrDateRef.getClass(), attrDateRef.getAttrId());
                attrDate.setAttrDateRef(attrDateRef);
            }
            em.persist(attrDate);
            if (attrDateRef != null) {
                attrDateRef.setAttrDate(attrDate);
                attrDateRef = em.merge(attrDateRef);
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

    public void edit(AttrDate attrDate) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrDate persistentAttrDate = em.find(AttrDate.class, attrDate.getAttrDateId());
            Attr attrDateRefOld = persistentAttrDate.getAttrDateRef();
            Attr attrDateRefNew = attrDate.getAttrDateRef();
            List<String> illegalOrphanMessages = null;
            if (attrDateRefNew != null && !attrDateRefNew.equals(attrDateRefOld)) {
                AttrDate oldAttrDateOfAttrDateRef = attrDateRefNew.getAttrDate();
                if (oldAttrDateOfAttrDateRef != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attr " + attrDateRefNew + " already has an item of type AttrDate whose attrDateRef column cannot be null. Please make another selection for the attrDateRef field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrDateRefNew != null) {
                attrDateRefNew = em.getReference(attrDateRefNew.getClass(), attrDateRefNew.getAttrId());
                attrDate.setAttrDateRef(attrDateRefNew);
            }
            attrDate = em.merge(attrDate);
            if (attrDateRefOld != null && !attrDateRefOld.equals(attrDateRefNew)) {
                attrDateRefOld.setAttrDate(null);
                attrDateRefOld = em.merge(attrDateRefOld);
            }
            if (attrDateRefNew != null && !attrDateRefNew.equals(attrDateRefOld)) {
                attrDateRefNew.setAttrDate(attrDate);
                attrDateRefNew = em.merge(attrDateRefNew);
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
                Integer id = attrDate.getAttrDateId();
                if (findAttrDate(id) == null) {
                    throw new NonexistentEntityException("The attrDate with id " + id + " no longer exists.");
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
            AttrDate attrDate;
            try {
                attrDate = em.getReference(AttrDate.class, id);
                attrDate.getAttrDateId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attrDate with id " + id + " no longer exists.", enfe);
            }
            Attr attrDateRef = attrDate.getAttrDateRef();
            if (attrDateRef != null) {
                attrDateRef.setAttrDate(null);
                attrDateRef = em.merge(attrDateRef);
            }
            em.remove(attrDate);
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

    public List<AttrDate> findAttrDateEntities() {
        return findAttrDateEntities(true, -1, -1);
    }

    public List<AttrDate> findAttrDateEntities(int maxResults, int firstResult) {
        return findAttrDateEntities(false, maxResults, firstResult);
    }

    private List<AttrDate> findAttrDateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttrDate.class));
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

    public AttrDate findAttrDate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttrDate.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrDateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttrDate> rt = cq.from(AttrDate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
