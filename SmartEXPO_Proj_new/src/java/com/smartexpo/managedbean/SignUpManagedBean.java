/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
import com.smartexpo.jpgcontrollers.ManagerJpaController;
import com.smartexpo.jpgcontrollers.exceptions.RollbackFailureException;
import com.smartexpo.models.Manager;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
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

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi = null;
    // SignUpManagedBean Field
    private String username;
    private String password;
    private String confirmPassword;
    private Boolean[] permissions;
    boolean isVerify;
    private static String UsernameComponentID = "username";
    private static String PasswordComponentID = "password";
    private static String ConfirmPasswordComponentID = "confirm_password";

    /**
     * Creates a new instance of SignUpManagedBean
     */
    public SignUpManagedBean() {
        isVerify = false;
        permissions = new Boolean[5];
        for (int i = 0; i < 5; i++) {
            permissions[i] = true;
        }
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

    public Boolean[] getPermissions() {
        return permissions;
    }

    public void setPermissions(Boolean[] permissions) {
        this.permissions = permissions;
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

    public void verify(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        if (session.isNew()) {
            session.invalidate();
        }

        if (isExist()) { // Username has existed
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sign up Error", "Username has existed."));
            isVerify = false;
        } else if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sign up Error", "Password not match"));
            isVerify = false;
        } else {
            isVerify = true;
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Sign Up Successfully!"));
        }

        if (isVerify) {
            storeManager();
        }
        username = password = confirmPassword = "";
    }

    private boolean isExist() {
        boolean result = false;

        if (gi.getManagerByName(username) != null) {
            result = true;
        }

        return result;
    }

    private void storeManager() {
        try {
            Manager manager = new Manager();
            manager.setUsername(username);
            manager.setPassword(password);

            if (permissions[0]) {
                manager.setPermission1(true);
            } else {
                manager.setPermission1(false);
            }
            if (permissions[1]) {
                manager.setPermission2(true);
            } else {
                manager.setPermission2(false);
            }
            if (permissions[2]) {
                manager.setPermission3(true);
            } else {
                manager.setPermission3(false);
            }
            if (permissions[3]) {
                manager.setPermission4(true);
            } else {
                manager.setPermission4(false);
            }
            if (permissions[4]) {
                manager.setPermission5(true);
            } else {
                manager.setPermission5(false);
            }

            ManagerJpaController mjc = new ManagerJpaController(utx, emf);
            mjc.create(manager);

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
        } catch (RollbackFailureException ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onInputValue() {
        if (isUsernameLegal(username)) {
            Logger.getLogger(SignUpManagedBean.class.getName()).log(Level.WARNING, "username = {0} leagal", username);
        }
    }

    private boolean isUsernameLegal(String user) {
        if (null == user || "".equals(user)) {
            return false;
        }
        Pattern p = Pattern.compile("^[a-zA-Z_]{3,16}$");
        Matcher matcher = p.matcher(user);
        return matcher.matches();
    }
}
