/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import com.smartexpo.controls.GetInfo;
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
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
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
    @Resource
    private UserTransaction utx;
    // LoginManagedBean Field
    private static Logger logger = Logger.getLogger(LoginManagedBean.class.getName());
    private String username;
    private String password;
    @ManagedProperty(value = "false")
    private boolean status;
    private GetInfo gi = null;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        if (gi == null) {
            gi = new GetInfo(em, utx);
        }
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SignUpManagedBean signUpManagedBean = (SignUpManagedBean) facesContext
                .getELContext().getELResolver()
                .getValue(facesContext.getELContext(), null, "signUpManagedBean");

        username = signUpManagedBean.getUsername();
        password = signUpManagedBean.getPassword();
        logger.log(Level.WARNING, "In PostConstruct of LoginMB: username {0}, password {1}", new Object[]{username, password});

        if (username != null && !username.equals("")
                && password != null && !password.equals("")) {
            // 数据库验证
            setStatus(true);
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
    public void verify(ActionEvent event) {
        if (isPass()) { // 数据库验证
            logger.log(Level.WARNING, "Pass");
            setStatus(true);
            RequestContext.getCurrentInstance()
                    .execute("vanishLogin();void(0);"); // Close login pannel
        } else {
        }
    }

    public void logout(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
        session.invalidate();
        setStatus(false);
    }

    public String logoutAction() {
        HttpServletRequest request = (HttpServletRequest) FacesContext
                .getCurrentInstance().getExternalContext().getRequest();
        String itemID = request.getParameter("itemid");
        return "item?faces-redirect=true&itemid=" + itemID;
    }

    private boolean isPass() {
        boolean result = false;

        List<com.smartexpo.models.Manager> managers = gi.getManagerByName(username);
        if (managers == null) {
            FacesContext.getCurrentInstance()
                    .addMessage(null, new FacesMessage("Username doesn't exit."));
        } else {
            com.smartexpo.models.Manager manager = managers.get(0);
            if (manager.getPassword().equals(password)) {
                result = true;
                logger.log(Level.WARNING, "Log in successfully.");
            } else {
                FacesContext.getCurrentInstance()
                        .addMessage(null, new FacesMessage("Password doesn't match."));
                logger.log(Level.WARNING, "Password doesn't match.");
            }
        }

        return result;
    }
}
