/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Boy
 * @version 1.0
 */
@ManagedBean
@SessionScoped
public class SignInManagedBean {

    /**
     * Creates a new instance of SignInManagedBean
     */
    public SignInManagedBean() {
    }
    
    private String userName;
    private String password;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
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
    
    public String signIn() {
        return "error";
    }
    
    public String verify() {
        // TODO 数据库验证
        boolean verify = true;
        if (verify) {
            return "index";
        } else {
            return "login?face-redirect=true";
        }
    }
}
