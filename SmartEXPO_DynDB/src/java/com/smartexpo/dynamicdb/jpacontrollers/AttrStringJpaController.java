/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.dynamicdb.jpacontrollers;

import com.smartexpo.dynamicdb.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.dynamicdb.jpacontrollers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dynamicdb.models.Attr;
import com.smartexpo.dynamicdb.models.AttrString;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AttrStringJpaController implements Serializable {

    public AttrStringJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttrString attrString) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Attr attrStringRefOrphanCheck = attrString.getAttrStringRef();
        if (attrStringRefOrphanCheck != null) {
            AttrString oldAttrStringOfAttrStringRef = attrStringRefOrphanCheck.getAttrString();
            if (oldAttrStringOfAttrStringRef != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attr " + attrStringRefOrphanCheck + " already has an item of type AttrString whose attrStringRef column cannot be null. Please make another selection for the attrStringRef field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attrStringRef = attrString.getAttrStringRef();
            if (attrStringRef != null) {
                attrStringRef = em.getReference(attrStringRef.getClass(), attrStringRef.getAttrId());
                attrString.setAttrStringRef(attrStringRef);
            }
            em.persist(attrString);
            if (attrStringRef != null) {
                attrStringRef.setAttrString(attrString);
                attrStringRef = em.merge(attrStringRef);
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

    public void edit(AttrString attrString) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrString persistentAttrString = em.find(AttrString.class, attrString.getAttrStringId());
            Attr attrStringRefOld = persistentAttrString.getAttrStringRef();
            Attr attrStringRefNew = attrString.getAttrStringRef();
            List<String> illegalOrphanMessages = null;
            if (attrStringRefNew != null && !attrStringRefNew.equals(attrStringRefOld)) {
                AttrString oldAttrStringOfAttrStringRef = attrStringRefNew.getAttrString();
                if (oldAttrStringOfAttrStringRef != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attr " + attrStringRefNew + " already has an item of type AttrString whose attrStringRef column cannot be null. Please make another selection for the attrStringRef field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrStringRefNew != null) {
                attrStringRefNew = em.getReference(attrStringRefNew.getClass(), attrStringRefNew.getAttrId());
                attrString.setAttrStringRef(attrStringRefNew);
            }
            attrString = em.merge(attrString);
            if (attrStringRefOld != null && !attrStringRefOld.equals(attrStringRefNew)) {
                attrStringRefOld.setAttrString(null);
                attrStringRefOld = em.merge(attrStringRefOld);
            }
            if (attrStringRefNew != null && !attrStringRefNew.equals(attrStringRefOld)) {
                attrStringRefNew.setAttrString(attrString);
                attrStringRefNew = em.merge(attrStringRefNew);
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
                Integer id = attrString.getAttrStringId();
                if (findAttrString(id) == null) {
                    throw new NonexistentEntityException("The attrString with id " + id + " no longer exists.");
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
            AttrString attrString;
            try {
                attrString = em.getReference(AttrString.class, id);
                attrString.getAttrStringId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attrString with id " + id + " no longer exists.", enfe);
            }
            Attr attrStringRef = attrString.getAttrStringRef();
            if (attrStringRef != null) {
                attrStringRef.setAttrString(null);
                attrStringRef = em.merge(attrStringRef);
            }
            em.remove(attrString);
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

    public List<AttrString> findAttrStringEntities() {
        return findAttrStringEntities(true, -1, -1);
    }

    public List<AttrString> findAttrStringEntities(int maxResults, int firstResult) {
        return findAttrStringEntities(false, maxResults, firstResult);
    }

    private List<AttrString> findAttrStringEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttrString.class));
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

    public AttrString findAttrString(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttrString.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrStringCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttrString> rt = cq.from(AttrString.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
