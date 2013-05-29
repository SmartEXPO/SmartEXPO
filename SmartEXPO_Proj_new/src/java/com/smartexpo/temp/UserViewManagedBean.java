/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smartexpo.temp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Boy
 */
@ManagedBean
@RequestScoped
public class UserViewManagedBean {

    private List<User> users;

    /**
     * Creates a new instance of UserViewManagedBean
     */
    public UserViewManagedBean() {
        users = new ArrayList<User>();
        for (int i = 0; i < 20; i++) {
            boolean[] pers = new boolean[User.permissionCount];
            for (int j = 0; j < pers.length; j++) {
                pers[j] = Math.random() > 0.5 ? true : false;
            }
            users.add(new User("user " + i, "pass " + i, pers));
        }
    }

    /**
     * @return the users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public int getCount() {
        return users.size();
    }
}
