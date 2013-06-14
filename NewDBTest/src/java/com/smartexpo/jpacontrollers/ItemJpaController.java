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
import com.smartexpo.dbproto.AttrGroup;
import com.smartexpo.dbproto.Item;
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
public class ItemJpaController implements Serializable {

    public ItemJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Item item) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        AttrGroup itemAttrGroupIdOrphanCheck = item.getItemAttrGroupId();
        if (itemAttrGroupIdOrphanCheck != null) {
            Item oldItemOfItemAttrGroupId = itemAttrGroupIdOrphanCheck.getItem();
            if (oldItemOfItemAttrGroupId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The AttrGroup " + itemAttrGroupIdOrphanCheck + " already has an item of type Item whose itemAttrGroupId column cannot be null. Please make another selection for the itemAttrGroupId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrGroup itemAttrGroupId = item.getItemAttrGroupId();
            if (itemAttrGroupId != null) {
                itemAttrGroupId = em.getReference(itemAttrGroupId.getClass(), itemAttrGroupId.getAttrGroupId());
                item.setItemAttrGroupId(itemAttrGroupId);
            }
            em.persist(item);
            if (itemAttrGroupId != null) {
                itemAttrGroupId.setItem(item);
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

    public void edit(Item item) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item persistentItem = em.find(Item.class, item.getItemId());
            AttrGroup itemAttrGroupIdOld = persistentItem.getItemAttrGroupId();
            AttrGroup itemAttrGroupIdNew = item.getItemAttrGroupId();
            List<String> illegalOrphanMessages = null;
            if (itemAttrGroupIdNew != null && !itemAttrGroupIdNew.equals(itemAttrGroupIdOld)) {
                Item oldItemOfItemAttrGroupId = itemAttrGroupIdNew.getItem();
                if (oldItemOfItemAttrGroupId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The AttrGroup " + itemAttrGroupIdNew + " already has an item of type Item whose itemAttrGroupId column cannot be null. Please make another selection for the itemAttrGroupId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemAttrGroupIdNew != null) {
                itemAttrGroupIdNew = em.getReference(itemAttrGroupIdNew.getClass(), itemAttrGroupIdNew.getAttrGroupId());
                item.setItemAttrGroupId(itemAttrGroupIdNew);
            }
            item = em.merge(item);
            if (itemAttrGroupIdOld != null && !itemAttrGroupIdOld.equals(itemAttrGroupIdNew)) {
                itemAttrGroupIdOld.setItem(null);
                itemAttrGroupIdOld = em.merge(itemAttrGroupIdOld);
            }
            if (itemAttrGroupIdNew != null && !itemAttrGroupIdNew.equals(itemAttrGroupIdOld)) {
                itemAttrGroupIdNew.setItem(item);
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
                Integer id = item.getItemId();
                if (findItem(id) == null) {
                    throw new NonexistentEntityException("The item with id " + id + " no longer exists.");
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
            Item item;
            try {
                item = em.getReference(Item.class, id);
                item.getItemId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The item with id " + id + " no longer exists.", enfe);
            }
            AttrGroup itemAttrGroupId = item.getItemAttrGroupId();
            if (itemAttrGroupId != null) {
                itemAttrGroupId.setItem(null);
                itemAttrGroupId = em.merge(itemAttrGroupId);
            }
            em.remove(item);
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

    public List<Item> findItemEntities() {
        return findItemEntities(true, -1, -1);
    }

    public List<Item> findItemEntities(int maxResults, int firstResult) {
        return findItemEntities(false, maxResults, firstResult);
    }

    private List<Item> findItemEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Item.class));
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

    public Item findItem(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Item.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Item> rt = cq.from(Item.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
