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
import com.smartexpo.dynamicdb.models.AttrInt;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AttrIntJpaController implements Serializable {

    public AttrIntJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttrInt attrInt) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Attr attrIntRefOrphanCheck = attrInt.getAttrIntRef();
        if (attrIntRefOrphanCheck != null) {
            AttrInt oldAttrIntOfAttrIntRef = attrIntRefOrphanCheck.getAttrInt();
            if (oldAttrIntOfAttrIntRef != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attr " + attrIntRefOrphanCheck + " already has an item of type AttrInt whose attrIntRef column cannot be null. Please make another selection for the attrIntRef field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attrIntRef = attrInt.getAttrIntRef();
            if (attrIntRef != null) {
                attrIntRef = em.getReference(attrIntRef.getClass(), attrIntRef.getAttrId());
                attrInt.setAttrIntRef(attrIntRef);
            }
            em.persist(attrInt);
            if (attrIntRef != null) {
                attrIntRef.setAttrInt(attrInt);
                attrIntRef = em.merge(attrIntRef);
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

    public void edit(AttrInt attrInt) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrInt persistentAttrInt = em.find(AttrInt.class, attrInt.getAttrIntId());
            Attr attrIntRefOld = persistentAttrInt.getAttrIntRef();
            Attr attrIntRefNew = attrInt.getAttrIntRef();
            List<String> illegalOrphanMessages = null;
            if (attrIntRefNew != null && !attrIntRefNew.equals(attrIntRefOld)) {
                AttrInt oldAttrIntOfAttrIntRef = attrIntRefNew.getAttrInt();
                if (oldAttrIntOfAttrIntRef != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attr " + attrIntRefNew + " already has an item of type AttrInt whose attrIntRef column cannot be null. Please make another selection for the attrIntRef field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrIntRefNew != null) {
                attrIntRefNew = em.getReference(attrIntRefNew.getClass(), attrIntRefNew.getAttrId());
                attrInt.setAttrIntRef(attrIntRefNew);
            }
            attrInt = em.merge(attrInt);
            if (attrIntRefOld != null && !attrIntRefOld.equals(attrIntRefNew)) {
                attrIntRefOld.setAttrInt(null);
                attrIntRefOld = em.merge(attrIntRefOld);
            }
            if (attrIntRefNew != null && !attrIntRefNew.equals(attrIntRefOld)) {
                attrIntRefNew.setAttrInt(attrInt);
                attrIntRefNew = em.merge(attrIntRefNew);
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
                Integer id = attrInt.getAttrIntId();
                if (findAttrInt(id) == null) {
                    throw new NonexistentEntityException("The attrInt with id " + id + " no longer exists.");
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
            AttrInt attrInt;
            try {
                attrInt = em.getReference(AttrInt.class, id);
                attrInt.getAttrIntId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attrInt with id " + id + " no longer exists.", enfe);
            }
            Attr attrIntRef = attrInt.getAttrIntRef();
            if (attrIntRef != null) {
                attrIntRef.setAttrInt(null);
                attrIntRef = em.merge(attrIntRef);
            }
            em.remove(attrInt);
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

    public List<AttrInt> findAttrIntEntities() {
        return findAttrIntEntities(true, -1, -1);
    }

    public List<AttrInt> findAttrIntEntities(int maxResults, int firstResult) {
        return findAttrIntEntities(false, maxResults, firstResult);
    }

    private List<AttrInt> findAttrIntEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttrInt.class));
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

    public AttrInt findAttrInt(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttrInt.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrIntCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttrInt> rt = cq.from(AttrInt.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
