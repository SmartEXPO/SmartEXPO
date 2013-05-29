/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

/**
 *
 * @author Boy
 */
public class User {

    private String username;
    private String password;
    private boolean[] permission;
    static int permissionCount = 5;

    public User(String username, String password, boolean[] permission) {
        this.username = username;
        this.password = password;
        this.permission = permission;
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
     * @return the permission
     */
    public boolean[] getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(boolean[] permission) {
        this.permission = permission;
    }
}
