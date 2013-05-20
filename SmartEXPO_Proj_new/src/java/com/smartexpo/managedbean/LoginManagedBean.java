/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class LoginManagedBean implements Serializable {

    private static Logger logger = Logger.getLogger(LoginManagedBean.class.getName());
    @ManagedProperty(value = "Username")
    private String username;
    private String password = null;
    @ManagedProperty(value = "false")
    private boolean status;
    private String iID;

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

    @PostConstruct
    public void postConstruct() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SignUpManagedBean signUpManagedBean = (SignUpManagedBean) facesContext
                .getELContext().getELResolver()
                .getValue(facesContext.getELContext(), null, "signUpManagedBean");

        username = signUpManagedBean.getUsername();
        password = signUpManagedBean.getPassword();

        if (username != null && !username.equals("")
                && password != null && !password.equals("")) {
            // 数据库验证
            logger.log(Level.WARNING, "Why nonono");
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
    public void verify(AjaxBehaviorEvent event) {
        boolean check = true;

        if (check) { // 数据库验证
            setStatus(true);
            RequestContext.getCurrentInstance()
                    .execute("vanishLogin();void(0);"); // Close login pannel
        } else {
            setUsername("Username");
            setPassword("Password");
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
}
