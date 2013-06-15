/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.models.Item;
import com.smartexpo.models.Author;
import com.smartexpo.models.ItemAuthor;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ItemAuthorJpaController implements Serializable {

    public ItemAuthorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ItemAuthor itemAuthor) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Item itemIdOrphanCheck = itemAuthor.getItemId();
        if (itemIdOrphanCheck != null) {
            ItemAuthor oldItemAuthorOfItemId = itemIdOrphanCheck.getItemAuthor();
            if (oldItemAuthorOfItemId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Item " + itemIdOrphanCheck + " already has an item of type ItemAuthor whose itemId column cannot be null. Please make another selection for the itemId field.");
            }
        }
        Author authorIdOrphanCheck = itemAuthor.getAuthorId();
        if (authorIdOrphanCheck != null) {
            ItemAuthor oldItemAuthorOfAuthorId = authorIdOrphanCheck.getItemAuthor();
            if (oldItemAuthorOfAuthorId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Author " + authorIdOrphanCheck + " already has an item of type ItemAuthor whose authorId column cannot be null. Please make another selection for the authorId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Item itemId = itemAuthor.getItemId();
            if (itemId != null) {
                itemId = em.getReference(itemId.getClass(), itemId.getItemId());
                itemAuthor.setItemId(itemId);
            }
            Author authorId = itemAuthor.getAuthorId();
            if (authorId != null) {
                authorId = em.getReference(authorId.getClass(), authorId.getAuthorId());
                itemAuthor.setAuthorId(authorId);
            }
            em.persist(itemAuthor);
            if (itemId != null) {
                itemId.setItemAuthor(itemAuthor);
                itemId = em.merge(itemId);
            }
            if (authorId != null) {
                authorId.setItemAuthor(itemAuthor);
                authorId = em.merge(authorId);
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

    public void edit(ItemAuthor itemAuthor) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemAuthor persistentItemAuthor = em.find(ItemAuthor.class, itemAuthor.getItemAuthorId());
            Item itemIdOld = persistentItemAuthor.getItemId();
            Item itemIdNew = itemAuthor.getItemId();
            Author authorIdOld = persistentItemAuthor.getAuthorId();
            Author authorIdNew = itemAuthor.getAuthorId();
            List<String> illegalOrphanMessages = null;
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                ItemAuthor oldItemAuthorOfItemId = itemIdNew.getItemAuthor();
                if (oldItemAuthorOfItemId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Item " + itemIdNew + " already has an item of type ItemAuthor whose itemId column cannot be null. Please make another selection for the itemId field.");
                }
            }
            if (authorIdNew != null && !authorIdNew.equals(authorIdOld)) {
                ItemAuthor oldItemAuthorOfAuthorId = authorIdNew.getItemAuthor();
                if (oldItemAuthorOfAuthorId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Author " + authorIdNew + " already has an item of type ItemAuthor whose authorId column cannot be null. Please make another selection for the authorId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemIdNew != null) {
                itemIdNew = em.getReference(itemIdNew.getClass(), itemIdNew.getItemId());
                itemAuthor.setItemId(itemIdNew);
            }
            if (authorIdNew != null) {
                authorIdNew = em.getReference(authorIdNew.getClass(), authorIdNew.getAuthorId());
                itemAuthor.setAuthorId(authorIdNew);
            }
            itemAuthor = em.merge(itemAuthor);
            if (itemIdOld != null && !itemIdOld.equals(itemIdNew)) {
                itemIdOld.setItemAuthor(null);
                itemIdOld = em.merge(itemIdOld);
            }
            if (itemIdNew != null && !itemIdNew.equals(itemIdOld)) {
                itemIdNew.setItemAuthor(itemAuthor);
                itemIdNew = em.merge(itemIdNew);
            }
            if (authorIdOld != null && !authorIdOld.equals(authorIdNew)) {
                authorIdOld.setItemAuthor(null);
                authorIdOld = em.merge(authorIdOld);
            }
            if (authorIdNew != null && !authorIdNew.equals(authorIdOld)) {
                authorIdNew.setItemAuthor(itemAuthor);
                authorIdNew = em.merge(authorIdNew);
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
                Integer id = itemAuthor.getItemAuthorId();
                if (findItemAuthor(id) == null) {
                    throw new NonexistentEntityException("The itemAuthor with id " + id + " no longer exists.");
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
            ItemAuthor itemAuthor;
            try {
                itemAuthor = em.getReference(ItemAuthor.class, id);
                itemAuthor.getItemAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The itemAuthor with id " + id + " no longer exists.", enfe);
            }
            Item itemId = itemAuthor.getItemId();
            if (itemId != null) {
                itemId.setItemAuthor(null);
                itemId = em.merge(itemId);
            }
            Author authorId = itemAuthor.getAuthorId();
            if (authorId != null) {
                authorId.setItemAuthor(null);
                authorId = em.merge(authorId);
            }
            em.remove(itemAuthor);
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

    public List<ItemAuthor> findItemAuthorEntities() {
        return findItemAuthorEntities(true, -1, -1);
    }

    public List<ItemAuthor> findItemAuthorEntities(int maxResults, int firstResult) {
        return findItemAuthorEntities(false, maxResults, firstResult);
    }

    private List<ItemAuthor> findItemAuthorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ItemAuthor.class));
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

    public ItemAuthor findItemAuthor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ItemAuthor.class, id);
        } finally {
            em.close();
        }
    }

    public int getItemAuthorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ItemAuthor> rt = cq.from(ItemAuthor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
