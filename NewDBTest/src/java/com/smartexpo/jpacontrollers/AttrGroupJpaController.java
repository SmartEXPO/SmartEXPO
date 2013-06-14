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
import com.smartexpo.dbproto.Item;
import com.smartexpo.dbproto.AgAg;
import com.smartexpo.dbproto.AttrGroup;
import com.smartexpo.jpacontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpacontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpacontrollers.exceptions.RollbackFailureException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class AttrGroupJpaController implements Serializable {

    public AttrGroupJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AttrGroup attrGroup) throws RollbackFailureException, Exception {
        if (attrGroup.getItemCollection() == null) {
            attrGroup.setItemCollection(new ArrayList<Item>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item item = attrGroup.getItem();
            if (item != null) {
                item = em.getReference(item.getClass(), item.getItemId());
                attrGroup.setItem(item);
            }
            AgAg agAg_toParent = attrGroup.getAgAg_toParent();
            if (agAg_toParent != null) {
                agAg_toParent = em.getReference(agAg_toParent.getClass(), agAg_toParent.getAgAgId());
                attrGroup.setAgAg_toParent(agAg_toParent);
            }
            Collection<Item> attachedItemCollection = new ArrayList<Item>();
            for (Item itemCollectionItemToAttach : attrGroup.getItemCollection()) {
                itemCollectionItemToAttach = em.getReference(itemCollectionItemToAttach.getClass(), itemCollectionItemToAttach.getItemId());
                attachedItemCollection.add(itemCollectionItemToAttach);
            }
            attrGroup.setItemCollection(attachedItemCollection);
            em.persist(attrGroup);
            if (item != null) {
                AttrGroup oldItemAttrGroupIdOfItem = item.getItemAttrGroupId();
                if (oldItemAttrGroupIdOfItem != null) {
                    oldItemAttrGroupIdOfItem.setItem(null);
                    oldItemAttrGroupIdOfItem = em.merge(oldItemAttrGroupIdOfItem);
                }
                item.setItemAttrGroupId(attrGroup);
                item = em.merge(item);
            }
            if (agAg_toParent != null) {
                AttrGroup oldAgChildIdOfAgAg_toParent = agAg_toParent.getAgChildId();
                if (oldAgChildIdOfAgAg_toParent != null) {
                    oldAgChildIdOfAgAg_toParent.setAgAg_toParent(null);
                    oldAgChildIdOfAgAg_toParent = em.merge(oldAgChildIdOfAgAg_toParent);
                }
                agAg_toParent.setAgChildId(attrGroup);
                agAg_toParent = em.merge(agAg_toParent);
            }
            for (Item itemCollectionItem : attrGroup.getItemCollection()) {
                AttrGroup oldItemAttrGroupIdOfItemCollectionItem = itemCollectionItem.getItemAttrGroupId();
                itemCollectionItem.setItemAttrGroupId(attrGroup);
                itemCollectionItem = em.merge(itemCollectionItem);
                if (oldItemAttrGroupIdOfItemCollectionItem != null) {
                    oldItemAttrGroupIdOfItemCollectionItem.getItemCollection().remove(itemCollectionItem);
                    oldItemAttrGroupIdOfItemCollectionItem = em.merge(oldItemAttrGroupIdOfItemCollectionItem);
                }
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

    public void edit(AttrGroup attrGroup) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AttrGroup persistentAttrGroup = em.find(AttrGroup.class, attrGroup.getAttrGroupId());
            Item itemOld = persistentAttrGroup.getItem();
            Item itemNew = attrGroup.getItem();
            AgAg agAg_toParentOld = persistentAttrGroup.getAgAg_toParent();
            AgAg agAg_toParentNew = attrGroup.getAgAg_toParent();
            Collection<Item> itemCollectionOld = persistentAttrGroup.getItemCollection();
            Collection<Item> itemCollectionNew = attrGroup.getItemCollection();
            List<String> illegalOrphanMessages = null;
            if (itemOld != null && !itemOld.equals(itemNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Item " + itemOld + " since its itemAttrGroupId field is not nullable.");
            }
            if (agAg_toParentOld != null && !agAg_toParentOld.equals(agAg_toParentNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain AgAg " + agAg_toParentOld + " since its agChildId field is not nullable.");
            }
            for (Item itemCollectionOldItem : itemCollectionOld) {
                if (!itemCollectionNew.contains(itemCollectionOldItem)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Item " + itemCollectionOldItem + " since its itemAttrGroupId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemNew != null) {
                itemNew = em.getReference(itemNew.getClass(), itemNew.getItemId());
                attrGroup.setItem(itemNew);
            }
            if (agAg_toParentNew != null) {
                agAg_toParentNew = em.getReference(agAg_toParentNew.getClass(), agAg_toParentNew.getAgAgId());
                attrGroup.setAgAg_toParent(agAg_toParentNew);
            }
            Collection<Item> attachedItemCollectionNew = new ArrayList<Item>();
            for (Item itemCollectionNewItemToAttach : itemCollectionNew) {
                itemCollectionNewItemToAttach = em.getReference(itemCollectionNewItemToAttach.getClass(), itemCollectionNewItemToAttach.getItemId());
                attachedItemCollectionNew.add(itemCollectionNewItemToAttach);
            }
            itemCollectionNew = attachedItemCollectionNew;
            attrGroup.setItemCollection(itemCollectionNew);
            attrGroup = em.merge(attrGroup);
            if (itemNew != null && !itemNew.equals(itemOld)) {
                AttrGroup oldItemAttrGroupIdOfItem = itemNew.getItemAttrGroupId();
                if (oldItemAttrGroupIdOfItem != null) {
                    oldItemAttrGroupIdOfItem.setItem(null);
                    oldItemAttrGroupIdOfItem = em.merge(oldItemAttrGroupIdOfItem);
                }
                itemNew.setItemAttrGroupId(attrGroup);
                itemNew = em.merge(itemNew);
            }
            if (agAg_toParentNew != null && !agAg_toParentNew.equals(agAg_toParentOld)) {
                AttrGroup oldAgChildIdOfAgAg_toParent = agAg_toParentNew.getAgChildId();
                if (oldAgChildIdOfAgAg_toParent != null) {
                    oldAgChildIdOfAgAg_toParent.setAgAg_toParent(null);
                    oldAgChildIdOfAgAg_toParent = em.merge(oldAgChildIdOfAgAg_toParent);
                }
                agAg_toParentNew.setAgChildId(attrGroup);
                agAg_toParentNew = em.merge(agAg_toParentNew);
            }
            for (Item itemCollectionNewItem : itemCollectionNew) {
                if (!itemCollectionOld.contains(itemCollectionNewItem)) {
                    AttrGroup oldItemAttrGroupIdOfItemCollectionNewItem = itemCollectionNewItem.getItemAttrGroupId();
                    itemCollectionNewItem.setItemAttrGroupId(attrGroup);
                    itemCollectionNewItem = em.merge(itemCollectionNewItem);
                    if (oldItemAttrGroupIdOfItemCollectionNewItem != null && !oldItemAttrGroupIdOfItemCollectionNewItem.equals(attrGroup)) {
                        oldItemAttrGroupIdOfItemCollectionNewItem.getItemCollection().remove(itemCollectionNewItem);
                        oldItemAttrGroupIdOfItemCollectionNewItem = em.merge(oldItemAttrGroupIdOfItemCollectionNewItem);
                    }
                }
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
                Integer id = attrGroup.getAttrGroupId();
                if (findAttrGroup(id) == null) {
                    throw new NonexistentEntityException("The attrGroup with id " + id + " no longer exists.");
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
            AttrGroup attrGroup;
            try {
                attrGroup = em.getReference(AttrGroup.class, id);
                attrGroup.getAttrGroupId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attrGroup with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Item itemOrphanCheck = attrGroup.getItem();
            if (itemOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AttrGroup (" + attrGroup + ") cannot be destroyed since the Item " + itemOrphanCheck + " in its item field has a non-nullable itemAttrGroupId field.");
            }
            AgAg agAg_toParentOrphanCheck = attrGroup.getAgAg_toParent();
            if (agAg_toParentOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AttrGroup (" + attrGroup + ") cannot be destroyed since the AgAg " + agAg_toParentOrphanCheck + " in its agAg_toParent field has a non-nullable agChildId field.");
            }
            Collection<Item> itemCollectionOrphanCheck = attrGroup.getItemCollection();
            for (Item itemCollectionOrphanCheckItem : itemCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AttrGroup (" + attrGroup + ") cannot be destroyed since the Item " + itemCollectionOrphanCheckItem + " in its itemCollection field has a non-nullable itemAttrGroupId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(attrGroup);
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

    public List<AttrGroup> findAttrGroupEntities() {
        return findAttrGroupEntities(true, -1, -1);
    }

    public List<AttrGroup> findAttrGroupEntities(int maxResults, int firstResult) {
        return findAttrGroupEntities(false, maxResults, firstResult);
    }

    private List<AttrGroup> findAttrGroupEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AttrGroup.class));
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

    public AttrGroup findAttrGroup(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AttrGroup.class, id);
        } finally {
            em.close();
        }
    }

    public int getAttrGroupCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AttrGroup> rt = cq.from(AttrGroup.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
