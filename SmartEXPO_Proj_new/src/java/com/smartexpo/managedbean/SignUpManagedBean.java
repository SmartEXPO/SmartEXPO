/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

/**
 *
 * @author Boy
 */
@ManagedBean
@SessionScoped
public class SignUpManagedBean implements Serializable {

    @ManagedProperty(value = "Username")
    private String username;
    @ManagedProperty(value = "")
    private String password;
    @ManagedProperty(value = "")
    private String confirmPassword;
    boolean isVerify;

    /**
     * Creates a new instance of SignUpManagedBean
     */
    public SignUpManagedBean() {
        Logger logger = Logger.getLogger(SignUpManagedBean.class.getName());
        logger.log(Level.WARNING, FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
        isVerify = false;
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

    public String signUpVerify() {
        if (isVerify) {
            return "item?itemid=1&faces-redirect=true";
        } else {
            return null;
        }
    }

    public void verify(AjaxBehaviorEvent event) {
        if (true) { // 数据库中存在username
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Username has existed."));
            isVerify = false;
        } else if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Password not match"));
            isVerify = false;
        } else {
            isVerify = true;
        }
    }
}
