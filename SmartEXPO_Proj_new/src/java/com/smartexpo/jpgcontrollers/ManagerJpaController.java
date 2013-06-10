/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.jpgcontrollers;

import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Manager;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.smartexpo.models.ManagerPermission;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ManagerJpaController implements Serializable {

    public ManagerJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Manager manager) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ManagerPermission managerPermission = manager.getManagerPermission();
            if (managerPermission != null) {
                managerPermission = em.getReference(managerPermission.getClass(), managerPermission.getManagerPermissionId());
                manager.setManagerPermission(managerPermission);
            }
            em.persist(manager);
            if (managerPermission != null) {
                Manager oldManagerIdOfManagerPermission = managerPermission.getManagerId();
                if (oldManagerIdOfManagerPermission != null) {
                    oldManagerIdOfManagerPermission.setManagerPermission(null);
                    oldManagerIdOfManagerPermission = em.merge(oldManagerIdOfManagerPermission);
                }
                managerPermission.setManagerId(manager);
                managerPermission = em.merge(managerPermission);
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

    public void edit(Manager manager) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Manager persistentManager = em.find(Manager.class, manager.getManagerId());
            ManagerPermission managerPermissionOld = persistentManager.getManagerPermission();
            ManagerPermission managerPermissionNew = manager.getManagerPermission();
            List<String> illegalOrphanMessages = null;
            if (managerPermissionOld != null && !managerPermissionOld.equals(managerPermissionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ManagerPermission " + managerPermissionOld + " since its managerId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (managerPermissionNew != null) {
                managerPermissionNew = em.getReference(managerPermissionNew.getClass(), managerPermissionNew.getManagerPermissionId());
                manager.setManagerPermission(managerPermissionNew);
            }
            manager = em.merge(manager);
            if (managerPermissionNew != null && !managerPermissionNew.equals(managerPermissionOld)) {
                Manager oldManagerIdOfManagerPermission = managerPermissionNew.getManagerId();
                if (oldManagerIdOfManagerPermission != null) {
                    oldManagerIdOfManagerPermission.setManagerPermission(null);
                    oldManagerIdOfManagerPermission = em.merge(oldManagerIdOfManagerPermission);
                }
                managerPermissionNew.setManagerId(manager);
                managerPermissionNew = em.merge(managerPermissionNew);
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
                Integer id = manager.getManagerId();
                if (findManager(id) == null) {
                    throw new NonexistentEntityException("The manager with id " + id + " no longer exists.");
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
            Manager manager;
            try {
                manager = em.getReference(Manager.class, id);
                manager.getManagerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The manager with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ManagerPermission managerPermissionOrphanCheck = manager.getManagerPermission();
            if (managerPermissionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Manager (" + manager + ") cannot be destroyed since the ManagerPermission " + managerPermissionOrphanCheck + " in its managerPermission field has a non-nullable managerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(manager);
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

    public List<Manager> findManagerEntities() {
        return findManagerEntities(true, -1, -1);
    }

    public List<Manager> findManagerEntities(int maxResults, int firstResult) {
        return findManagerEntities(false, maxResults, firstResult);
    }

    private List<Manager> findManagerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Manager.class));
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

    public Manager findManager(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Manager.class, id);
        } finally {
            em.close();
        }
    }

    public int getManagerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Manager> rt = cq.from(Manager.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    public void destroy(Manager selectedUser) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
