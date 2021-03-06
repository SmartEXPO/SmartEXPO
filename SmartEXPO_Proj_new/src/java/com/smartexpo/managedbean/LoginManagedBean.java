/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.bundle.SessioninfoJpaController;
import com.smartexpo.bundle.exceptions.NonexistentEntityException;
import com.smartexpo.bundle.exceptions.PreexistingEntityException;
import com.smartexpo.bundle.exceptions.RollbackFailureException;
import com.smartexpo.controls.GetInfo;
import com.smartexpo.models.Manager;
import com.smartexpo.models.Sessioninfo;
import com.smartexpo.models.SessioninfoPK;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    @PersistenceContext(unitName = "SmartEXPO_ProjPU")
    EntityManager em;
    @PersistenceUnit(unitName = "SmartEXPO_ProjPU")
    EntityManagerFactory emf;
    @Resource
    private UserTransaction utx;
    private GetInfo gi;
    // LoginManagedBean Field
    private String username;
    private String password;
    private boolean[] permissions;
    @ManagedProperty(value = "false")
    private boolean autoLogin;
    @ManagedProperty(value = "false")
    private boolean status;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
        permissions = new boolean[6];
        for (int i = 0; i <= 5; ++i) {
            permissions[i] = false;
        }
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(emf, utx);
        }

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        String sessionUsername = (String) session.getAttribute("user");

        if (sessionUsername != null) {
            List<Manager> managers = gi.getManagerByName(sessionUsername);
            if (managers == null || managers.isEmpty()) {
            } else {
                Manager manager = managers.get(0);
                username = manager.getUsername();
                password = manager.getPassword();

                permissions[1] = manager.isPermission1();
                permissions[2] = manager.isPermission2();
                permissions[3] = manager.isPermission3();
                permissions[4] = manager.isPermission4();
                permissions[5] = manager.isPermission5();

                setStatus(true);
            }
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
     * @return the autoLogin
     */
    public boolean isAutoLogin() {
        return autoLogin;
    }

    /**
     * @param autoLogin the autoLogin to set
     */
    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    /**
     * @return the permissions
     */
    public boolean[] getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(boolean[] permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * Verify the user
     *
     * @return item.xhtml or error.xhtml
     */
    public void verify(AjaxBehaviorEvent event) {
        if (isPass(username, password)) { // 数据库验证
            setStatus(true);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", username);

            if (isAutoLogin()) { // choose auto log in check box
                recordCookie(username);
            }

            RequestContext.getCurrentInstance()
                    .execute("vanish();void(0);"); // Close login pannel
        } else {
            RequestContext.getCurrentInstance()
                    .execute("alert('Username or password wrong')");
        }
    }

    public String adminLogin() {
        if (isPass(username, password)) {
            setStatus(true);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(false);
            session.setAttribute("user", username);

            return "item_view?faces-redirect=true";
        } else {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Username or password Error!"));
            username = password = null;
        }

        return null;
    }

    public void logout(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();

        List<Sessioninfo> sinfos = gi.getSessioninfosByName(username);
        if (sinfos == null || sinfos.isEmpty()) {
        } else {
            SessioninfoJpaController sijc = new SessioninfoJpaController(utx, emf);
            for (int i = 0; i < sinfos.size(); i++) {
                try {
                    sijc.destroy(sinfos.get(i).getSessioninfoPK());
                } catch (NonexistentEntityException ex) {
                    Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RollbackFailureException ex) {
                    Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        username = password = null;
        for (int i = 1; i <= 5; ++i) {
            permissions[i] = false;
        }
        setStatus(false);

        RequestContext.getCurrentInstance()
                .execute("location.reload(true);");
    }

    private boolean isPass(String user, String pass) {
        boolean result = false;

        List<Manager> managers = gi.getManagerByName(user);
        if (managers == null) {
        } else {
            Manager manager = managers.get(0);
            if (manager.getPassword().equals(pass)) {
                permissions[1] = manager.isPermission1();
                permissions[2] = manager.isPermission2();
                permissions[3] = manager.isPermission3();
                permissions[4] = manager.isPermission4();
                permissions[5] = manager.isPermission5();

                result = true;
            }
        }
        return result;
    }

    private void recordCookie(String user) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        HttpServletResponse response = (HttpServletResponse) FacesContext
                .getCurrentInstance().getExternalContext().getResponse();

        Cookie usernameCookie, sessionIDCookie;
        usernameCookie = new Cookie(OverallInfo.COOKIE_NAME_USERNAME, user);
        usernameCookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(usernameCookie);

        String sessionID = session.getId();
        sessionIDCookie = new Cookie(OverallInfo.COOKIE_NAME_SESSION_ID, sessionID);
        sessionIDCookie.setMaxAge(60 * 60 * 24 * 14);
        response.addCookie(sessionIDCookie);


        SessioninfoPK sessioninfoPK = new SessioninfoPK();
        Sessioninfo sessioninfo = new Sessioninfo();
        sessioninfo.setSessioninfoPK(sessioninfoPK);
        sessioninfo.getSessioninfoPK().setSessionid(sessionID);
        sessioninfo.getSessioninfoPK().setUsername(user);
        SessioninfoJpaController sijc = new SessioninfoJpaController(utx, emf);

        try {
            sijc.create(sessioninfo);
        } catch (PreexistingEntityException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LoginManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
