/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpacontroller;

import com.smartexpo.concretemodel.exceptions.IllegalOrphanException;
import com.smartexpo.concretemodel.exceptions.NonexistentEntityException;
import com.smartexpo.concretemodel.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.dbproto.AttrGroup;
import com.smartexpo.dbproto.DBItem;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class DBItemJpaController implements Serializable {

    public DBItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(DBItem DBItem) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        AttrGroup itemAttrGroupIdOrphanCheck = DBItem.getItemAttrGroupId();
        if (itemAttrGroupIdOrphanCheck != null) {
            DBItem oldItemOfItemAttrGroupId = itemAttrGroupIdOrphanCheck.getItem();
            if (oldItemOfItemAttrGroupId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The AttrGroup " + itemAttrGroupIdOrphanCheck + " already has an item of type DBItem whose itemAttrGroupId column cannot be null. Please make another selection for the itemAttrGroupId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrGroup itemAttrGroupId = DBItem.getItemAttrGroupId();
            if (itemAttrGroupId != null) {
                itemAttrGroupId = em.getReference(itemAttrGroupId.getClass(), itemAttrGroupId.getAttrGroupId());
                DBItem.setItemAttrGroupId(itemAttrGroupId);
            }
            em.persist(DBItem);
            if (itemAttrGroupId != null) {
                itemAttrGroupId.setItem(DBItem);
                itemAttrGroupId = em.merge(itemAttrGroupId);
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

    public void edit(DBItem DBItem) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            DBItem persistentDBItem = em.find(DBItem.class, DBItem.getItemId());
            AttrGroup itemAttrGroupIdOld = persistentDBItem.getItemAttrGroupId();
            AttrGroup itemAttrGroupIdNew = DBItem.getItemAttrGroupId();
            List<String> illegalOrphanMessages = null;
            if (itemAttrGroupIdNew != null && !itemAttrGroupIdNew.equals(itemAttrGroupIdOld)) {
                DBItem oldItemOfItemAttrGroupId = itemAttrGroupIdNew.getItem();
                if (oldItemOfItemAttrGroupId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The AttrGroup " + itemAttrGroupIdNew + " already has an item of type DBItem whose itemAttrGroupId column cannot be null. Please make another selection for the itemAttrGroupId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemAttrGroupIdNew != null) {
                itemAttrGroupIdNew = em.getReference(itemAttrGroupIdNew.getClass(), itemAttrGroupIdNew.getAttrGroupId());
                DBItem.setItemAttrGroupId(itemAttrGroupIdNew);
            }
            DBItem = em.merge(DBItem);
            if (itemAttrGroupIdOld != null && !itemAttrGroupIdOld.equals(itemAttrGroupIdNew)) {
                itemAttrGroupIdOld.setItem(null);
                itemAttrGroupIdOld = em.merge(itemAttrGroupIdOld);
            }
            if (itemAttrGroupIdNew != null && !itemAttrGroupIdNew.equals(itemAttrGroupIdOld)) {
                itemAttrGroupIdNew.setItem(DBItem);
                itemAttrGroupIdNew = em.merge(itemAttrGroupIdNew);
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
                Integer id = DBItem.getItemId();
                if (findDBItem(id) == null) {
                    throw new NonexistentEntityException("The dBItem with id " + id + " no longer exists.");
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
            DBItem DBItem;
            try {
                DBItem = em.getReference(DBItem.class, id);
                DBItem.getItemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The DBItem with id " + id + " no longer exists.", enfe);
            }
            AttrGroup itemAttrGroupId = DBItem.getItemAttrGroupId();
            if (itemAttrGroupId != null) {
                itemAttrGroupId.setItem(null);
                itemAttrGroupId = em.merge(itemAttrGroupId);
            }
            em.remove(DBItem);
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

    public List<DBItem> findDBItemEntities() {
        return findDBItemEntities(true, -1, -1);
    }

    public List<DBItem> findDBItemEntities(int maxResults, int firstResult) {
        return findDBItemEntities(false, maxResults, firstResult);
    }

    private List<DBItem> findDBItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(DBItem.class));
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

    public DBItem findDBItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(DBItem.class, id);
        } finally {
            em.close();
        }
    }

    public int getDBItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<DBItem> rt = cq.from(DBItem.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
