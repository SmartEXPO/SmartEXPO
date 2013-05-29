/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Manager;
import com.smartexpo.models.ManagerPermission;
import com.smartexpo.models.Permission;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class SignUpManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(SignUpManagedBean.class.getName());
    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @Resource
    private UserTransaction utx;
    // SignUpManagedBean Field
    private String username;
    private String password;
    private String confirmPassword;
    private String[] permissionString = {"p1", "p2", "p3", "p4", "p5"};
    boolean isVerify;
    private GetInfo gi = null;

    /**
     * Creates a new instance of SignUpManagedBean
     */
    public SignUpManagedBean() {
        isVerify = false;
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(em, utx);
        }
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword the confirmPassword to set
     */
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the permissionString
     */
    public String[] getPermissionString() {
        return permissionString;
    }

    /**
     * @param permissionString the permissionString to set
     */
    public void setPermissionString(String[] permissionString) {
        this.permissionString = permissionString;
    }

    public void verify(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session.isNew()) {
            logger.log(Level.WARNING, "session has existed.");
            session.invalidate();
        }

        if (isExist()) { // Username has existed
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Username has existed."));
            isVerify = false;
        } else if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Password not match"));
            isVerify = false;
            logger.log(Level.WARNING, "Password not match");
        } else {
            isVerify = true;
        }

        if (isVerify) {
            storeManager();
        }
    }

    private boolean isExist() {
        boolean result = false;

        if (gi.getManagerByName(username) != null) {
            result = true;
            logger.log(Level.WARNING, "Username \"{0}\" has existed.", username);
        }

        return result;
    }

    private void storeManager() {
        try {
            Manager manager = new Manager();
            manager.setUsername(username);
            manager.setPassword(password);

            Permission[] per = new Permission[3];
            for (int i = 0; i < 3; i++) {
                per[i] = new Permission();
                per[i].setPermissionName(permissionString[i]);
            }

            // Establish mapping between manager and permissions 
            ManagerPermission[] mp = new ManagerPermission[3];
            for (int i = 0; i < 3; i++) {
                mp[i] = new ManagerPermission();
                mp[i].setManagerId(manager);
                mp[i].setPermissionId(per[i]);
            }

            utx.begin();
            em.persist(manager);
            for (int i = 0; i < 3; i++) {
                em.persist(per[i]);
                em.persist(mp[i]);
            }
            utx.commit();

        } catch (NotSupportedException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicMixedException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (HeuristicRollbackException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalStateException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SystemException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
