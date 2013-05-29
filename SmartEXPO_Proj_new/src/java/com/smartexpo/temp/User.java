/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.util.List;

/**
 *
 * @author Boy
 */
public class User {

    private String username;
    private String passowrd;
    private List<Boolean> permissions;
    static final int Count = 5;

    public User(String username, String passowrd, List<Boolean> permissions) {
        this.username = username;
        this.passowrd = passowrd;
        this.permissions = permissions;
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
     * @return the passowrd
     */
    public String getPassowrd() {
        return passowrd;
    }

    /**
     * @param passowrd the passowrd to set
     */
    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    /**
     * @return the permissions
     */
    public List<Boolean> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<Boolean> permissions) {
        this.permissions = permissions;
    }
}
