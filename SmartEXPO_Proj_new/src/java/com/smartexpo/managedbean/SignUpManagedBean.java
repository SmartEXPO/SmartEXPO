/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.managedbean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

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

    /**
     * Creates a new instance of SignUpManagedBean
     */
    public SignUpManagedBean() {
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
        return "item?id=1&faces-redirect=true";
    }
}
