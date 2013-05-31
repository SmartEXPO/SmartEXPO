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
public class PermissionJpaController implements Serializable {

    public PermissionJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Permission permission) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ManagerPermission managerPermission = permission.getManagerPermission();
            if (managerPermission != null) {
                managerPermission = em.getReference(managerPermission.getClass(), managerPermission.getManagerPermissionId());
                permission.setManagerPermission(managerPermission);
            }
            em.persist(permission);
            if (managerPermission != null) {
                Permission oldPermissionIdOfManagerPermission = managerPermission.getPermissionId();
                if (oldPermissionIdOfManagerPermission != null) {
                    oldPermissionIdOfManagerPermission.setManagerPermission(null);
                    oldPermissionIdOfManagerPermission = em.merge(oldPermissionIdOfManagerPermission);
                }
                managerPermission.setPermissionId(permission);
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

    public void edit(Permission permission) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Permission persistentPermission = em.find(Permission.class, permission.getPermissionId());
            ManagerPermission managerPermissionOld = persistentPermission.getManagerPermission();
            ManagerPermission managerPermissionNew = permission.getManagerPermission();
            List<String> illegalOrphanMessages = null;
            if (managerPermissionOld != null && !managerPermissionOld.equals(managerPermissionNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain ManagerPermission " + managerPermissionOld + " since its permissionId field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (managerPermissionNew != null) {
                managerPermissionNew = em.getReference(managerPermissionNew.getClass(), managerPermissionNew.getManagerPermissionId());
                permission.setManagerPermission(managerPermissionNew);
            }
            permission = em.merge(permission);
            if (managerPermissionNew != null && !managerPermissionNew.equals(managerPermissionOld)) {
                Permission oldPermissionIdOfManagerPermission = managerPermissionNew.getPermissionId();
                if (oldPermissionIdOfManagerPermission != null) {
                    oldPermissionIdOfManagerPermission.setManagerPermission(null);
                    oldPermissionIdOfManagerPermission = em.merge(oldPermissionIdOfManagerPermission);
                }
                managerPermissionNew.setPermissionId(permission);
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
                Integer id = permission.getPermissionId();
                if (findPermission(id) == null) {
                    throw new NonexistentEntityException("The permission with id " + id + " no longer exists.");
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
            Permission permission;
            try {
                permission = em.getReference(Permission.class, id);
                permission.getPermissionId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The permission with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            ManagerPermission managerPermissionOrphanCheck = permission.getManagerPermission();
            if (managerPermissionOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Permission (" + permission + ") cannot be destroyed since the ManagerPermission " + managerPermissionOrphanCheck + " in its managerPermission field has a non-nullable permissionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(permission);
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

    public List<Permission> findPermissionEntities() {
        return findPermissionEntities(true, -1, -1);
    }

    public List<Permission> findPermissionEntities(int maxResults, int firstResult) {
        return findPermissionEntities(false, maxResults, firstResult);
    }

    private List<Permission> findPermissionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Permission.class));
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

    public Permission findPermission(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Permission.class, id);
        } finally {
            em.close();
        }
    }

    public int getPermissionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Permission> rt = cq.from(Permission.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
