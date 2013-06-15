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
import com.smartexpo.dynamicdb.models.AttrText;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AttrTextJpaController implements Serializable {

    public AttrTextJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttrText attrText) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Attr attrTextRefOrphanCheck = attrText.getAttrTextRef();
        if (attrTextRefOrphanCheck != null) {
            AttrText oldAttrTextOfAttrTextRef = attrTextRefOrphanCheck.getAttrText();
            if (oldAttrTextOfAttrTextRef != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Attr " + attrTextRefOrphanCheck + " already has an item of type AttrText whose attrTextRef column cannot be null. Please make another selection for the attrTextRef field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Attr attrTextRef = attrText.getAttrTextRef();
            if (attrTextRef != null) {
                attrTextRef = em.getReference(attrTextRef.getClass(), attrTextRef.getAttrId());
                attrText.setAttrTextRef(attrTextRef);
            }
            em.persist(attrText);
            if (attrTextRef != null) {
                attrTextRef.setAttrText(attrText);
                attrTextRef = em.merge(attrTextRef);
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

    public void edit(AttrText attrText) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrText persistentAttrText = em.find(AttrText.class, attrText.getAttrTextId());
            Attr attrTextRefOld = persistentAttrText.getAttrTextRef();
            Attr attrTextRefNew = attrText.getAttrTextRef();
            List<String> illegalOrphanMessages = null;
            if (attrTextRefNew != null && !attrTextRefNew.equals(attrTextRefOld)) {
                AttrText oldAttrTextOfAttrTextRef = attrTextRefNew.getAttrText();
                if (oldAttrTextOfAttrTextRef != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Attr " + attrTextRefNew + " already has an item of type AttrText whose attrTextRef column cannot be null. Please make another selection for the attrTextRef field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (attrTextRefNew != null) {
                attrTextRefNew = em.getReference(attrTextRefNew.getClass(), attrTextRefNew.getAttrId());
                attrText.setAttrTextRef(attrTextRefNew);
            }
            attrText = em.merge(attrText);
            if (attrTextRefOld != null && !attrTextRefOld.equals(attrTextRefNew)) {
                attrTextRefOld.setAttrText(null);
                attrTextRefOld = em.merge(attrTextRefOld);
            }
            if (attrTextRefNew != null && !attrTextRefNew.equals(attrTextRefOld)) {
                attrTextRefNew.setAttrText(attrText);
                attrTextRefNew = em.merge(attrTextRefNew);
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
                Integer id = attrText.getAttrTextId();
                if (findAttrText(id) == null) {
                    throw new NonexistentEntityException("The attrText with id " + id + " no longer exists.");
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
            AttrText attrText;
            try {
                attrText = em.getReference(AttrText.class, id);
                attrText.getAttrTextId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attrText with id " + id + " no longer exists.", enfe);
            }
            Attr attrTextRef = attrText.getAttrTextRef();
            if (attrTextRef != null) {
                attrTextRef.setAttrText(null);
                attrTextRef = em.merge(attrTextRef);
            }
            em.remove(attrText);
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

    public List<AttrText> findAttrTextEntities() {
        return findAttrTextEntities(true, -1, -1);
    }

    public List<AttrText> findAttrTextEntities(int maxResults, int firstResult) {
        return findAttrTextEntities(false, maxResults, firstResult);
    }

    private List<AttrText> findAttrTextEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttrText.class));
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

    public AttrText findAttrText(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttrText.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrTextCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttrText> rt = cq.from(AttrText.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
