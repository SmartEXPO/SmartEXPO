/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Author;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class AuthorJpaController implements Serializable {

    public AuthorJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Author author) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ItemAuthor itemAuthor = author.getItemAuthor();
            if (itemAuthor != null) {
                itemAuthor = em.getReference(itemAuthor.getClass(), itemAuthor.getItemAuthorId());
                author.setItemAuthor(itemAuthor);
            }
            em.persist(author);
            if (itemAuthor != null) {
                Author oldAuthorIdOfItemAuthor = itemAuthor.getAuthorId();
                if (oldAuthorIdOfItemAuthor != null) {
                    oldAuthorIdOfItemAuthor.setItemAuthor(null);
                    oldAuthorIdOfItemAuthor = em.merge(oldAuthorIdOfItemAuthor);
                }
                itemAuthor.setAuthorId(author);
                itemAuthor = em.merge(itemAuthor);
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

    public void edit(Author author) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Author persistentAuthor = em.find(Author.class, author.getAuthorId());
            ItemAuthor itemAuthorOld = persistentAuthor.getItemAuthor();
            ItemAuthor itemAuthorNew = author.getItemAuthor();
            List<String> illegalOrphanMessages = null;
            if (itemAuthorOld != null && !itemAuthorOld.equals(itemAuthorNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ItemAuthor " + itemAuthorOld + " since its authorId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (itemAuthorNew != null) {
                itemAuthorNew = em.getReference(itemAuthorNew.getClass(), itemAuthorNew.getItemAuthorId());
                author.setItemAuthor(itemAuthorNew);
            }
            author = em.merge(author);
            if (itemAuthorNew != null && !itemAuthorNew.equals(itemAuthorOld)) {
                Author oldAuthorIdOfItemAuthor = itemAuthorNew.getAuthorId();
                if (oldAuthorIdOfItemAuthor != null) {
                    oldAuthorIdOfItemAuthor.setItemAuthor(null);
                    oldAuthorIdOfItemAuthor = em.merge(oldAuthorIdOfItemAuthor);
                }
                itemAuthorNew.setAuthorId(author);
                itemAuthorNew = em.merge(itemAuthorNew);
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
                Integer id = author.getAuthorId();
                if (findAuthor(id) == null) {
                    throw new NonexistentEntityException("The author with id " + id + " no longer exists.");
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
            Author author;
            try {
                author = em.getReference(Author.class, id);
                author.getAuthorId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The author with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ItemAuthor itemAuthorOrphanCheck = author.getItemAuthor();
            if (itemAuthorOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Author (" + author + ") cannot be destroyed since the ItemAuthor " + itemAuthorOrphanCheck + " in its itemAuthor field has a non-nullable authorId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(author);
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

    public List<Author> findAuthorEntities() {
        return findAuthorEntities(true, -1, -1);
    }

    public List<Author> findAuthorEntities(int maxResults, int firstResult) {
        return findAuthorEntities(false, maxResults, firstResult);
    }

    private List<Author> findAuthorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Author.class));
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

    public Author findAuthor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Author.class, id);
        } finally {
            em.close();
        }
    }

    public int getAuthorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Author> rt = cq.from(Author.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
