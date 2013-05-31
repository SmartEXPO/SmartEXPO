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
import com.smartexpo.models.Manager;
import com.smartexpo.models.ManagerPermission;
import com.smartexpo.models.Permission;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author tornado718
 */
public class ManagerPermissionJpaController implements Serializable {

    public ManagerPermissionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ManagerPermission managerPermission) throws IllegalOrphanException, RollbackFailureException, Exception {
        List<String> illegalOrphanMessages = null;
        Manager managerIdOrphanCheck = managerPermission.getManagerId();
        if (managerIdOrphanCheck != null) {
            ManagerPermission oldManagerPermissionOfManagerId = managerIdOrphanCheck.getManagerPermission();
            if (oldManagerPermissionOfManagerId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Manager " + managerIdOrphanCheck + " already has an item of type ManagerPermission whose managerId column cannot be null. Please make another selection for the managerId field.");
            }
        }
        Permission permissionIdOrphanCheck = managerPermission.getPermissionId();
        if (permissionIdOrphanCheck != null) {
            ManagerPermission oldManagerPermissionOfPermissionId = permissionIdOrphanCheck.getManagerPermission();
            if (oldManagerPermissionOfPermissionId != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Permission " + permissionIdOrphanCheck + " already has an item of type ManagerPermission whose permissionId column cannot be null. Please make another selection for the permissionId field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Manager managerId = managerPermission.getManagerId();
            if (managerId != null) {
                managerId = em.getReference(managerId.getClass(), managerId.getManagerId());
                managerPermission.setManagerId(managerId);
            }
            Permission permissionId = managerPermission.getPermissionId();
            if (permissionId != null) {
                permissionId = em.getReference(permissionId.getClass(), permissionId.getPermissionId());
                managerPermission.setPermissionId(permissionId);
            }
            em.persist(managerPermission);
            if (managerId != null) {
                managerId.setManagerPermission(managerPermission);
                managerId = em.merge(managerId);
            }
            if (permissionId != null) {
                permissionId.setManagerPermission(managerPermission);
                permissionId = em.merge(permissionId);
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

    public void edit(ManagerPermission managerPermission) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ManagerPermission persistentManagerPermission = em.find(ManagerPermission.class, managerPermission.getManagerPermissionId());
            Manager managerIdOld = persistentManagerPermission.getManagerId();
            Manager managerIdNew = managerPermission.getManagerId();
            Permission permissionIdOld = persistentManagerPermission.getPermissionId();
            Permission permissionIdNew = managerPermission.getPermissionId();
            List<String> illegalOrphanMessages = null;
            if (managerIdNew != null && !managerIdNew.equals(managerIdOld)) {
                ManagerPermission oldManagerPermissionOfManagerId = managerIdNew.getManagerPermission();
                if (oldManagerPermissionOfManagerId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Manager " + managerIdNew + " already has an item of type ManagerPermission whose managerId column cannot be null. Please make another selection for the managerId field.");
                }
            }
            if (permissionIdNew != null && !permissionIdNew.equals(permissionIdOld)) {
                ManagerPermission oldManagerPermissionOfPermissionId = permissionIdNew.getManagerPermission();
                if (oldManagerPermissionOfPermissionId != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Permission " + permissionIdNew + " already has an item of type ManagerPermission whose permissionId column cannot be null. Please make another selection for the permissionId field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (managerIdNew != null) {
                managerIdNew = em.getReference(managerIdNew.getClass(), managerIdNew.getManagerId());
                managerPermission.setManagerId(managerIdNew);
            }
            if (permissionIdNew != null) {
                permissionIdNew = em.getReference(permissionIdNew.getClass(), permissionIdNew.getPermissionId());
                managerPermission.setPermissionId(permissionIdNew);
            }
            managerPermission = em.merge(managerPermission);
            if (managerIdOld != null && !managerIdOld.equals(managerIdNew)) {
                managerIdOld.setManagerPermission(null);
                managerIdOld = em.merge(managerIdOld);
            }
            if (managerIdNew != null && !managerIdNew.equals(managerIdOld)) {
                managerIdNew.setManagerPermission(managerPermission);
                managerIdNew = em.merge(managerIdNew);
            }
            if (permissionIdOld != null && !permissionIdOld.equals(permissionIdNew)) {
                permissionIdOld.setManagerPermission(null);
                permissionIdOld = em.merge(permissionIdOld);
            }
            if (permissionIdNew != null && !permissionIdNew.equals(permissionIdOld)) {
                permissionIdNew.setManagerPermission(managerPermission);
                permissionIdNew = em.merge(permissionIdNew);
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
                Integer id = managerPermission.getManagerPermissionId();
                if (findManagerPermission(id) == null) {
                    throw new NonexistentEntityException("The managerPermission with id " + id + " no longer exists.");
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
            ManagerPermission managerPermission;
            try {
                managerPermission = em.getReference(ManagerPermission.class, id);
                managerPermission.getManagerPermissionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The managerPermission with id " + id + " no longer exists.", enfe);
            }
            Manager managerId = managerPermission.getManagerId();
            if (managerId != null) {
                managerId.setManagerPermission(null);
                managerId = em.merge(managerId);
            }
            Permission permissionId = managerPermission.getPermissionId();
            if (permissionId != null) {
                permissionId.setManagerPermission(null);
                permissionId = em.merge(permissionId);
            }
            em.remove(managerPermission);
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

    public List<ManagerPermission> findManagerPermissionEntities() {
        return findManagerPermissionEntities(true, -1, -1);
    }

    public List<ManagerPermission> findManagerPermissionEntities(int maxResults, int firstResult) {
        return findManagerPermissionEntities(false, maxResults, firstResult);
    }

    private List<ManagerPermission> findManagerPermissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ManagerPermission.class));
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

    public ManagerPermission findManagerPermission(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ManagerPermission.class, id);
        } finally {
            em.close();
        }
    }

    public int getManagerPermissionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ManagerPermission> rt = cq.from(ManagerPermission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
