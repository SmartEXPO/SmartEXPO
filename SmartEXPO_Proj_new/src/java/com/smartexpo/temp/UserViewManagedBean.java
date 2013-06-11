/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.ManagerJpaController;
import com.smartexpo.jpgcontrollers.ManagerPermissionJpaController;
import com.smartexpo.jpgcontrollers.exceptions.IllegalOrphanException;
import com.smartexpo.jpgcontrollers.exceptions.NonexistentEntityException;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Manager;
import com.smartexpo.models.ManagerPermission;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;
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
    private GetInfo gi = null;
    private List<Manager> managers;
    private Manager selectedUser;

    /**
     * Creates a new instance of UserViewManagedBean
     */
    public UserViewManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        gi = new GetInfo(emf, utx);
    }

    /**
     * @return the users
     */
    public List<Manager> getUsers() {
        if (managers == null) {
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

            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Edit successfully.", "Username is " + ((Manager) event.getObject()).getUsername()));
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
        FacesContext.getCurrentInstance()
                .addMessage(null, new FacesMessage("Edit cancelled."));
    }

    // 缺少从数据库中删除这一步，目标为selectedUser
    public void destroyUser() {
        try {
            managers.remove(selectedUser);

            ManagerPermissionJpaController mpjc = new ManagerPermissionJpaController(utx, emf);
            List<ManagerPermission> mps = gi.getManagerPermissionsByManagerID(selectedUser.getManagerId());
            for (int i = 0; i < mps.size(); i++) {
                mpjc.destroy(mps.get(i).getManagerPermissionId());
            }

            ManagerJpaController mjc = new ManagerJpaController(utx, emf);

            mjc.destroy(gi.getManagerByName(selectedUser.getUsername()).get(0).getManagerId());


            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            String user = (String) session.getAttribute("user");

            if (user != null && selectedUser.getUsername().equals(user)) {
                session.invalidate();

                RequestContext.getCurrentInstance().execute("location.reload(true)");
            }
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

    public void test() {
    }

    public Manager getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Manager selectedUser) {
        this.selectedUser = selectedUser;
    }
}
