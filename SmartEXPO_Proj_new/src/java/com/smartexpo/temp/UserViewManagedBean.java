/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.ManagerJpaController;
import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Manager;
import com.smartexpo.models.Permission;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.UserTransaction;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class UserViewManagedBean implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    private static final Logger LOG = Logger.getLogger(CommentViewManagedBean.class.getName());
    private List<Manager> managers;

    /**
     * Creates a new instance of UserViewManagedBean
     */
    public UserViewManagedBean() {
    }

    /**
     * @return the users
     */
    public List<Manager> getUsers() {
        if (managers == null) {
            gi = new GetInfo(emf, utx);
            ManagerJpaController mjc = new ManagerJpaController(utx, emf);
            managers = mjc.findManagerEntities();

        }

        return managers;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<Manager> users) {
        this.managers = users;
    }

    public void onEdit(RowEditEvent event) {
        try {
            ManagerJpaController mjc = new ManagerJpaController(utx, emf);
            mjc.edit(selectedUser);
        } catch (IllegalOrphanException ex) {
            Logger.getLogger(UserViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(UserViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserViewManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onCancel(RowEditEvent event) {
    }

    public void destroyUser() {
        managers.remove(selectedUser);
    }

    public void test() {
    }
    private Manager selectedUser;

    public Manager getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Manager selectedUser) {
        this.selectedUser = selectedUser;
    }
}
