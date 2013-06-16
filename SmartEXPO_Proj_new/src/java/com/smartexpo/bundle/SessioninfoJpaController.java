/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.bundle;

import com.smartexpo.bundle.exceptions.NonexistentEntityException;
import com.smartexpo.bundle.exceptions.PreexistingEntityException;
import com.smartexpo.bundle.exceptions.RollbackFailureException;
import com.smartexpo.models.Sessioninfo;
import com.smartexpo.models.SessioninfoPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class SessioninfoJpaController implements Serializable {

    public SessioninfoJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sessioninfo sessioninfo) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (sessioninfo.getSessioninfoPK() == null) {
            sessioninfo.setSessioninfoPK(new SessioninfoPK());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(sessioninfo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findSessioninfo(sessioninfo.getSessioninfoPK()) != null) {
                throw new PreexistingEntityException("Sessioninfo " + sessioninfo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Sessioninfo sessioninfo) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            sessioninfo = em.merge(sessioninfo);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                SessioninfoPK id = sessioninfo.getSessioninfoPK();
                if (findSessioninfo(id) == null) {
                    throw new NonexistentEntityException("The sessioninfo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(SessioninfoPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sessioninfo sessioninfo;
            try {
                sessioninfo = em.getReference(Sessioninfo.class, id);
                sessioninfo.getSessioninfoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sessioninfo with id " + id + " no longer exists.", enfe);
            }
            em.remove(sessioninfo);
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

    public List<Sessioninfo> findSessioninfoEntities() {
        return findSessioninfoEntities(true, -1, -1);
    }

    public List<Sessioninfo> findSessioninfoEntities(int maxResults, int firstResult) {
        return findSessioninfoEntities(false, maxResults, firstResult);
    }

    private List<Sessioninfo> findSessioninfoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sessioninfo.class));
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

    public Sessioninfo findSessioninfo(SessioninfoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sessioninfo.class, id);
        } finally {
            em.close();
        }
    }

    public int getSessioninfoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sessioninfo> rt = cq.from(Sessioninfo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
